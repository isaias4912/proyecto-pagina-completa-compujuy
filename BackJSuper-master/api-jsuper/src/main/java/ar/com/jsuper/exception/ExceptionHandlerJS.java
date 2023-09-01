package ar.com.jsuper.exception;

import ar.com.jsuper.exceptions.DataInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dao.exception.DataException;
import ar.com.jsuper.exceptions.JSuperException;
import ar.com.jsuper.utils.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.UnknownHostException;
import java.util.Locale;

import javassist.NotFoundException;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@ControllerAdvice
public class ExceptionHandlerJS {

    private Logger logger = Logger.getLogger(ExceptionHandlerJS.class);
    @Autowired
    private FileUtils fileUtils;

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Response> fileNotFoundExceptionHandler(FileNotFoundException ex) {
        logger.error("FileNotFoundException", ex);
        String msg = this.replaceMSG(ex.getMessage());
        Response error = new Response();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("FileNotFoundException- Hubo un error:" + msg);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Response> iOExceptionExceptionHandler(IOException ex) {
        logger.error("FileNotFoundException", ex);
        String msg = this.replaceMSG(ex.getMessage());
        Response error = new Response();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("FileNotFoundException- Hubo un error:" + msg);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> notFoundExceptionHandler(NotFoundException ex) {
        logger.error("NotFoundException", ex);
        Response error = new Response();
        String msg = this.replaceMSG(ex.getMessage());
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("NotFoundException- Hubo un error:" + msg);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> BadCredentialsExceptionHandler(BadCredentialsException ex) {
        logger.error("BadCredentialsException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage("NotFoundException- Hubo un error:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OAuth2Exception.class)
    public ResponseEntity<Response> oAuth2ExceptionHandler(OAuth2Exception ex) {
        logger.error("OAuth2Exception", ex);
        Response error = new Response();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage("OAuth2Exception- Hubo un error:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<Response> UnknownHostExceptionHandler(UnknownHostException ex) {
        logger.error("UnknownHostException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage("UnknownHostException- Hubo un error:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataException.class)
    public ResponseEntity<Response> dataIntegrityExceptionHandler(DataException ex) {
        logger.error("DataException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setData(ex.getData());
        if (ex.getMessage() == null || "".equals(ex.getMessage().trim())) {
            error.setMessage("Existen problemas, no se puedo realizar la peticion solicitada, verifique los datos.");
        } else {
            String msg = this.replaceMSG(ex.getMessage());
            error.setMessage(msg);
        }
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JSuperException.class)
    public ResponseEntity<Response> jsuperExceptionHandler(JSuperException ex) {
        logger.error("JSuperException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setData(ex.getError());
        if (ex.getMessage() == null || "".equals(ex.getMessage().trim())) {
            error.setMessage("Existen problemas de inconsistencia, no se puedo realizar la peticion solicitada, verifique los datos.");
        } else {
            String msg = this.replaceMSG(ex.getMessage());
            error.setMessage(msg);
        }
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataInvalidException.class)
    public ResponseEntity<Response> dataInvalidException(DataInvalidException ex) {
        logger.error("DataInvalidException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setType(ex.getType().name());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> dataIntegrityExceptionHandler(DataIntegrityViolationException ex) {
        logger.error("DataIntegrityViolationException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        if (ex.getMessage() == null || "".equals(ex.getMessage().trim())) {
            error.setMessage("Existen problemas de datos, no se puedo realizar la peticion solicitada, verifique los datos.");
        } else {
            // let's check if contains phrases sql
            if (ex.getMessage().toLowerCase(Locale.ROOT).indexOf("sql") != -1) {
                error.setMessage("Existen problemas de datos, no se puedo realizar la peticion solicitada, verifique los datos.");
            } else {
                String msg = this.replaceMSG(ex.getMessage());
                error.setMessage(msg);
            }
        }
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Response> httpMessageNotWritableException(HttpMessageNotWritableException ex) {
        logger.error("HttpMessageNotWritableException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        if (ex.getMessage() != null && ex.getMessage().trim() != "") {
            error.setMessage("Existen problemas de inconsistencia, no se puedo realizar la peticion solicitada, verifique los datos.");
        } else {
            error.setMessage(ex.getMessage());
        }
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> httpMConstraintViolationException(ConstraintViolationException ex) {
        logger.error("ConstraintViolationException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        if (ex.getMessage() != null && ex.getMessage().trim() != "") {
            error.setMessage("Existen problemas de inconsistencia, no se puedo realizar la peticion solicitada, verifique los datos.");
        } else {
            error.setMessage(ex.getMessage());
        }
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AxisFault.class)
    public ResponseEntity<Response> axisExceptionHandler(AxisFault ex) {
        logger.error("AxisFault Exception", ex);
        Response error = new Response();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage("AxisFault Exception- Hubo un error:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<Response> hibernateExceptionHandler(HibernateException ex) {
        logger.error("HibernateException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Hibernate Exception- Hubo un error:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> accessDeniedException(AccessDeniedException ex) {
        logger.error("AccessDeniedException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage("AccessDeniedException- No autorizado:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> runtimeExceptionHandler(RuntimeException ex) {
        logger.error("RuntimeException", ex);
        Response error = new Response();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("RunTimeException- Hubo un error:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handle(Exception ex) {
        logger.error("Exception", ex);
        Response error = new Response();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("Exception- Hubo un error:" + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Remplaza algunos msj de las exc, como ser ubicaciones de archivos, para seguridad
     *
     * @return
     */
    private String replaceMSG(String msg) {
        msg = msg.replaceAll(fileUtils.getDirApp(), "");
        msg = msg.replaceAll("/", "");
        msg = msg.replace("afip", "");
        msg = msg.replace(fileUtils.getDirImagenesAPP(), "");
        msg = msg.replace(fileUtils.getDirImagenesConfig(), "");
        msg = msg.replace(fileUtils.getDirImagenesProd(), "");
        msg = msg.replace(fileUtils.getDirImagenesUsers(), "");
        return msg;
    }
}
