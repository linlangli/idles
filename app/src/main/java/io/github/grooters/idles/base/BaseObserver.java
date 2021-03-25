package io.github.grooters.idles.base;

import com.orhanobut.logger.Logger;

import io.github.grooters.idles.net.Server;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) { }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        Logger.e("接口地址: " + Server.localUrl + "login" + "\n错误类型：" + e.getMessage());
        handleError();
    }

    @Override
    public void onComplete() { }

    public void handleError(){ }

}
