package io.github.grooters.idles.model;

import java.util.List;
import io.github.grooters.idles.base.BaseObserver;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.model.interfac.IWorksM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.net.api.WorksApi;
import io.github.grooters.idles.utils.Jsoner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WorksM implements IWorksM {
    @Override
    public void getWorks(String tokenNumber, ModelCallBack<List<Works>> callBack) {
        Retrofiter.getApi(WorksApi.class, Server.localUrl).getWorks(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Works>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求失败");
                    }
                });
    }

    @Override
    public void pushWorks(String tokenNumber, Works works, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(WorksApi.class, Server.localUrl).pushWorks(tokenNumber, Jsoner.toJson(works))
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
