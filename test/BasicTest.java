import models.Foo;
import models.UpdateEnum;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class BasicTest {

    /**
     * Calling save() on a new object should save default simple types, but not default entities
     */
    @Test
    public void saveWithDefaults() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.save();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("default");
                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(1);
                assertThat(Foo.find.byId(foo1.id).enumVar).isEqualTo(UpdateEnum.DEFAULT);
                // @OneToOne relationships will not cascade save
                assertThat(Foo.find.byId(foo1.id).objectVar).isNull();
            }
        });
    }

    /**
     * Calling update() on a new object should throw exception
     */
    @Test(expected = PersistenceException.class)
    public void updateWithDefaults() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.update();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("default");
                assertThat(Foo.find.byId(foo1.id).integerVar).isEqualTo(1);
                assertThat(Foo.find.byId(foo1.id).enumVar).isEqualTo(UpdateEnum.DEFAULT);
                // @OneToOne relationships will not cascade save
                assertThat(Foo.find.byId(foo1.id).objectVar).isNull();
            }
        });
    }

    /**
     * Saving a detached entity with an id that already exists should throw an exception
     */
    @Test(expected = PersistenceException.class)
    public void saveDetachedShouldThrowException() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.save();

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.stringVar = "updated";
                foo2.save();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("updated");
            }
        });
    }

    /**
     * Updating a detached entity with an id that already exists should not throw an exception
     */
    @Test
    public void updateDetachedShouldNotThrowException() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.save();

                Foo foo2 = new Foo();
                foo2.id = foo1.id;
                foo2.stringVar = "updated";
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("updated");
            }
        });
    }

    /**
     * Saving an attached entity should succeed
     */
    @Test
    public void saveAttachedShouldSucceed() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.save();

                Foo foo2 = Foo.find.byId(foo1.id);
                foo2.stringVar = "updated";
                foo2.save();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("updated");
            }
        });
    }

    /**
     * Updating an attached entity with an id that already exists should succeed
     */
    @Test
    public void updateAttachedShouldNotThrowException() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Foo foo1 = new Foo();
                foo1.save();

                Foo foo2 = Foo.find.byId(foo1.id);
                foo2.stringVar = "updated";
                foo2.update();

                assertThat(Foo.find.byId(foo1.id).stringVar).isEqualTo("updated");
            }
        });
    }
}
