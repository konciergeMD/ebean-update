package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Foo extends Model {

    @Id
    public Long id;

    public String name = "default";

    public FooEnum value = FooEnum.ONE;

    public static Finder<Long,Foo> find = new Finder<Long,Foo>(
            Long.class, Foo.class
    );

    public enum FooEnum {
        ONE, TWO;
    }
}
