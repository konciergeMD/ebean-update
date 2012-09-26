import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "ebean-update"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here
      compile in Test <<= PostCompileTemp(Test)
    )

  //TODO: remove after https://github.com/playframework/Play20/commit/7294d6df0ab92e2e71010f9f7cabcbf00b51c570 is available
  def PostCompileTemp(scope: Configuration) = (sourceDirectory in scope, dependencyClasspath in scope, compile in scope, javaSource in scope, sourceManaged in scope, classDirectory in scope, ebeanEnabled) map {
    (src, deps, analysis, javaSrc, srcManaged, classes, ebean) =>

      val classpath = (deps.map(_.data.getAbsolutePath).toArray :+ classes.getAbsolutePath).mkString(java.io.File.pathSeparator)

      val javaClasses = (javaSrc ** "*.java").get.map {
        sourceFile =>
          analysis.relations.products(sourceFile)
      }.flatten.distinct

      javaClasses.foreach(play.core.enhancers.PropertiesEnhancer.generateAccessors(classpath, _))
      javaClasses.foreach(play.core.enhancers.PropertiesEnhancer.rewriteAccess(classpath, _))

      // EBean
      if (ebean) {

        val originalContextClassLoader = Thread.currentThread.getContextClassLoader

        try {

          val cp = deps.map(_.data.toURI.toURL).toArray :+ classes.toURI.toURL

          Thread.currentThread.setContextClassLoader(new java.net.URLClassLoader(cp, ClassLoader.getSystemClassLoader))

          import com.avaje.ebean.enhance.agent._
          import com.avaje.ebean.enhance.ant._
          import collection.JavaConverters._
          import com.typesafe.config._

          val cl = ClassLoader.getSystemClassLoader

          val t = new Transformer(cp, "debug=-1")

          val ft = new OfflineFileTransform(t, cl, classes.getAbsolutePath, classes.getAbsolutePath)

          val config = ConfigFactory.load(ConfigFactory.parseFileAnySyntax(new File("conf/application.conf")))

          val models = try {
            config.getConfig("ebean").entrySet.asScala.map(_.getValue.unwrapped).toSet.mkString(",")
          } catch {
            case e: ConfigException.Missing => "models.*"
          }

          try {
            ft.process(models)
          } catch {
            case _ =>
          }

        } catch {
          case e => throw e
        } finally {
          Thread.currentThread.setContextClassLoader(originalContextClassLoader)
        }
      }
      // Copy managed classes - only needed in Compile scope
      if (scope.name.toLowerCase == "compile") {
        val managedClassesDirectory = classes.getParentFile / (classes.getName + "_managed")

        val managedClasses = ((srcManaged ** "*.scala").get ++ (srcManaged ** "*.java").get).map {
          managedSourceFile =>
            analysis.relations.products(managedSourceFile)
        }.flatten x rebase(classes, managedClassesDirectory)

        // Copy modified class files
        val managedSet = IO.copy(managedClasses)

        // Remove deleted class files
        (managedClassesDirectory ** "*.class").get.filterNot(managedSet.contains(_)).foreach(_.delete())
      }
      analysis
  }

}
