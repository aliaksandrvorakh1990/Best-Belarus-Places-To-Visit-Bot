package by.vorakh.alex.best_belarus_places_bot.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import by.vorakh.alex.best_belarus_places_bot.model.response.CityPlaceResponse;
import by.vorakh.alex.best_belarus_places_bot.model.response.CityResponse;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.City;

@Component
public class CityToCityResponseConverter implements Converter<City, CityResponse> {
    
    @Autowired
    private CityPlaceToCityPlaceResponseConverter placeConverter;
    
    @Override
    public CityResponse convert(City source) {
        Integer id = source.getId();
        String name = source.getName();
        Set<CityPlaceResponse> places = source.getPlaces().stream().map(placeConverter::convert)
                .collect(Collectors.toSet());
        return new CityResponse(id, name, places);
    }

}
