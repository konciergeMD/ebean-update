package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Foo extends Model {

    @Id
    public Long id;

    public String stringVar = "default";

    public Integer integerVar = 1;

    public UpdateEnum enumVar = UpdateEnum.DEFAULT;

    @OneToOne
    public Bar objectVar = new Bar();

    public static Finder<Long,Foo> find = new Finder<Long,Foo>(
            Long.class, Foo.class
    );
}
