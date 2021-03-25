package io.github.grooters.idles.model;

import java.util.List;
import io.github.grooters.idles.base.BaseObserver;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.entity.Universities;
import io.github.grooters.idles.model.interfac.IPersonalM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.net.api.PersonalApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PersonalM implements IPersonalM {

    @Override
    public void getMyOrderGoods(String tokenNumber, ModelCallBack<List<Goods>> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).getMyOrderGoods(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Goods>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void getMyOrderWorks(String tokenNumber, ModelCallBack<List<Works>> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).getMyOrderWorks(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Works>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void getMyPushGoods(String tokenNumber, ModelCallBack<List<Goods>> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).getMyPushGoods(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Goods>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void getMyPushWorks(String tokenNumber, ModelCallBack<List<Works>> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).getMyPushWorks(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Works>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void getMyCollectionGoods(String tokenNumber, ModelCallBack<List<Goods>> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).getMyCollectionGoods(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Goods>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void getMyCollectionWorks(String tokenNumber, ModelCallBack<List<Works>> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).getMyCollectionWorks(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Works>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void getUser(String tokenNumber, ModelCallBack<User> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).getUser(tokenNumber)
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
    public void setUniversityName(String tokenNumber, String universityName, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).setUniversity(tokenNumber, universityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void setResume(String tokenNumber, String resume, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).setResume(tokenNumber, resume)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void searchUniversity(String key, ModelCallBack<Universities> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).searchUniversity(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Universities>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void setUserData(String userNumber, String number, String name,
                            String gender, String home, String avatar, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).setDataUser(userNumber, number, name, gender, home, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void setLocation(String token, String location, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(PersonalApi.class, Server.localUrl).setLocation(token, location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }
}
