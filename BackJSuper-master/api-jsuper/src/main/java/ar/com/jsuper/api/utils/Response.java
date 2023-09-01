package ar.com.jsuper.api.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class Response {

    private String message;
    private Integer code;
    private String type;
    private Object data;

    public Response() {
    }

    public Response(Object data, Integer code, String message) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static Response update(Object data) {
        return new Response(data, HttpStatus.OK.value(), "Modificación correcta.");
    }
    public static Response insert(Object data) {
        return new Response(data, HttpStatus.CREATED.value(), "Se agrego correctamente el registro.");
    }
    public static Response ok(Object data) {
        return new Response(data, HttpStatus.OK.value(), "Operación correcta");
    }
    public static Response ok() {
        Map<String, Object> rta= new HashMap<>();
        rta.put("estado", "OK");
        return new Response(rta, HttpStatus.OK.value(), "Operación correcta");
    }
    public static Response notModified() {
        Map<String, Object> rta= new HashMap<>();
        rta.put("estado", "OK");
        return new Response(rta, HttpStatus.NOT_MODIFIED.value(), "No se modifico.");
    }

    public Response(Object data, String message) {
        this.message = message;
        this.data = data;
    }

    public Response(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
