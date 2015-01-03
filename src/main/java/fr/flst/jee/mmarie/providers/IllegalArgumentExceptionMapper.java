package fr.flst.jee.mmarie.providers;

import com.google.common.collect.Lists;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * {@link fr.flst.jee.mmarie.providers.IllegalArgumentExceptionMapper} map the exception {@link java.lang.IllegalArgumentException}
 * thrown in a jersey resource to a custom response.
 */
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    /**
     * Set a response a 422 status and an entity {@link fr.flst.jee.mmarie.providers.ErrorMessage}
     *
     * @param exception Throwed expcetion in jersey resource.
     * @return The custom {@link javax.ws.rs.core.Response}
     */
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(422)
                .entity(new ErrorMessage(Lists.newArrayList(exception.getMessage())))
                .build();
    }
}
