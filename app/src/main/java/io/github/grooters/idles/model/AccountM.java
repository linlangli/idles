package io.github.grooters.idles.model;

import io.github.grooters.idles.base.BaseObserver;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.model.interfac.IAccountM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Code;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.net.api.AccountApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AccountM implements IAccountM {

    @Override
    public void getToken(ModelCallBack<Token> callBack) {
        Retrofiter.getApi(AccountApi.class, Server.localUrl).getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Token>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求失败");
                    }
                });
    }

    @Override
    public void getVerification(String email, String emailServer, String emailServerKey, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(AccountApi.class, Server.localUrl,120).getVerification(email, emailServer, emailServerKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求失败");
                    }
                });
    }

    @Override
    public void register(String email, String password, ModelCallBack<User> callBack) {
        Retrofiter.getApi(AccountApi.class, Server.localUrl).register(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<User>(){
                    @Override
                    public void handleError() {
                        callBack.failure("注册失败");
                    }
                });
    }

    @Override
    public void login(String number, String password, ModelCallBack<User> callBack) {
        Retrofiter.getApi(AccountApi.class, Server.localUrl).login(number, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(callBack::success)
            .subscribe(new BaseObserver<User>(){
                @Override
                public void handleError() {
                    callBack.failure("接口请求错误");
                 }
            });
    }

    @Override
    public void verify(String email, String verificationNumber, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(AccountApi.class, Server.localUrl).verify(email, verificationNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求失败");
                    }
                });
    }

    @Override
    public void setTime(String tokenNumber, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(AccountApi.class, Server.localUrl).setTime(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求失败");
                    }
                });
    }
}
