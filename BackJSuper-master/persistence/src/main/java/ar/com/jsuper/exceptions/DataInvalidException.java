package ar.com.jsuper.exceptions;

import ar.com.jsuper.utils.ErrorVentaType;
import org.springframework.dao.DataIntegrityViolationException;

public class DataInvalidException extends DataIntegrityViolationException {

    private ErrorVentaType type;

    public DataInvalidException(String msg) {
        super(msg);
    }

    public DataInvalidException(String msg, ErrorVentaType type) {
        super(msg);
        this.type = type;
    }

    public DataInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ErrorVentaType getType() {
        return type;
    }

    public void setType(ErrorVentaType type) {
        this.type = type;
    }
}
