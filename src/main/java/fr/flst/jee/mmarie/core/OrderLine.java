package fr.flst.jee.mmarie.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Maximilien on 21/10/2014.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderLine {

    private int quantity;
}
