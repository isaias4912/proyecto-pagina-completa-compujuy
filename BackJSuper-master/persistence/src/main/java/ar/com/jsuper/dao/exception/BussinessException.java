package ar.com.jsuper.dao.exception;

import ar.com.jsuper.aspect.LoggingAspect;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import javassist.NotFoundException;
import javax.xml.rpc.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

public class BussinessException extends Exception {

    private Logger logger = Logger.getLogger(LoggingAspect.class);

    public BussinessException(Exception ex) {
        super(ex);
        logger.error("--------------Exception Bussines---------------");
    }

    public BussinessException(FileNotFoundException ex) {
        super(ex);
        logger.error("--------------FileNotFoundException Bussines---------------");
    }
    public BussinessException(RuntimeException ex) {
        super(ex);
        logger.error("--------------RuntimeException Bussines---------------");
    }

    public BussinessException(NotFoundException ex) {
        super(ex);
        logger.error("--------------Not Found Bussines---------------");
    }

    public BussinessException(ServiceException ex) {
        super("Error en el servicio, verifique su conexión o intente más tarde.", ex);
        logger.error("--------------ServiceException---------------");
    }
    public BussinessException(RemoteException ex) {
        super("Error, verifique su conexión o intente más tarde.", ex);
        logger.error("--------------RemoteException---------------");
    }
    public BussinessException(IOException ex) {
        super("Erro, verifique la URL o la conexión, o intente más tarde.", ex);
        logger.error("--------------IOException---------------");
    }
    public BussinessException(UnknownHostException ex) {
        super(ex);
        logger.error("--------------UnknownHostException---------------");
    }
    public BussinessException(SocketException ex) {
        super(ex);
        logger.error("--------------SocketException---------------");
    }
    public BussinessException(GeneralSecurityException ex) {
        super(ex);
        logger.error("--------------GeneralSecurityException---------------");
    }

    public BussinessException(DataIntegrityViolationException ex) {
        logger.error("--------------DataIntegrityViolationException Bussines---------------");
        if (ex.getMessage() == null || "".equals(ex.getMessage().trim())) {
            throw new DataIntegrityViolationException("Existen problemas de inconsistencia, no se puedo realizar la peticion solicitada, verifique los datos.");
        } else {
            throw ex;
        }
    }

}
