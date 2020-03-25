package by.vorakh.alex.best_belarus_places_bot.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import by.vorakh.alex.best_belarus_places_bot.repository.CityPlaceRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;
import by.vorakh.alex.best_belarus_places_bot.service.exception.ServiceException;

public class CityPlaceServiceImplTest {

    @Mock
    private CityPlaceRepository repository;
    
    @InjectMocks
    private CityPlaceServiceImpl service;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetById_Existent_Place() {
        Optional<CityPlace> expected = Optional.of(new CityPlace(1, "Visit the Upper Town"));
        Integer id = 1;
        when(repository.getById(id)).thenReturn(new CityPlace(1, "Visit the Upper Town"));
        Optional<CityPlace> actual = service.getById(id);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetById_Inexistent_Place() {
        Optional<CityPlace> expected = Optional.empty();
        Integer id = 100;
        when(repository.getById(id)).thenReturn(null);
        Optional<CityPlace> actual = service.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCityPlaceInfo_Existent_City() {
        Optional<String> expected = Optional.of("Visit the Upper Town\nWalk the central part of Nezavisimosti Avenue");
        String cityName = "Minsk";
        List<CityPlace> minskPlaces = Arrays.asList(new CityPlace(1, "Visit the Upper Town"), 
                new CityPlace(2, "Walk the central part of Nezavisimosti Avenue"));
        when(repository.getAll(cityName)).thenReturn(minskPlaces);
        Optional<String> actual = service.getCityPlaceInfo(cityName);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetCityPlaceInfo_Inexistent_City() {
        Optional<String> expected = Optional.empty();
        String cityName = "Boston";
        List<CityPlace> emptyCityPlaces = Collections.emptyList();
        when(repository.getAll(cityName)).thenReturn(emptyCityPlaces);
        Optional<String> actual = service.getCityPlaceInfo(cityName);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_Existent_Place() {
        Integer id = 1;
        String cityPlaceInfo = "Walk the central part of Nezavisimosti Avenue";
        when(repository.getById(id)).thenReturn(new CityPlace(1, "Visit the Upper Town"));
        service.update(id, cityPlaceInfo);
        verify(repository, times(1)).update(any(CityPlace.class));
    }
    
    @Test(expected = ServiceException.class)
    public void testUpdate_Inexistent_Place_ThrownException() {
        Integer id = 100;
        String cityPlaceInfo = "See the grandeur of Bolshoi Opera and Ballet Theatre of Belarus";
        when(repository.getById(id)).thenReturn(null);
        service.update(id, cityPlaceInfo);
        verify(repository, times(0)).update(any(CityPlace.class));
    }

    @Test
    public void testDelete_Existent_Place() {
        Integer id = 1;
        when(repository.getById(id)).thenReturn(new CityPlace(1, "Visit the Upper Town"));
        service.delete(id);
        verify(repository, times(1)).delete(any(CityPlace.class));
    }
    
    @Test(expected = ServiceException.class)
    public void testDelete_Inexistent_Place_ThrownException() {
        Integer id = 102;
        when(repository.getById(id)).thenReturn(null);
        service.delete(id);
        verify(repository, times(0)).delete(any(CityPlace.class));
    }

}
