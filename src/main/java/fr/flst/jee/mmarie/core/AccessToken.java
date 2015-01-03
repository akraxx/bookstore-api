package fr.flst.jee.mmarie.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Wither;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * {@link fr.flst.jee.mmarie.core.AccessToken} representation.
 */
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Wither
@Setter
public class AccessToken {
	@JsonProperty("access_token_id")
	@NotNull
	private UUID accessTokenId;

	@JsonProperty("user")
	@NotNull
	private User user;

	@JsonProperty("last_access_utc")
	@NotNull
	private DateTime lastAccessUTC;
}
