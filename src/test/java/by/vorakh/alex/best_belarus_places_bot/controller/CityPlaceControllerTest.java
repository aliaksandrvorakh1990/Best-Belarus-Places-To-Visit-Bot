package by.vorakh.alex.best_belarus_places_bot.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import by.vorakh.alex.best_belarus_places_bot.controller.exception.ResourceNotExistException;
import by.vorakh.alex.best_belarus_places_bot.converter.CityPlaceToCityPlaceResponseConverter;
import by.vorakh.alex.best_belarus_places_bot.model.response.CityPlaceResponse;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;
import by.vorakh.alex.best_belarus_places_bot.service.CityPlaceService;

public class CityPlaceControllerTest {
    
    @Mock
    private CityPlaceService service;
    @Mock
    private CityPlaceToCityPlaceResponseConverter converter;
    
    @InjectMocks
    private CityPlaceController controller;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCityPlace_Existent_Place() {
        CityPlaceResponse expected = new CityPlaceResponse(1, "Visit the Upper Town");
        Integer id = 1;
        when(service.getById(id)).thenReturn(Optional.of(new CityPlace(1, "Visit the Upper Town")));
        when(converter.convert(any(CityPlace.class))).thenCallRealMethod();
        CityPlaceResponse actual = controller.getCityPlace(id);
        assertEquals(expected, actual);
    }
    
    @Test(expected = ResourceNotExistException.class)
    public void testGetCityPlace_Inexistent_Place_ThrownExceprion() {
        Integer id = 2;
        when(service.getById(id)).thenReturn(Optional.empty());
        CityPlaceResponse actual = controller.getCityPlace(id);
        assertNull(actual);
    }

}
