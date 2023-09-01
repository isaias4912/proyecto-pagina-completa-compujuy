/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.mails;

import ar.com.jsuper.domain.RecuperacionPass;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.dto.CbteVenEncDTO;

/**
 *
 * @author rafael
 */
public interface MailAdminService {

    void sendAdminNewUser(Usuarios usuario);

    public void sendNewUser(Usuarios usuario);

    public void sendMailRecuperarPass(Usuarios usuarios, RecuperacionPass recuperacionPass);

    public void sendCbtePdf(String[] mails, CbteVenEncDTO cbteVenEncDTO, byte[] pdf);

    public void sendMailTest(String[] mails);

}
