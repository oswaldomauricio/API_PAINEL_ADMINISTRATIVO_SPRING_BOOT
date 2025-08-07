package br.com.norteautopecas.painel_administrativo_backend.infra.security;

import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // essa anotação é usada de forma generica quando queremos que o
// spring carregue, porem, nao sabemos o que é exatamente a classe.
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsersRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            // aqui estamos recuperando o subject do token, que é o login do usuário.
            var subject = tokenService.getSubject(tokenJWT);
            var user = repository.findByLogin(subject);

            var authentication =
                    new UsernamePasswordAuthenticationToken(user, null,
                            user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization != null) {
            return authorization.replace("Bearer ", "").trim();
        }
        return null;
    }
}
