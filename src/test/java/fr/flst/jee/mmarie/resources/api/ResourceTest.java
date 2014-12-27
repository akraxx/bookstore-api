package fr.flst.jee.mmarie.resources.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maximilien on 06/12/2014.
 */
public class ResourceTest {

    protected ObjectMapper objectMapper = new ObjectMapper();

    public void setTokenAuthorization(ResourceTestRule resources, String token) {
        resources.client().addFilter(new ClientFilter() {
            private List<Object> authorizations;

            @Override
            public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
                if (authorizations == null) {
                    authorizations = new LinkedList<>();
                    authorizations.add("Bearer " + token);
                }
                request.getHeaders().put("Authorization", authorizations);

                return getNext().handle(request);
            }
        });
    }
}
