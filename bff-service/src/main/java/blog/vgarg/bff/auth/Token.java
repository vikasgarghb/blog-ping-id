package blog.vgarg.bff.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class Token implements Serializable {
    private static final long serialVersionUID = -6664580403217243608L;
    @JsonProperty(value = "access_token")
    private final String accessToken;

    @JsonProperty(value = "token_type")
    private final String tokenType;

    @JsonProperty(value = "expires_in")
    private final int expiresIn;
    private final long expireAt;

    public Token() {
        this.accessToken = null;
        this.tokenType = null;
        this.expiresIn = -1;
        this.expireAt = -1L;
    }

    public Token(String accessToken, String tokenType, int expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.expireAt = (((new Date()).getTime()) / 1000) + expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public boolean isExpired() {
        return (((new Date()).getTime()) / 1000 - expireAt) >= 0;
    }
}
