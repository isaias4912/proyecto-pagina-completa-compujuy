/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.mails.impl;

import ar.com.jsuper.domain.RecuperacionPass;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.services.mails.MailAdminService;
import ar.com.jsuper.services.reportes.ReporteVentas;
import ar.com.jsuper.utils.NetworkUtil;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author rafa
 */
@Service
public class MailAdminServiceImpl implements MailAdminService {

    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ReporteVentas reporteVentas;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MailAdminServiceImpl.class);
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    protected String from;

    @Override
    @Async
    public void sendAdminNewUser(Usuarios usuario) {
        try {
            NetworkUtil networkUtil = new NetworkUtil();
            Map<String, Object> model = new HashMap();
            model.put("user", usuario.getUsuario());
            model.put("email", usuario.getMail());
            model.put("ip", networkUtil.getIp());
            MimeMessage message = this.getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
//		helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
            Context context = new Context();
            context.setVariables(model);
            String html = templateEngine.process("mails/mailAdminNewUser.html", context);
            helper.setTo("rafa2299@gmail.com");
            helper.setText(html, true);
            helper.setSubject("Compujuy - Nuevo Usuario");
            emailSender.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(MailAdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Async
    public void sendNewUser(Usuarios usuario) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("user", usuario.getUsuario());
            model.put("email", usuario.getMail());
            model.put("nombre", usuario.getPersona().getNombre());
            MimeMessage message = this.getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            Context context = new Context();
            context.setVariables(model);
            String html = templateEngine.process("mails/newUser.html", context);
            helper.setTo(usuario.getMail());
            helper.setText(html, true);
            helper.setSubject("Bienvenido a Newstock " + usuario.getPersona().getNombre());
            emailSender.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(MailAdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Async
    public void sendCbtePdf(String[] mails, CbteVenEncDTO cbteVenEncDTO, byte[] pdf) {
        try {
            MimeMessage message = this.getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
            helper.addAttachment("comprobante_" + cbteVenEncDTO.getId() + ".pdf", new ByteArrayResource(pdf));
            Context context = new Context();
            String html = templateEngine.process("mails/cbteVenta.html", context);
            helper.setTo(mails);
            helper.setText(html, true);
            helper.setSubject("Comprobante de venta - Newstock");
            emailSender.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(MailAdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Async
    public void sendMailTest(String[] mails) {
        try {
            MimeMessage message = this.getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
            Context context = new Context();
            String html = templateEngine.process("mails/test.html", context);
            helper.setTo(mails);
            helper.setText(html, true);
            helper.setSubject("Test email - Newstock");
            emailSender.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(MailAdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Async
    public void sendMailRecuperarPass(Usuarios usuario, RecuperacionPass recuperacionPass) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("token", recuperacionPass.getToken());
            model.put("nombre", usuario.getPersona().getNombre());
            MimeMessage message = this.getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
            Context context = new Context();
            context.setVariables(model);
            String html = templateEngine.process("mails/recuperarPass.html", context);
            helper.setTo(usuario.getMail());
            helper.setText(html, true);
            helper.setSubject("Recuperar contraseña - Newstock");
            emailSender.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(MailAdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MimeMessage getMimeMessage() {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(from, "Compujuy"));
        } catch (UnsupportedEncodingException | MessagingException ex) {
            try {
                message.setFrom(from);
            } catch (MessagingException ex1) {
                Logger.getLogger(MailAdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return message;
    }
}
