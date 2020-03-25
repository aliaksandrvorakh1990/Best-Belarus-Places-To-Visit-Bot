package by.vorakh.alex.best_belarus_places_bot.model.payload;

import java.util.Set;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A model for updating of a city with existed cityplaces.", parent = CityPayload.class)
public class CityWithPlacesIDsPayload extends CityPayload {

    private static final long serialVersionUID = 1007147401072015169L;
    
    @NotNull
    @ApiModelProperty(value = "City places IDs")
    private Set<IdPayload> placeIds;

    public CityWithPlacesIDsPayload() {}

    public CityWithPlacesIDsPayload(String name, @NotNull Set<IdPayload> placeIds) {
        super(name);
        this.placeIds = placeIds;
    }

    public Set<IdPayload> getPlaceIds() {
        return placeIds;
    }

    public void setPlaceIds(Set<IdPayload> placeIds) {
        this.placeIds = placeIds;
    }

    @Override
    public String toString() {
        return  String.format("%s [info=%s, placeIds=%s]", getClass().getSimpleName(), placeIds);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((placeIds == null) ? 0 : placeIds.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CityWithPlacesIDsPayload other = (CityWithPlacesIDsPayload) obj;
        if (placeIds == null) {
            if (other.placeIds != null) {
                return false;
            }
        } else if (!placeIds.equals(other.placeIds)) {
            return false;
        }
        return true;
    }

}
