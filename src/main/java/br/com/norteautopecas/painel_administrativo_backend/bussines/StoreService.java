package br.com.norteautopecas.painel_administrativo_backend.bussines;

import br.com.norteautopecas.painel_administrativo_backend.infra.dto.ListStoreByUserDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.ListStoreInformationDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.RegisterStoreDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.dto.StoreRegistrationDetailsDTO;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.Store;
import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreInformationRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.StoreRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.repository.UsersRepository;
import br.com.norteautopecas.painel_administrativo_backend.infra.validations.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreInformationRepository storeInformationRepository;

    @Autowired
    private UsersRepository usersRepository;


    public StoreRegistrationDetailsDTO registerStore(RegisterStoreDTO dados) {
        if (storeRepository.countByUserIdAndLoja(dados.idUser(),
                dados.loja()) > 0) {
            System.out.println("Usuário já cadastrado na loja: " + dados.loja());
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

    public List<ListStoreByUserDTO> listStoresByUser(Long userId) {
        var userStores = storeRepository.findAllByUserId(userId);

        if (userStores.isEmpty()) {
            throw new ValidateException("Nenhuma loja encontrada para o usuário com ID: " + userId);
        }

        return userStores.stream()
                .map(s -> new ListStoreByUserDTO(s.getUser().getId(),
                        s.getUser().getLogin(),
                        s.getLoja()))
                .collect(Collectors.toList());
    }

    public List<ListStoreInformationDTO> getAllStores() {
        var stores = storeInformationRepository.findAll();
        if (stores.isEmpty()) {
            throw new ValidateException("Nenhuma loja encontrada.");
        }

        return stores.stream()
                .map(store -> new ListStoreInformationDTO(
                        store.getLoja(),
                        store.getNomeLoja(),
                        store.getEnderecoLoja(),
                        store.getEstado(),
                        store.getSigla(),
                        store.getCidade(),
                        store.getEmail(),
                        store.getLatitude(),
                        store.getLongitude(),
                        store.getSegmentacao(),
                        store.getSegmentacao2(),
                        store.getTelefone(),
                        store.getWhatsapp(),
                        store.getHorario()))
                .collect(Collectors.toList());

    }
}
