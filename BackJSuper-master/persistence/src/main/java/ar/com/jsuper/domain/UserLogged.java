/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;


/**
 * @author rafa22
 */
public class UserLogged {

    private Integer idUser;
    private Integer idApp;
    private String nameUser;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdApp() {
        return idApp;
    }

    public void setIdApp(Integer idApp) {
        this.idApp = idApp;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
