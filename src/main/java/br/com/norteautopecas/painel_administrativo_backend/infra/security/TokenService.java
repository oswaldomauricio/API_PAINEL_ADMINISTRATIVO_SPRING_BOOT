package br.com.norteautopecas.painel_administrativo_backend.infra.security;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${api.security.jwt.secret-key}")
    //ler a propriedade do application.properties
    private String JWT_SECRET;


    public String gerarToken(User user) {
        try {
            var algoritmo = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withIssuer("API Norte.pecas")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algoritmo)
                    .withIssuer("API Norte.pecas")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado", exception);
        }
    }
}
