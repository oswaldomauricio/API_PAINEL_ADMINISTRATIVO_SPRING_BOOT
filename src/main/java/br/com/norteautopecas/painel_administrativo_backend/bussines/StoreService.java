package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.RegisterStoreDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.StoreRegistrationDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Store;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.user.StoreRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.user.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UsersRepository usersRepository;

    public StoreRegistrationDetailsDTO registerStore(RegisterStoreDTO dados) {
        if (storeRepository.existsByIdAndLoja(dados.idUser(), dados.loja())) {
            throw new ValidateException("O Usuário já está cadastrado na loja: " + dados.loja());
        }

        var user = usersRepository.findById(dados.idUser()).orElseThrow(() -> new ValidateException("Usuário não encontrado com ID: " + dados.idUser()));

        Store store = new Store(dados.loja(), user);

        var saveStore = storeRepository.save(store);

        return new StoreRegistrationDetailsDTO(
                user.getId(),
                saveStore.getLoja(),
                saveStore.getUser());
    }
}
