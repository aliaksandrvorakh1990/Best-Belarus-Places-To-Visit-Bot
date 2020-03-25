package by.vorakh.alex.best_belarus_places_bot.repository.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 8019687392271664344L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "name", length = 40, nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private Set<CityPlace> places;
    
    public City() {}

    public City(@NotNull @Size(min = 2, max = 40) String name) {
        this.name = name;
    }

    public City(Integer id, String name) {
        this(name);
        this.id = id;
    }

    public City(String name, Set<CityPlace> places) {
        this(name);
        this.places = places;
    }
    
    public City(Integer id, String name, Set<CityPlace> places) {
        this(name);
        this.id = id;
        this.places = places;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<CityPlace> getPlaces() {
        return places;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaces(Set<CityPlace> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name=%s, places=%s]",  getClass().getSimpleName(), id, name, places);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((places == null) ? 0 : places.hashCode());
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
        City other = (City) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (places == null) {
            if (other.places != null) {
                return false;
            }
        } else if (!places.equals(other.places)) {
            return false;
        }
        return true;
    }

}
