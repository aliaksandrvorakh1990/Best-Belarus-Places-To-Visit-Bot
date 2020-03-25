package by.vorakh.alex.best_belarus_places_bot.service;

import java.util.List;
import java.util.Optional;

import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;

public interface CityPlaceService {

    List<CityPlace> getAll();

    Optional<CityPlace> getById(Integer id);
    
    Optional<String> getCityPlaceInfo(String cityName);
    
    Integer create(String cityPlaceInfo);
    
    void update(Integer id, String cityPlaceInfo);

    void delete(Integer id);
    
}
