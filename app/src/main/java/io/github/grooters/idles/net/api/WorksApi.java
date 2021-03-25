package io.github.grooters.idles.net.api;

import java.util.List;
import java.util.Map;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Works;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WorksApi {

    @FormUrlEncoded
    @POST("getWorks")
    Observable<List<Works>> getWorks(@Field("tokenNumber") String tokenNumber);

    @FormUrlEncoded
    @POST("requestTransaction")
    Observable<Result> requestTransaction(@Field("tokenNumber") String tokenNumber, @Field("worksNumber") String worksNumber);

    @FormUrlEncoded
    @POST("pushWorks")
    Observable<Result> pushWorks(@Field("tokenNumber") String tokenNumber, @Field("works") String worksJson);

}
