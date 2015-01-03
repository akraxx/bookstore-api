package fr.flst.jee.mmarie.misc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.flst.jee.mmarie.dto.Dto;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link fr.flst.jee.mmarie.misc.DtoMappingService} makes the relation between dto and it's model classes.
 */
@Singleton
public class DtoMappingService {

    private final Mapper mapper;

    @Inject
    public DtoMappingService(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Converts the model {@code T} to it's dto {@code D} using the {@code mapper} instance.
     *
     * @param objectToMap Model to converts.
     * @param dtoClass Class of the dto.
     * @param <T> Class of the model.
     * @param <D> Class of the dto.
     * @return Instance of {@code dtoClass}, which is the converted {@code objectToMap}.
     */
    public <T, D extends Dto<T>> D convertsToDto(T objectToMap, Class<D> dtoClass) {
        return mapper.map(objectToMap, dtoClass);
    }

    /**
     * Converts the list of models {@code T} to a list of dto {@code D} using the {@code mapper} instance.
     *
     * @param objectsToMap List of model to converts.
     * @param dtoClass Class of the dto.
     * @param <T> Class of the model.
     * @param <D> Class of the dto.
     * @return List of instances of {@code dtoClass}, which is the converted list {@code objectsToMap}.
     */
    public <T, D extends Dto<T>> List<D> convertsListToDto(List<T> objectsToMap, Class<D> dtoClass) {
        List<D> dtos = new ArrayList<>(objectsToMap.size());

        for(Object object : objectsToMap) {
            dtos.add(mapper.map(object, dtoClass));
        }

        return dtos;
    }

    /**
     * Converts the dto {@code D} to the model {@code T} using the {@code mapper} instance.
     *
     * @param objectToMap Dto to converts.
     * @param modelClass Class of the model.
     * @param <T> Class of the model.
     * @param <D> Class of the dto.
     * @return Instance of {@code modelClass}, which is the converted {@code objectToMap}.
     */
    public <T, D extends Dto<T>> T convertsToModel(D objectToMap, Class<T> modelClass) {
        return mapper.map(objectToMap, modelClass);
    }

}
