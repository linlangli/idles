package io.github.grooters.idles.model.interfac;

public interface ModelCallBack<T> {

    void success(T data);

    void failure(String message);
}
