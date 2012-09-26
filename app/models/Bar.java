package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bar extends Model {

    @Id
    public Long id;

    public String stringVar;

    public Integer integerVar;

    public UpdateEnum enumVar;

    public static Finder<Long,Bar> find = new Finder<Long,Bar>(
            Long.class, Bar.class
    );

    @Override
    public String toString() {
        return "Bar{" +
                "id=" + id +
                ", stringVar='" + stringVar + '\'' +
                '}';
    }
}
