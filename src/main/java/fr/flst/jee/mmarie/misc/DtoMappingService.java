package fr.flst.jee.mmarie.misc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.flst.jee.mmarie.dto.Dto;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maximilien on 06/12/2014.
 */
@Singleton
public class DtoMappingService {

    private final Mapper mapper;

    @Inject
    public DtoMappingService(Mapper mapper) {
        this.mapper = mapper;
    }

    public <T, D extends Dto<T>> D convertsToDto(T objectToMap, Class<D> dtoClass) {
        return mapper.map(objectToMap, dtoClass);
    }

    public <T, D extends Dto<T>> List<D> convertsListToDto(List<T> objectsToMap, Class<D> dtoClass) {
        List<D> dtos = new ArrayList<>(objectsToMap.size());

        for(Object object : objectsToMap) {
            dtos.add(mapper.map(object, dtoClass));
        }

        return dtos;
    }


}
