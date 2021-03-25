package io.github.grooters.idles.net.api;

import java.util.List;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.entity.Universities;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PersonalApi {

    @FormUrlEncoded
    @POST("getMyOrderGoods")
    Observable<List<Goods>> getMyOrderGoods(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("getMyOrderWorks")
    Observable<List<Works>> getMyOrderWorks(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("getMyCollectionGoods")
    Observable<List<Goods>> getMyCollectionGoods(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("getMyCollectionWorks")
    Observable<List<Works>> getMyCollectionWorks(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("getMyPushGoods")
    Observable<List<Goods>> getMyPushGoods(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("getMyPushWorks")
    Observable<List<Works>> getMyPushWorks(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("getUser")
    Observable<User> getUser(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("setUniversity")
    Observable<Result> setUniversity(@Field("tokenNumber") String tokenNumber, @Field("universityName") String collegeName);

    @FormUrlEncoded
    @POST("setResume")
    Observable<Result> setResume(@Field("tokenNumber") String tokenNumber, @Field("resume") String resume);

    @GET("searchUniversity")
    Observable<Universities> searchUniversity(@Query("key") String key);

    @FormUrlEncoded
    @POST("setUserData")
    Observable<Result> setDataUser(@Field("userNumber") String userNumber, @Field("newUserNumber") String newUserNumber,
                                   @Field("name") String name, @Field("gender")String gender, @Field("home") String home, @Field("avatar") String avatar);

    @FormUrlEncoded
    @POST("setLocation")
    Observable<Result> setLocation(@Field("tokenNumber") String tokenNumber, @Field("location")String location);
}
