package by.vorakh.alex.best_belarus_places_bot.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.vorakh.alex.best_belarus_places_bot.repository.CityPlaceRepository;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;
import by.vorakh.alex.best_belarus_places_bot.service.CityPlaceService;
import by.vorakh.alex.best_belarus_places_bot.service.exception.ServiceException;

@Service
public class CityPlaceServiceImpl implements CityPlaceService {
    
    private final static String INEXISTENT_CITY_PLACE = "A city place with this ID does not exists.";

    @Autowired
    private CityPlaceRepository repository;
    
    @Override
    public List<CityPlace> getAll() {
        return repository.getAll();
    }

    @Override
    public Optional<CityPlace> getById(Integer id) {
        return Optional.ofNullable(repository.getById(id));
    }

    @Override
    public Optional<String> getCityPlaceInfo(String cityName) {
        return repository.getAll(cityName).stream().map(cityplace -> cityplace.getInfo()).distinct()
                .reduce((old, current)-> old + "\n" + current);
    }

    @Override
    @Transactional
    public Integer create(String cityPlaceInfo) {
        return repository.create(new CityPlace(cityPlaceInfo));
    }

    @Override
    @Transactional
    public void update(@Valid @NotNull Integer id, String cityPlaceInfo) {
        CityPlace updatedCityPlace = Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new ServiceException(INEXISTENT_CITY_PLACE));
        updatedCityPlace.setInfo(cityPlaceInfo);
        repository.update(updatedCityPlace);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        CityPlace deletedCityPlace = Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new ServiceException(INEXISTENT_CITY_PLACE));
        repository.delete(deletedCityPlace);
    }

}
