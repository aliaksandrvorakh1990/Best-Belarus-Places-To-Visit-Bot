package by.vorakh.alex.best_belarus_places_bot.model.payload;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "The id model for updating.")
public class IdPayload implements Serializable {

    private static final long serialVersionUID = -5259709119438865381L;
    
    @NotNull
    @Positive 
    @ApiModelProperty(value = "An ID.", example = "6")
    private Integer id;
    
    public IdPayload() {}
    
    public IdPayload(@NotNull @Positive Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s]", getClass().getSimpleName(), id);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IdPayload other = (IdPayload) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
