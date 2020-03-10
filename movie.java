package movieHibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_movie")
public class movie {
    @Id
    @GeneratedValue
    private Integer movie_id;
    private String movie_name;

    public String getMovie_name() {
        return movie_name;
    }
    public void setMovie_name(String phone) {
        this.movie_name = movie_name;
    }

}
