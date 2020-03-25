package by.vorakh.alex.best_belarus_places_bot.model.payload;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details about the cityplace for creating and updating.")
public class CityPlacePayload implements Serializable {

    private static final long serialVersionUID = 2937059655235680487L;

    @NotNull
    @Size(min = 2, max = 200)
    @ApiModelProperty(value = "The Cityplace info.",  example = "Walk the central part of Nezavisimosti Avenue")
    private String info;
    
    public CityPlacePayload() {}

    public CityPlacePayload(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return String.format("%s [info=%s]", getClass().getSimpleName(), info);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        CityPlacePayload other = (CityPlacePayload) obj;
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
