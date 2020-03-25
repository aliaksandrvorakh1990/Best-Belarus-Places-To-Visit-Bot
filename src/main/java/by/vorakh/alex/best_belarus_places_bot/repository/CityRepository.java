package by.vorakh.alex.best_belarus_places_bot.repository;

import java.util.List;

import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;

public interface CityRepository {
    
    List<City> getAll();

    City getById(Integer id);

    Integer create(City newCity);

    void update(City editedCity);

    void delete(City deletedCity);

}
