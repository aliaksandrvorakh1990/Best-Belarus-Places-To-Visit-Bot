package by.vorakh.alex.best_belarus_places_bot.model.payload;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A model for creating or updating of a city .")
public class CityPayload implements Serializable {

    private static final long serialVersionUID = 7046869725179254927L;
    
    @NotNull
    @Size(min = 2, max = 40)
    @ApiModelProperty(value = "The city name.", example = "Brest")
    private String name;

    
    public CityPayload() {}

    public CityPayload(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s [name=%s]", getClass().getSimpleName(), name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        CityPayload other = (CityPayload) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
