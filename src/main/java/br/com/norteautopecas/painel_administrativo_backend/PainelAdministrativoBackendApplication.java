package br.com.norteautopecas.painel_administrativo_backend;

import br.com.norteautopecas.painel_administrativo_backend.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageConfig.class
})
public class PainelAdministrativoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PainelAdministrativoBackendApplication.class, args);
	}

}
