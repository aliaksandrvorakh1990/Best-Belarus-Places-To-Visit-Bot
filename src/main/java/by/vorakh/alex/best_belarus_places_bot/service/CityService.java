package by.vorakh.alex.best_belarus_places_bot.service;

import java.util.List;
import java.util.Optional;

import by.vorakh.alex.best_belarus_places_bot.model.payload.CityPayload;
import by.vorakh.alex.best_belarus_places_bot.model.payload.CityWithPlacesIDsPayload;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;

public interface CityService {

    List<City> getAll();

    Optional<City> getById(Integer id);
    
    Integer create(CityPayload newCityPayload);
    
    void update(Integer id, CityWithPlacesIDsPayload updateCityPayload);

    void delete(Integer id);
    
}
