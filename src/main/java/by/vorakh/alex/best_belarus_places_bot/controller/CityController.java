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
import by.vorakh.alex.best_belarus_places_bot.converter.CityToCityResponseConverter;
import by.vorakh.alex.best_belarus_places_bot.model.payload.CityPayload;
import by.vorakh.alex.best_belarus_places_bot.model.payload.CityWithPlacesIDsPayload;
import by.vorakh.alex.best_belarus_places_bot.model.response.CityResponse;
import by.vorakh.alex.best_belarus_places_bot.model.response.IdResponse;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;
import by.vorakh.alex.best_belarus_places_bot.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 1800)
public class CityController {

    private final static String ID_DESCRIPTION = "The city ID.";
    private final static String ID_NOTE = "ID has to be greater than zero.";
    private final static String ID_EXAMPLE = "4";
    private final static String SERVER_ERROR = "Intenal server error.";
    private final static String INEXISTENT_CITY = "The city does not exist.";
    
    @Autowired
    private CityService service;
    @Autowired
    private CityToCityResponseConverter converter;
    
    @ApiOperation(value = "Get a list of existing cities from the database.",response = CityResponse.class, 
            responseContainer = "List")
    @ApiResponses({
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @GetMapping("/cities")
    public List<CityResponse> getAllCity() {
        return service.getAll().stream().map(converter::convert).collect(Collectors.toList());
    }
    
    @ApiOperation(value = "Get a city by id from the database.", notes = ID_NOTE, response = CityResponse.class)
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = INEXISTENT_CITY),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @GetMapping("/cities/{id}")
    public CityResponse getCity(@ApiParam(value = ID_DESCRIPTION, example = ID_EXAMPLE, required = true) 
            @PathVariable("id") @Valid @Positive @NotNull Integer id) {
        City city = service.getById(id).orElseThrow(() -> new ResourceNotExistException(INEXISTENT_CITY));
        return converter.convert(city);
    }

    @ApiOperation(value = "Create a city without city places in the database.", response = IdResponse.class)
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = "The city exists or invalid data."),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR,message = SERVER_ERROR),
    })
    @PostMapping("/cities")
    public IdResponse createCity(
            @ApiParam(value = "A payload with a city name for creating in the database.", required = true)
            @Valid @RequestBody CityPayload newPayload) {
        Integer id = service.create(newPayload);
        return new IdResponse(id);
    }

    @ApiOperation("Update a name and city places for an existing city in the database.")
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = "The city does not exist or invalid data."),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @PutMapping("/cities/{id}")
    public void updateCity(
            @ApiParam(value = ID_DESCRIPTION, example = ID_EXAMPLE, required = true)
            @PathVariable("id") @Valid @Positive @NotNull Integer id, 
            @ApiParam(value = "A payload with a new city name and city places IDs.", required = true)
            @Valid @RequestBody CityWithPlacesIDsPayload editedCityPayload) {
        service.update(id, editedCityPayload);
    }
    
    @ApiOperation(value = "Delete an existing city by id from the database.", notes = ID_NOTE)
    @ApiResponses({
        @ApiResponse(code = SC_NOT_FOUND, message = INEXISTENT_CITY),
        @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = SERVER_ERROR)
    })
    @DeleteMapping("/cities/{id}")
    public void deleteCityPlace(@ApiParam(value = ID_DESCRIPTION, example = ID_EXAMPLE, required = true)
            @PathVariable("id") @Valid @Positive @NotNull Integer id) {
        service.delete(id);
    }

}
