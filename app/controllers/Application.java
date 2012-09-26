package controllers;

import models.Foo;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        Foo foo1 = new Foo();
        foo1.stringVar = "Foo1";
        foo1.save();

        try {
            Foo foo2 = new Foo();
            foo2.id = foo1.id;
            foo2.stringVar = "Foo2";
            foo2.save();
        } catch (Throwable t) {
            System.out.println(t.toString());
        }

        return ok(index.render("Your new application is ready."));
    }
}