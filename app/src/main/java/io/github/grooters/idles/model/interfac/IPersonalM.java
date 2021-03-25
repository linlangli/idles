package io.github.grooters.idles.model.interfac;

import java.util.List;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.entity.Universities;

public interface IPersonalM{
    void getMyOrderGoods(String token, ModelCallBack<List<Goods>> callBack);
    void getMyOrderWorks(String token, ModelCallBack<List<Works> >callBack);
    void getMyPushGoods(String token, ModelCallBack<List<Goods>> callBack);
    void getMyPushWorks(String token, ModelCallBack<List<Works>> callBack);
    void getMyCollectionGoods(String token, ModelCallBack<List<Goods>> callBack);
    void getMyCollectionWorks(String token, ModelCallBack<List<Works>> callBack);
    void getUser(String tokenNumber, ModelCallBack<User> callBack);
    void setUniversityName(String token, String universityName, ModelCallBack<Result> callBack);
    void setResume(String token, String resume, ModelCallBack<Result> callBack);
    void searchUniversity(String key, ModelCallBack<Universities> callBack);
    void setUserData(String userNumber, String number, String name,
                     String gender, String home, String avatar, ModelCallBack<Result> callBack);
    void setLocation(String token, String location, ModelCallBack<Result> callBack);
}
