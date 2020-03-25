package by.vorakh.alex.best_belarus_places_bot.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import by.vorakh.alex.best_belarus_places_bot.repository.CityRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;

@Repository
public class CityRepositoryImpl implements CityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<City> getAll() {
        return (List<City>) entityManager.createQuery("select c from City c", City.class).getResultList();
    }

    @Override
    public City getById(Integer id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public Integer create(City newCity) {
        entityManager.persist(newCity);
        entityManager.flush();
        return newCity.getId();
    }

    @Override
    public void update(City editedCity) {
        entityManager.merge(editedCity);
    }

    @Override
    public void delete(City deletedCity) {
        entityManager.remove(deletedCity);
    }

}
