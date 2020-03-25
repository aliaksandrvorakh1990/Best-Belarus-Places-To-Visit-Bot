package by.vorakh.alex.best_belarus_places_bot.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import by.vorakh.alex.best_belarus_places_bot.model.payload.CityPayload;
import by.vorakh.alex.best_belarus_places_bot.model.payload.CityWithPlacesIDsPayload;
import by.vorakh.alex.best_belarus_places_bot.model.payload.IdPayload;
import by.vorakh.alex.best_belarus_places_bot.repository.CityPlaceRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.CityRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;
import by.vorakh.alex.best_belarus_places_bot.service.exception.ServiceException;

public class CityServiceImplTest {
 
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityPlaceRepository cityPlaceRepository;
    
    @InjectMocks
    private CityServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetById_Existent_City() {
        Set<CityPlace> minskPlaces = new HashSet<CityPlace>();
        minskPlaces.add(new CityPlace(1, "Visit the Upper Town"));
        minskPlaces.add(new CityPlace(2, "See the grandeur of Bolshoi Opera and Ballet Theatre of Belarus"));
        minskPlaces.add(new CityPlace(3, "Walk the central part of Nezavisimosti Avenue"));
        String cityName = "Minsk";
        City minsk = new City(1, cityName, minskPlaces);
        Optional<City> expected = Optional.of(minsk);
        Integer id = 1;
        Set<CityPlace> places = new HashSet<CityPlace>();
        places.add(new CityPlace(1, "Visit the Upper Town"));
        places.add(new CityPlace(2, "See the grandeur of Bolshoi Opera and Ballet Theatre of Belarus"));
        places.add(new CityPlace(3, "Walk the central part of Nezavisimosti Avenue"));
        when(cityRepository.getById(id)).thenReturn(new City(id, cityName, places));
        Optional<City> actual = service.getById(id);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetById_Inexistent_City() {
        Optional<City> expected = Optional.empty();
        Integer id = 100;
        when(cityRepository.getById(id)).thenReturn(null);
        Optional<City> actual = service.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate_Payload_With_Unique_Name() {
        Integer expected = 3;
        City brest = new City(1,"Brest");
        City gomel = new City(2,"Gomel");
        when(cityRepository.getAll()).thenReturn(Arrays.asList(brest, gomel));
        when(cityRepository.create(any(City.class))).thenReturn(3);
        CityPayload minskPayload = new CityPayload("Minsk");
        Integer actual = service.create(minskPayload);
        assertEquals(expected, actual);
    }
    
    @Test(expected = ServiceException.class)
    public void testCreate_Payload_With_Same_Name_ThrownException() {
        City brest = new City(1,"Brest");
        City gomel = new City(2,"Gomel");
        City minsk = new City(3,"Minsk");
        when(cityRepository.getAll()).thenReturn(Arrays.asList(brest, gomel, minsk));
        when(cityRepository.create(any(City.class))).thenReturn(3);
        CityPayload minskPayload = new CityPayload("Minsk");
        Integer actual = service.create(minskPayload);
        assertNull(actual);
    }

    @Test
    public void testUpdate_Existent_City() {
        Set<CityPlace> places = new HashSet<CityPlace>();
        places.add(new CityPlace(1, "Visit the Upper Town"));
        City brest = new City(1,"Brest");
        City gomel = new City(2,"Gomel");
        City minsk = new City(3,"Mensk", places);
        places.add(new CityPlace(2, "See the grandeur of Bolshoi Opera and Ballet Theatre of Belarus"));
        places.add(new CityPlace(3, "Walk the central part of Nezavisimosti Avenue"));
        when(cityRepository.getAll()).thenReturn(Arrays.asList(brest, gomel, minsk));
        when(cityRepository.getById(3)).thenReturn(minsk);
        when(cityPlaceRepository.getById(2))
                .thenReturn(new CityPlace(2, "See the grandeur of Bolshoi Opera and Ballet Theatre of Belarus"));
        when(cityPlaceRepository.getById(3))
                .thenReturn(new CityPlace(3, "Walk the central part of Nezavisimosti Avenue"));
        Set<IdPayload> placesIDs = new HashSet<IdPayload>();
        placesIDs.addAll(Arrays.asList(new IdPayload(2), new IdPayload(3)));
        CityWithPlacesIDsPayload cityPayload = new CityWithPlacesIDsPayload("Minsk",placesIDs);
        service.update(3, cityPayload);
        verify(cityRepository, times(1)).getAll();
        verify(cityRepository, times(1)).update(any(City.class));
        verify(cityPlaceRepository, times(2)).getById(any(Integer.class));
    }
    
    @Test(expected = ServiceException.class)
    public void testUpdate_Existent_City_And_Payload_With_Same_Name_ThrownException() {
        City brest = new City(1,"Brest");
        City gomel = new City(2,"Gomel");
        City minsk = new City(3,"Minsk");
        City capital = new City(4,"Minsk-capital");
        when(cityRepository.getById(3)).thenReturn(minsk);
        when(cityRepository.getAll()).thenReturn(Arrays.asList(brest, gomel, minsk, capital));
        Set<IdPayload> placesIDs = Collections.emptySet();
        CityWithPlacesIDsPayload cityPayload = new CityWithPlacesIDsPayload("Minsk-capital", placesIDs);
        service.update(3, cityPayload);
        verify(cityRepository, times(1)).getAll();
        verify(cityRepository, times(0)).update(any(City.class));
    }
    
    @Test(expected = ServiceException.class)
    public void testUpdate_Inexistent_City_ThrownException() {
        Set<IdPayload> placesIDs = Collections.emptySet();
        when(cityRepository.getById(3)).thenReturn(null);
        CityWithPlacesIDsPayload cityPayload = new CityWithPlacesIDsPayload("Minsk-capital", placesIDs);
        service.update(3, cityPayload);
        verify(cityRepository, times(0)).getAll();
        verify(cityRepository, times(0)).update(any(City.class));
    }

    @Test
    public void testDelete_Existent_City_Without_Places() {
        Integer id = 1;
        when(cityRepository.getById(id)).thenReturn(new City(id, "Minsk", Collections.emptySet()));
        service.delete(id);
        verify(cityRepository, times(1)).delete(any(City.class));
        verify(cityPlaceRepository, times(0)).delete(any(CityPlace.class));
    }
    
    @Test
    public void testDelete_Existent_City_With_Places() {
        Integer id = 1;
        Set<CityPlace> places = new HashSet<CityPlace>();
        places.add(new CityPlace(1, "Visit the Upper Town"));
        places.add(new CityPlace(2, "See the grandeur of Bolshoi Opera and Ballet Theatre of Belarus"));
        places.add(new CityPlace(3, "Walk the central part of Nezavisimosti Avenue"));
        when(cityRepository.getById(id)).thenReturn(new City(id, "Minsk", places));
        service.delete(id);
        verify(cityRepository, times(1)).delete(any(City.class));
        verify(cityPlaceRepository, times(3)).delete(any(CityPlace.class));
    }
    
    @Test(expected = ServiceException.class)
    public void testDelete_Inexistent_City_ThrownException() {
        Integer id = 13;
        when(cityRepository.getById(id)).thenReturn(null);
        service.delete(id);
        verify(cityRepository, times(0)).delete(any(City.class));
    }

}
