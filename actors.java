package movieHibernate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_actors")
public class actors {

    @Id
    @GeneratedValue
    private Integer actor_id;
    private String actor_name;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name="tbl_movie_actor",
            joinColumns = { @JoinColumn( name="actor_id") },
            inverseJoinColumns = @JoinColumn( name="movie_id")
    )
    private Set<movie> movieNames;

    @Override
    public String toString() {
        return "Actors{" +
                "actor_id=" + actor_id +
                ", actor_name='" + actor_name +
                '}';
    }

    public Integer getActor_id() {
        return actor_id;
    }
    public void setActor_id(Integer actor_id) {
        this.actor_id = actor_id;
    }
    public String getActor_name() {
        return actor_name;
    }
    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }
    public Set<movie> getMovieNames() {
        return movieNames;
    }


}
