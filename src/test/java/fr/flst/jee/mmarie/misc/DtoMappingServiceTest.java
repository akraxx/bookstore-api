package fr.flst.jee.mmarie.misc;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class DtoMappingServiceTest {

    private Mapper mapper = new DozerBeanMapper();

    private DtoMappingService dtoMappingService;

    @Before
    public void setUp() throws Exception {
        dtoMappingService = new DtoMappingService(mapper);
    }

    @Test
    public void testConvertsToDto() throws Exception {
        Car car = Car.builder().id(1).name("Polo").brand(Brand.builder().id(5).name("Volkswagen").build()).build();
        CarDto carDto = dtoMappingService.convertsToDto(car, CarDto.class);

        assertThat(carDto, allOf(
                hasProperty("id", is(1)),
                hasProperty("name", is("Polo")),
                hasProperty("brandId", is(5))
        ));
    }

    @Test
    public void testConvertsListToDto() throws Exception {
        List<Car> cars = Arrays.asList(
                Car.builder().id(1).name("Polo").brand(Brand.builder().id(5).name("Volkswagen").build()).build(),
                Car.builder().id(2).name("Veyron").brand(Brand.builder().id(11).name("Bugatti").build()).build()
        );

        List<CarDto> carsDto = dtoMappingService.convertsListToDto(cars, CarDto.class);

        assertThat(carsDto, hasItems(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("Polo")),
                                hasProperty("brandId", is(5))
                        ),
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is("Veyron")),
                                hasProperty("brandId", is(11))
                        )
                )
        );

    }

    @Test
    public void testConvertsToModel() throws Exception {
        CarDto carDto = CarDto.builder().id(1).name("Polo").brandId(5).build();
        Car car = dtoMappingService.convertsToModel(carDto, Car.class);

        assertThat(car, allOf(
                hasProperty("id", is(1)),
                hasProperty("name", is("Polo")),
                hasProperty("brand", hasProperty("id", is(5)))
        ));
    }
}