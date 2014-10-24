package fr.flst.jee.mmarie.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Maximilien on 23/10/2014.
 */
public class CrossDomainFilter implements ContainerResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrossDomainFilter.class);



    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        LOGGER.info("Filter cross domain");
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        containerResponse.getHttpHeaders().add("Access-Control-Max-Age", "1209600");
        return containerResponse;
    }
}
