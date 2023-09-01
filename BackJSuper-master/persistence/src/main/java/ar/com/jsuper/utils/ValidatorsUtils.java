/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author rafael
 */
public class ValidatorsUtils {

    private static final String regexEmail = "^(.+)@(.+)$";

    public static Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isValidDNI(String dni) {
        if ("".equals(dni)) {
            throw new DataIntegrityViolationException("El DNI no puede ser vacio");
        }
        if (dni.trim().length() < 7) {
            throw new DataIntegrityViolationException("El DNI es muy corto.");
        }
        if (dni.trim().length() > 8) {
            throw new DataIntegrityViolationException("El DNI es muy largo");
        }
        return true;
    }
}
