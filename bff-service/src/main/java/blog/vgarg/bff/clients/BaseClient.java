package blog.vgarg.bff.clients;

import blog.vgarg.bff.util.TokenUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class BaseClient {
    private final TokenUtil tokenUtil;
    private final RestTemplate restTemplate;

    private final String serviceId;

    public BaseClient(TokenUtil tokenUtil, RestTemplate restTemplate, String serviceId) {
        this.tokenUtil = tokenUtil;
        this.restTemplate = restTemplate;
        this.serviceId = serviceId;
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected HttpEntity<String> getRequest(String data, String audience, String clientId, String clientSecret) {
        String accessToken = tokenUtil.getToken(serviceId, audience, clientId, clientSecret);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (data == null) {
            return new HttpEntity<>(httpHeaders);
        }
        return new HttpEntity<>(data, httpHeaders);
    }
}
