package fr.flst.jee.mmarie.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * Created by Maximilien on 29/12/2014.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Brand {
    private int id;
    private String name;
}
