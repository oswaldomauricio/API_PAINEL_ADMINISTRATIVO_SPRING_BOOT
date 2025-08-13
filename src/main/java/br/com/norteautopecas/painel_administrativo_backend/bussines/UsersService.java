package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.RegisterUserDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(RegisterUserDTO dados) {
        if (usersRepository.existsByLogin(dados.login())) {
            throw new ValidateException("Usuário já cadastrado com o login: " + dados.login());
        }

        String senhaCriptografada = passwordEncoder.encode(dados.senha());

        User user = new User(dados.login(), senhaCriptografada);

        usersRepository.save(user);
        return user;

    }
}
