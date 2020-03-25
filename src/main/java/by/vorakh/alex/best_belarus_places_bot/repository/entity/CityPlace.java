package by.vorakh.alex.best_belarus_places_bot.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "city_place")
public class CityPlace implements Serializable {

    private static final long serialVersionUID = -8983179727634158803L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", unique = true, nullable = false)
    private Integer id;
    @NotNull
    @Size(min = 2, max = 200)
    @Column(name = "info", length = 200, nullable = false)
    private String info;//
    
    public CityPlace() {}

    public CityPlace(@NotNull @Size(min = 2, max = 200) String info) {
        this.info = info;
    }

    public CityPlace(Integer id, String info) {
        this(info);
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, info=%s]", getClass().getSimpleName(), id, info);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((info == null) ? 0 : info.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        CityPlace other = (CityPlace) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (info == null) {
            if (other.info != null) {
                return false;
            }
        } else if (!info.equals(other.info)) {
            return false;
        }
        return true;
    }

}
