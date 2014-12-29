package fr.flst.jee.mmarie.providers;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class IllegalArgumentExceptionMapperTest {

    private IllegalArgumentExceptionMapper mapper = new IllegalArgumentExceptionMapper();
    @Test
    public void testToResponse() throws Exception {
        IllegalArgumentException argumentException = new IllegalArgumentException("custom exception");
        Response response = mapper.toResponse(argumentException);

        assertThat(response, allOf(
                hasProperty("status", is(422)),
                hasProperty("entity", isA(ErrorMessage.class))
        ));
    }
}