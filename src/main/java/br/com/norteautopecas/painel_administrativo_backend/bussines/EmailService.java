package br.com.norteautopecas.painel_administrativo_backend.bussines;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${spring.mail.setor.responsavel}")
    private String setorResponsavel;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envia email em HTML usando template
     */
    public void enviarEmailHtml(String para, String assunto, Map<String,
            String> variaveis, String templateName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setSubject(assunto);
            helper.setTo(para);
            helper.setCc(setorResponsavel);
            String template = carregarTemplateEmail(templateName);

            for (Map.Entry<String, String> entry : variaveis.entrySet()) {
                template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            helper.setText(template, true);

            mailSender.send(message);
            log.info("E-mail enviado para {} e em copia para o email {}",
                    para, setorResponsavel);

        } catch (Exception ex) {
            log.error("Falha ao enviar o email!", ex);
        }
    }

    private String carregarTemplateEmail(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }


}
