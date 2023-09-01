package ar.com.jsuper.api.utils;

public interface JsonTransformer {
    String toJson(Object data);
    Object fromJson(String json, Class clazz);
}
