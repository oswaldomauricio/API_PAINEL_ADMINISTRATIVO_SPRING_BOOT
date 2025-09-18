package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.RegisterUserDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.users.UserRegistrationDataDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.enums.Roles;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.User;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        User user = new User(dados.login(), senhaCriptografada, dados.email(), dados.role());

        usersRepository.save(user);
        return user;

    }

    public UserRegistrationDataDTO alterarRegraDeUsuario(Long id, Roles novaRole) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Usuário não encontrado com o ID: " + id));

        if (!user.getRole().equals(novaRole)) {
            user.setUpdatedAt(LocalDateTime.now());
            user.setRole(novaRole);
            user = usersRepository.save(user);
        }

        return new UserRegistrationDataDTO(user.getId(), user.getLogin(),
                user.getEmail(),
                user.getRole());
    }

    public UserRegistrationDataDTO alterarEmailUsuario(Long id, String newEmail) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new ValidateException("Usuário não encontrado com o ID: " + id));

        if (!user.getEmail().equals(newEmail)) {
            user.setUpdatedAt(LocalDateTime.now());
            user.setEmail(newEmail);
            user = usersRepository.save(user);
        }

        return new UserRegistrationDataDTO(user.getId(), user.getLogin(),
                user.getEmail(),
                user.getRole());
    }

    public List<UserRegistrationDataDTO> ListarUsuarios() {

        List<User> users = usersRepository.findAll();

        if (users.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado no sistema.");
        }

        users.stream()
                .map(u -> {
                    return new User(u.getId(), u.getLogin(), u.getSenha(), u.getEmail(), u.getRole(), u.getCreatedAt(), u.getUpdatedAt());
                }).toList();

        return users.stream()
                .map(u -> new UserRegistrationDataDTO(u.getId(), u.getLogin(), u.getEmail(), u.getRole()))
                .collect(Collectors.toList());
    }

}
