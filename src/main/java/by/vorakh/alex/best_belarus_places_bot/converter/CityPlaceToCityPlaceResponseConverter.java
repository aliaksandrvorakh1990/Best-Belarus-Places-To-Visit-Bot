package by.vorakh.alex.best_belarus_places_bot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import by.vorakh.alex.best_belarus_places_bot.model.response.CityPlaceResponse;
import by.vorakh.alex.best_belarus_places_bot.repository.entity.CityPlace;

@Component
public class CityPlaceToCityPlaceResponseConverter implements Converter<CityPlace, CityPlaceResponse> {

    @Override
    public CityPlaceResponse convert(CityPlace source) {
        return new CityPlaceResponse(source.getId(), source.getInfo());
    }

}
