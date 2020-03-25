package by.vorakh.alex.best_belarus_places_bot.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import by.vorakh.alex.best_belarus_places_bot.repository.CityPlaceRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;

@Repository
public class CityPlaceRepositoryImpl implements CityPlaceRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Value("${city.places}")
    private int maxPlaces;

    @Override
    public List<CityPlace> getAll() {
        return entityManager.createQuery("select p from CityPlace p", CityPlace.class).getResultList();
    }
    
    @Override
    public List<CityPlace> getAll(String cityName) {
        String query = "SELECT p FROM City c JOIN c.places p WHERE c.name = :n ORDER BY random()";
        return entityManager.createQuery(query, CityPlace.class).setParameter("n", cityName).setMaxResults(maxPlaces)
                .getResultList();
    }

    @Override
    public CityPlace getById(Integer id) {
        return entityManager.find(CityPlace.class, id);
    }

    @Override
    public Integer create(CityPlace newCityPlace) {
        entityManager.persist(newCityPlace);
        entityManager.flush();
        return newCityPlace.getId();
    }

    @Override
    public void update(CityPlace editedCityPlace) {
        entityManager.merge(editedCityPlace);
    }

    @Override
    public void delete(CityPlace deletedCityPlace) {
        entityManager.remove(deletedCityPlace);
    }

}
