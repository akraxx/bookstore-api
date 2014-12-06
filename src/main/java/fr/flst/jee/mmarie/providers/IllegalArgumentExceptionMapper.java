package fr.flst.jee.mmarie.providers;

import com.google.common.collect.Lists;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Maximilien on 14/11/2014.
 */
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(422)
                .entity(new ErrorMessage(Lists.newArrayList(exception.getMessage())))
                .build();
    }
}
