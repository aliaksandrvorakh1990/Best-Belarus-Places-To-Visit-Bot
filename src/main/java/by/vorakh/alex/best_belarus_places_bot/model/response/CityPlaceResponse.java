package by.vorakh.alex.best_belarus_places_bot.model.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A cityplace model for reading")
public class CityPlaceResponse implements Serializable {

    private static final long serialVersionUID = 2937059655235680487L;
    
    @ApiModelProperty(value = "The place ID", example = "2")
    private Integer id;
    @ApiModelProperty(value = "The Cityplace info.", example = "Walk the central part of Nezavisimosti Avenue")
    private String info;
    
    public CityPlaceResponse() {}

    public CityPlaceResponse(Integer id, String info) {
        this.info = info;
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
        CityPlaceResponse other = (CityPlaceResponse) obj;
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
