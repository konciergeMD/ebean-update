import models.Foo;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class UpdateTest {

    @Test
    public void saveSameIdShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.name = "Foo1";
                foo1.save();

                try {
                    Foo foo2 = new Foo();
                    foo2.id = foo1.id;
                    foo2.name = "Foo2";
                    foo2.save();
                } catch (Throwable t) {
                    System.out.println(t.toString());
                }


                assertThat(Foo.find.byId(foo1.id).name).isEqualTo("Foo2");
            }
        });
    }

    @Test
    public void saveSameIdAfterFindShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.name = "Foo1";
                foo1.save();

                try {
                    Foo foo2 = Foo.find.byId(foo1.id);
                    foo2.name = "Foo2";
                    foo2.save();
                } catch (Throwable t) {
                    System.out.println(t.toString());
                }

                assertThat(Foo.find.byId(foo1.id).name).isEqualTo("Foo2");
            }
        });
    }

    @Test
    public void updateSameIdShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.name = "Foo1";
                foo1.save();

                try {
                    Foo foo2 = new Foo();
                    foo2.id = foo1.id;
                    foo2.name = "Foo2";
                    foo2.update();
                } catch (Throwable t) {
                    System.out.println(t.toString());
                }

                assertThat(Foo.find.byId(foo1.id).name).isEqualTo("Foo2");
            }
        });
    }

    @Test
    public void updateSameIdWithNullShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.name = "Foo1";
                foo1.save();

                try {
                    Foo foo2 = new Foo();
                    foo2.id = foo1.id;
                    foo2.name = null;
                    foo2.update();
                } catch (Throwable t) {
                    System.out.println(t.toString());
                }

                assertThat(Foo.find.byId(foo1.id).name).isNull();
            }
        });
    }

    @Test
    public void updateSameIdWithEmptyStringShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.name = "Foo1";
                foo1.save();

                try {
                    Foo foo2 = new Foo();
                    foo2.id = foo1.id;
                    foo2.name = "";
                    foo2.update();
                } catch (Throwable t) {
                    System.out.println(t.toString());
                }

                assertThat(Foo.find.byId(foo1.id).name).isEmpty();
            }
        });
    }

    @Test
    public void updateSameIdWithDefaultNameShouldNotUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.name = "Foo1";
                foo1.save();

                try {
                    Foo foo2 = new Foo();
                    foo2.id = foo1.id;
                    foo2.update();
                } catch (Throwable t) {
                    System.out.println(t.toString());
                }

                assertThat(Foo.find.byId(foo1.id).name).isEqualTo("Foo1");
            }
        });
    }

    @Test
    public void saveWithDefaultValueShouldHaveDefaultValue() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.name = "Foo1";
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).value).isEqualTo(Foo.FooEnum.ONE);
            }
        });
    }

    @Test
    public void updateSameIdWithDefaultValueShouldNotUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.value = Foo.FooEnum.TWO;
                foo1.save();

                try {
                    Foo foo2 = new Foo();
                    foo2.id = foo1.id;
                    foo2.update();
                } catch (Throwable t) {
                    System.out.println(t.toString());
                }

                assertThat(Foo.find.byId(foo1.id).value).isEqualTo(Foo.FooEnum.TWO);
            }
        });
    }
}
