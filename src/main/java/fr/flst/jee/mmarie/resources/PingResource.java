package fr.flst.jee.mmarie.resources;

import com.codahale.metrics.annotation.Timed;
import fr.flst.jee.mmarie.core.User;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
	@GET
	@Timed
	public String pong() {
		return "{\"answer\": \"pong\"}";
	}

	@GET
	@Timed
	@Path("/auth")
	public String pongAuthenticated(@Auth User user) {
		return String.format("{\"answer\": \"authenticated pong for user %s\"}", user);
	}
}
