package ar.com.jsuper.dto;

public class SingleObject<T> {
    private T value;

    public SingleObject(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
