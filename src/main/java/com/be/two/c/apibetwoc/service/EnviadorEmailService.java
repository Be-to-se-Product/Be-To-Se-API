package com.be.two.c.apibetwoc.service;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;


@Service
public class EnviadorEmailService {

//    @Autowired
//    private JavaMailSender mailSender;

//    @Autowired
//    private TaskExecutor taskExecutor;

    private static Log log = LogFactory.getLog(EnviadorEmailService.class);

    public void sendEmail(final String remetente, final String destinatario, final String titulo, final String conteudo) {
        System.out.printf("""
                E-mail enviado corretamente de %s para :%s 
                Título da mensagem : %s                        
                Conteúdo da mensagem: 
                %s 
                                                               
                """, remetente, destinatario, titulo, conteudo);
//        taskExecutor.execute(() -> sendMailSimple(remetente, destinatario, titulo, conteudo));
    }

//    private void sendMailSimple(
//            final String remetente,
//            final String destinatario,
//            final String titulo,
//            final String conteudo
//    ) {
//
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(remetente);
//            helper.setTo(destinatario);
//            helper.setSubject(titulo);
//            helper.setText(conteudo);
//        } catch (MessagingException e) {
//            throw new MailParseException(e);
//        }
//
//        mailSender.send(message);
//
//        if (log.isDebugEnabled()) {
//            log.debug(String.format("Email enviado corretamente para: %s", destinatario));
//        }
//    }
}
