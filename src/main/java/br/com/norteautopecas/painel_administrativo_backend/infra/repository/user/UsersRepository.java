package br.com.norteautopecas.painel_administrativo_backend.infra.repository.user;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsersRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}