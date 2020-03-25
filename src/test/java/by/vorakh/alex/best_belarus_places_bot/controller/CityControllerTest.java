package by.vorakh.alex.best_belarus_places_bot.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import by.vorakh.alex.best_belarus_places_bot.controller.exception.ResourceNotExistException;
import by.vorakh.alex.best_belarus_places_bot.converter.CityToCityResponseConverter;
import by.vorakh.alex.best_belarus_places_bot.model.response.CityPlaceResponse;
import by.vorakh.alex.best_belarus_places_bot.model.response.CityResponse;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;
import by.vorakh.alex.best_belarus_places_bot.service.CityService;

public class CityControllerTest {
    
    @Mock
    private CityService service;
    @Mock
    private CityToCityResponseConverter converter;
    
    @InjectMocks
    private CityController controller;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCity_Existent_City() {
        Integer id = 1;
        String cityName = "Minsk";
        Set<CityPlaceResponse> places = new HashSet<CityPlaceResponse>();
        places.add(new CityPlaceResponse(1, "Visit the Upper Town"));
        CityResponse expected = new CityResponse(id, cityName, places);
        Set<CityPlace> minskPlaces = new HashSet<CityPlace>();
        minskPlaces.add(new CityPlace(1, "Visit the Upper Town"));
        when(service.getById(id)).thenReturn(Optional.of(new City(id, cityName, minskPlaces)));
        when(converter.convert(any(City.class))).thenReturn(new CityResponse(id, cityName, places));
        CityResponse actual = controller.getCity(id);
        assertEquals(expected, actual);
    }
    
    @Test(expected = ResourceNotExistException.class)
    public void testGetCity_Inexistent_City_ThrownExceprion() {
        Integer id = 2;
        when(service.getById(id)).thenReturn(Optional.empty());
        CityResponse actual = controller.getCity(id);
        assertNull(actual);
    }
}
