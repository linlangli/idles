package io.github.grooters.idles.presenter.interfac;

import android.content.Context;
import android.net.Uri;

import java.util.List;

public interface IGoodsP extends IBaseP{

    void getGoodsList();

    void handleGoodsData(Context context, Uri titleImageUri,
                         List<Uri> introImageUri, String videoPath, Uri videoUri,
                         String name, float price, String time, String location, String intro);

    void collectGoods(String tokenNumber, String goodsNumber);

    void getSellerGoodsData(String tokenNumber, String goodsNumber);

}
