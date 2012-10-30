package models;

import com.google.common.base.Optional;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Foo extends Model {

    private final static Integer EMPTY_INTEGER = -1;

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

    public Integer optionalIntegerVar() {
        if (Optional.of(integerVar).isPresent() && integerVar != EMPTY_INTEGER)
            return integerVar;
        else
            return null;
    }

    public void optionalIntegerVar(Optional<Integer> optionalIntegerVar) {
        this.integerVar = optionalIntegerVar.or(EMPTY_INTEGER);
    }
}
