package by.vorakh.alex.best_belarus_places_bot.controller;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import by.vorakh.alex.best_belarus_places_bot.controller.exception.ResourceNotExistException;
import by.vorakh.alex.best_belarus_places_bot.converter.CityPlaceToCityPlaceResponseConverter;
import by.vorakh.alex.best_belarus_places_bot.model.payload.CityPlacePayload;
import by.vorakh.alex.best_belarus_places_bot.model.response.CityPlaceResponse;
import by.vorakh.alex.best_belarus_places_bot.model.response.IdResponse;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;
import by.vorakh.alex.best_belarus_places_bot.service.CityPlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 1800)
public class CityPlaceController {
    
    private final static String ID_DESCRIPTION = "The cityplace ID";
    private final static String ID_NOTE = "ID has to be greater than zero.";
    private final static String ID_EXAMPLE = "2";
    private final static String SERVER_ERROR = "Intenal server error.";
    private final static String INEXISTENT_PLACE = "The city place does not exist.";

    @Autowired
    private CityPlaceService service;
    @Autowired
    private CityPlaceToCityPlaceResponseConverter converter;
    
    @ApiOperation(value = "Get a list of existing cityplaces from the database", response = CityPlaceResponse.class, 
            responseContainer = "List")
    @ApiResponses({
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @GetMapping("/city_places")
    public List<CityPlaceResponse> getAllCityPlaces() {
        return service.getAll().stream().map(converter::convert).collect(Collectors.toList());
    }
    
    @ApiOperation(value = "Get a cityplace by id .", notes = ID_NOTE, response = CityPlaceResponse.class)
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = INEXISTENT_PLACE),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @GetMapping("/city_places/{id}")
    public CityPlaceResponse getCityPlace(
            @ApiParam(value = ID_DESCRIPTION, example = ID_EXAMPLE, required = true) 
            @PathVariable("id") @Valid @Positive @NotNull Integer id) {
        CityPlace cityPlace = service.getById(id)
                .orElseThrow(() -> new ResourceNotExistException(INEXISTENT_PLACE));
        return converter.convert(cityPlace);
    }

    @ApiOperation(value = "Create a cityplace in the database", response = IdResponse.class)
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = "The city place exists or invalid data."),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR,message = SERVER_ERROR),
    })
    @PostMapping("/city_places")
    public IdResponse createCityPlace(
            @ApiParam(value = "A payload with a place info for creating in the database.", required = true)
            @RequestBody @Valid @NotNull CityPlacePayload newCityPlace) {
        Integer id = service.create(newCityPlace.getInfo());
        return new IdResponse(id);
    }

    @ApiOperation("Update an existing cityplace in the database.")
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = "The city place does not exist or invalid data."),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @PutMapping("/city_places/{id}")
    public void updateCityPlace(
            @ApiParam(value = ID_DESCRIPTION, example = ID_EXAMPLE, required = true) 
            @PathVariable("id") @Valid @Positive @NotNull Integer id, 
            @ApiParam(value = "A payload with a new place info.", required = true) 
            @RequestBody @Valid @NotNull CityPlacePayload editedCityPlace) {
        service.update(id, editedCityPlace.getInfo());
    }

    @ApiOperation(value = "Delete an existing cityplace by id from the database.",  notes = ID_NOTE)
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = INEXISTENT_PLACE),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @DeleteMapping("/city_places/{id}")
    public void deleteCityPlace(
            @ApiParam(value = ID_DESCRIPTION, example = ID_EXAMPLE, required = true)
            @PathVariable("id") @Valid @Positive @NotNull Integer id) {
        service.delete(id);
    }

}
