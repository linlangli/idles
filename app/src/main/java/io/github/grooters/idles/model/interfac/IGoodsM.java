package io.github.grooters.idles.model.interfac;

import com.tencent.cos.xml.CosXmlSimpleService;
import java.util.List;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.MultiMedia;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;

public interface IGoodsM {

    void getGoods(String tokenNumber, ModelCallBack<List<Goods>> callBack);

    void pushGoods(String token, Goods goods, ModelCallBack<Result> callBack);

    void upload(CosXmlSimpleService cosXmlService, final byte[] titleImage, final byte[] introVideo,
                         List<byte[]> introImageList, String userNumber, final ModelCallBack<MultiMedia> callBack);

    void collectGoods(String tokenNumber, String goodsNumber, ModelCallBack<Result> callBack);

    void getSeller(String tokenNumber, String goodsNumber, ModelCallBack<User> callBack);
}
