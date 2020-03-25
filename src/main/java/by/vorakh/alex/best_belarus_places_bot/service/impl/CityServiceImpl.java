package by.vorakh.alex.best_belarus_places_bot.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.vorakh.alex.best_belarus_places_bot.model.payload.CityPayload;
import by.vorakh.alex.best_belarus_places_bot.model.payload.CityWithPlacesIDsPayload;
import by.vorakh.alex.best_belarus_places_bot.model.payload.IdPayload;
import by.vorakh.alex.best_belarus_places_bot.repository.CityPlaceRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.CityRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;
import by.vorakh.alex.best_belarus_places_bot.service.CityService;
import by.vorakh.alex.best_belarus_places_bot.service.exception.ServiceException;

@Service
public class CityServiceImpl implements CityService {
    
    private final static String INEXISTENT_CITY = "A city place with this ID does not exists. ";
    private final static String CONTAIN_SAME_NAME = "The City with the same name exist in the database.";
    
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityPlaceRepository cityPlaceRepository;

    @Override
    public List<City> getAll() {
        return cityRepository.getAll();
    }

    @Override
    public Optional<City> getById(Integer id) {
        return Optional.ofNullable(cityRepository.getById(id));
    }

    @Override
    @Transactional
    public Integer create(CityPayload newCityPayload) {
        String cityName = newCityPayload.getName();
        if (isContain(cityName)) {
            throw new ServiceException(INEXISTENT_CITY);
        }
        return cityRepository.create(new City(cityName));
    }

    @Override
    @Transactional
    public void update(Integer id, CityWithPlacesIDsPayload updateCityPayload) {
        String newCityName = updateCityPayload.getName();
        City updatedCity = Optional.ofNullable(cityRepository.getById(id))
                .orElseThrow(() -> new ServiceException(INEXISTENT_CITY));
        String oldCityName = updatedCity.getName();
        if (!oldCityName.equals(newCityName)) {
            if (isContain(newCityName)) {
                throw new ServiceException(CONTAIN_SAME_NAME);
            }
            updatedCity.setName(newCityName);
        }
        Set<IdPayload> existedPlacesIds = updateCityPayload.getPlaceIds();
        Set<CityPlace> existedCityPlaces = getExistedCityPlacesBy(existedPlacesIds);
        updatedCity.setPlaces(existedCityPlaces);
        cityRepository.update(updatedCity);
    }
    
    @Override
    @Transactional
    public void delete(Integer id) {
        City deletedCity = Optional.ofNullable(cityRepository.getById(id))
                .orElseThrow(() -> new ServiceException(INEXISTENT_CITY));
        deletedCity.getPlaces().stream().forEach(cityPlaceRepository::delete);
        cityRepository.delete(deletedCity);
    }
    
    private Set<CityPlace> getExistedCityPlacesBy(Collection<IdPayload> placeIds) {
        return placeIds.stream()
                .map(idPayload -> idPayload.getId())
                .map(cityPlaceRepository::getById)
                .filter(place -> place != null)
                .collect(Collectors.toSet());
    }
    
    private boolean isContain(String cityName) {
        long sameNameCounter = cityRepository.getAll().stream().filter(city -> city.getName().equals(cityName)).count();
        final int NO_SAME_NAME = 0;
        return sameNameCounter > NO_SAME_NAME;
    }

}
