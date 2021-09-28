package blog.vgarg.bff.services;

import blog.vgarg.bff.auth.Token;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenCacheManager {
    private static Map<String, Token> _cache;

    public TokenCacheManager() {
        _cache = new HashMap<>();
    }

    public Token getToken(String serviceId) {
        return _cache.get(serviceId);
    }

    public void updateToken(String serviceId, Token token) {
        _cache.put(serviceId, token);
    }
}
