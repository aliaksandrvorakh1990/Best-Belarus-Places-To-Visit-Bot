package by.vorakh.alex.best_belarus_places_bot.repository;

import java.util.List;

import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;

public interface CityPlaceRepository {

    List<CityPlace> getAll();
    
    List<CityPlace> getAll(String cityName);

    CityPlace getById(Integer id);

    Integer create(CityPlace newCityPlace);

    void update(CityPlace editedCityPlace);

    void delete(CityPlace deletedCityPlace);
    
}
