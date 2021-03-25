package io.github.grooters.idles.model.interfac;

import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;

public interface IAccountM {

    void getToken(ModelCallBack<Token> callBack);

    void getVerification(String email,  String emailServer, String emailServerKey, ModelCallBack<Result> callBack);

    void register(String phoneNumber, String password, ModelCallBack<User> callBack);

    void login(String email, String password, final ModelCallBack<User> callBack);

    void verify(String email, String verificationNumber, ModelCallBack<Result> callBack);

    void setTime(String tokenNumber, ModelCallBack<Result> callBack);
}
