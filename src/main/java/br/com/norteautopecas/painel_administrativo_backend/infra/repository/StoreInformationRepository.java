package br.com.norteautopecas.painel_administrativo_backend.infra.repository;

import br.com.norteautopecas.painel_administrativo_backend.infra.entity.StoreInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreInformationRepository extends JpaRepository<StoreInformation, Long> {

}
