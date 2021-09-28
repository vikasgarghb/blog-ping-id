package blog.vgarg.bff.util;

import blog.vgarg.bff.auth.Token;
import blog.vgarg.bff.services.TokenCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenUtil {

    private final TokenCacheManager tokenCacheManager;
    private final RestTemplate restTemplate;

    @Value("${auth.tokenEndpoint}")
    private String tokenEndpoint;

    @Value("${auth.grantType}")
    private String grantType;

    public TokenUtil(TokenCacheManager tokenCacheManager, RestTemplate restTemplate) {
        this.tokenCacheManager = tokenCacheManager;
        this.restTemplate = restTemplate;
    }

    public String getToken(String serviceId, String audience, String clientId, String clientSecret) {
        Token cachedToken = tokenCacheManager.getToken(serviceId);
        if (cachedToken == null || cachedToken.isExpired()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("audience", audience);
            map.add("grant_type", grantType);
            ResponseEntity<Token> response =
                    restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(map, httpHeaders), Token.class);
            tokenCacheManager.updateToken(serviceId, response.getBody());
            return response.getBody().getAccessToken();
        }
        return cachedToken.getAccessToken();
    }
}
