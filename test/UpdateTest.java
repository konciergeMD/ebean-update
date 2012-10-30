import com.avaje.ebean.Ebean;
import com.google.common.base.Optional;
import models.Bar;
import models.Foo;
import models.UpdateEnum;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class UpdateTest {

    /**
     * Updating an detached entity with an id that already exists should succeed
     */
    @Test
    public void updateDetachedShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.stringVar = "Foo1";
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("Foo1");

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.stringVar = "Foo2";
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("Foo2");
            }
        });
    }

    /**
     * Updating an detached entity with default string should modify row
     * NOTE: This behavior implies that using defaults on Model objects is very dangerous
     */
    @Test
    public void updateDetachedWithDefaultStringShouldUpdateRow() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.stringVar = "don'tChangeMeBro";
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("don'tChangeMeBro");

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("default");
            }
        });
    }

    /**
     * Updating an detached entity with default enum should modify row
     * NOTE: This behavior implies that using defaults on Model objects is very dangerous
     */
    @Test
    public void updateDetachedWithDefaultEnumShouldUpdateRow() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.enumVar = UpdateEnum.UPDATED;
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).enumVar).isEqualTo(UpdateEnum.UPDATED);

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).enumVar).isEqualTo(UpdateEnum.DEFAULT);
            }
        });
    }

    /**
     * Updating an detached entity with default integer should modify row
     * NOTE: This behavior implies that using defaults on Model objects is very dangerous
     */
    @Test
    public void updateDetachedWithDefaultIntegerShouldUpdateRow() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.integerVar = 2;
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(2);

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(1);
            }
        });
    }

    /**
     * Updating an detached entity using empty string should update
     */
    @Test
    public void updateDetachedWithEmptyStringShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.stringVar = "Foo1";
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("Foo1");

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.stringVar = "";
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("");
            }
        });
    }

    /**
     * Updating an detached entity using null should not update
     */
    @Test
    public void updateDetachedWithNullStringShouldNotUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.stringVar = "Foo1";
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("Foo1");

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.stringVar = null;
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("Foo1");
            }
        });
    }

    /**
     * Updating an detached entity using null should not update
     */
    @Test
    public void updateDetachedWithNullIntegerShouldNotUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.integerVar = 2;
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(2);

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.integerVar = null;
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(2);
            }
        });
    }

    /**
     * Updating an detached entity using an absent value should update to null
     */
    @Test
    public void updateDetachedWithAbsentIntegerShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.integerVar = 2;
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(2);

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.optionalIntegerVar(Optional.<Integer>absent());
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).optionalIntegerVar()).isNull();
            }
        });
    }

    /**
     * Updating an detached entity using a present value should update
     */
    @Test
    public void updateDetachedWithPresentIntegerShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.integerVar = 2;
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(2);

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.optionalIntegerVar(Optional.of(47));
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).optionalIntegerVar()).isEqualTo(47);
            }
        });
    }

    /**
     * Updating an detached entity using an absent value should update to null
     */
    @Test
    public void updateDetachedWithNullIntegerShouldUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.integerVar = 2;
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(2);

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.integerVar = null;

                Set<String> changedFields = new HashSet<String>();
                changedFields.add("id");
                changedFields.add("integerVar");
                Ebean.update(foo2, changedFields);

                assertThat(Foo.find.byId(foo1.id).integerVar).isNull();
            }
        });
    }

    /**
     * Updating an detached entity using null object should not update
     */
    @Test
    public void updateDetachedWithNullObjectShouldNotUpdate() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Bar bar1 = new Bar();
                bar1.save();

                Foo foo1 = new Foo();
                foo1.objectVar = bar1;
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).objectVar).isEqualTo(bar1);

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.objectVar = null;
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).objectVar).isEqualTo(bar1);
            }
        });
    }


//    @Test
//    public void updateSameIdWithNullShouldUpdate() {
//        running(fakeApplication(), new Runnable() {
//            public void run() {
//                Foo foo1 = new Foo();
//                foo1.stringVar = "Foo1";
//                foo1.save();
//
//                try {
//                    Foo foo2 = new Foo();
//                    foo2.id = foo1.id;
//                    foo2.stringVar = null;
//                    foo2.update();
//                } catch (Throwable t) {
//                    System.out.println(t.toString());
//                }
//
//                assertThat(Foo.find.byId(foo1.id).stringVar).isNull();
//            }
//        });
//    }
//
//    @Test
//    public void updateSameIdWithEmptyStringShouldUpdate() {
//        running(fakeApplication(), new Runnable() {
//            public void run() {
//                Foo foo1 = new Foo();
//                foo1.stringVar = "Foo1";
//                foo1.save();
//
//                try {
//                    Foo foo2 = new Foo();
//                    foo2.id = foo1.id;
//                    foo2.stringVar = "";
//                    foo2.update();
//                } catch (Throwable t) {
//                    System.out.println(t.toString());
//                }
//
//                assertThat(Foo.find.byId(foo1.id).stringVar).isEmpty();
//            }
//        });
//    }
//
//    @Test
//    public void updateSameIdWithDefaultNameShouldNotUpdate() {
//        running(fakeApplication(), new Runnable() {
//            public void run() {
//                Foo foo1 = new Foo();
//                foo1.stringVar = "Foo1";
//                foo1.save();
//
//                try {
//                    Foo foo2 = new Foo();
//                    foo2.id = foo1.id;
//                    foo2.update();
//                } catch (Throwable t) {
//                    System.out.println(t.toString());
//                }
//
//                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("Foo1");
//            }
//        });
//    }
//
//    @Test
//    public void saveWithDefaultValueShouldHaveDefaultValue() {
//        running(fakeApplication(), new Runnable() {
//            public void run() {
//                Foo foo1 = new Foo();
//                foo1.stringVar = "Foo1";
//                foo1.save();
//
//                assertThat(Foo.find.byId(foo1.id).enumVar).isEqualTo(Foo.UpdateEnum.DEFAULT);
//            }
//        });
//    }
//
//    @Test
//    public void updateSameIdWithDefaultValueShouldNotUpdate() {
//        running(fakeApplication(), new Runnable() {
//            public void run() {
//                Foo foo1 = new Foo();
//                foo1.enumVar = Foo.UpdateEnum.UPDATED;
//                foo1.save();
//
//                try {
//                    Foo foo2 = new Foo();
//                    foo2.id = foo1.id;
//                    foo2.update();
//                } catch (Throwable t) {
//                    System.out.println(t.toString());
//                }
//
//                assertThat(Foo.find.byId(foo1.id).enumVar).isEqualTo(Foo.UpdateEnum.UPDATED);
//            }
//        });
//    }
}
