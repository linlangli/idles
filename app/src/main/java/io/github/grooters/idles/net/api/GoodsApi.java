package io.github.grooters.idles.net.api;

import java.util.List;
import java.util.Map;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GoodsApi {

    @FormUrlEncoded
    @POST("getGoods")
    Observable<List<Goods>> getGoods(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("collectGoods")
    Observable<Result> collectGoods(@Field("tokenNumber") String tokenNumber, @Field("goodsNumber") String goodsNumber);

    @FormUrlEncoded
    @POST("pushGoods")
    Observable<Result> pushGoods(@Field("tokenNumber") String tokenNumber, @Field("goods") String goodsJson);

    @FormUrlEncoded
    @POST("getSeller")
    Observable<User> getSeller(@Field("tokenNumber") String tokenNumber, @Field("goodsNumber") String goodsNumber);

}
