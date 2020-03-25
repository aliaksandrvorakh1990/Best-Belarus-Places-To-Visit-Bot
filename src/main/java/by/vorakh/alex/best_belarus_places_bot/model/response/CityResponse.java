package by.vorakh.alex.best_belarus_places_bot.model.response;

import java.io.Serializable;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A city model for reading.")
public class CityResponse implements Serializable {

    private static final long serialVersionUID = 7046869725179254927L;
    
    @ApiModelProperty(value = "The city ID in the database.", example = "1")
    private Integer id;
    @ApiModelProperty(value = "The city name.", example = "Minsk")
    private String name;
    @ApiModelProperty(value = "City places")
    private Set<CityPlaceResponse> places;
    
    public CityResponse() {}

    public CityResponse(Integer id, String name, Set<CityPlaceResponse> places) {
        this.id = id;
        this.name = name;
        this.places = places;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<CityPlaceResponse> getPlaces() {
        return places;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaces(Set<CityPlaceResponse> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name=%s, places=%s]", getClass().getSimpleName(), id, name, places);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((places == null) ? 0 
                : places.hashCode());
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
        CityResponse other = (CityResponse) obj;
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
