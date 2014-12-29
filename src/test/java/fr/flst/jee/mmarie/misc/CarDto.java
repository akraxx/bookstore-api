package fr.flst.jee.mmarie.misc;

import fr.flst.jee.mmarie.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;
import org.dozer.Mapping;

/**
 * Created by Maximilien on 29/12/2014.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDto implements Dto<Car> {
    private int id;
    private String name;
    @Mapping("brand.id") private int brandId;
}
