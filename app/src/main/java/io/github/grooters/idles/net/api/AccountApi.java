package io.github.grooters.idles.net.api;

import java.util.List;

import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Works;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountApi {

    @FormUrlEncoded
    @POST("login")
    Observable<User> login(@Field("number") String number, @Field("password") String password);

    @GET("getToken")
    Observable<Token> getToken();

    @FormUrlEncoded
    @POST("getVerification")
    Observable<Result> getVerification(@Field("email")String email, @Field("emailServer") String emailServer, @Field("emailServerKey") String emailServerKey);

    @FormUrlEncoded
    @POST("verify")
    Observable<Result> verify(@Field("email")String email, @Field("verificationNumber") String verificationNumber);

    @FormUrlEncoded
    @POST("register")
    Observable<User> register(@Field("email") String email, @Field("password") String password);

    @GET("setTime")
    Observable<Result> setTime(@Query("tokenNumber")String tokenNumber);
}
