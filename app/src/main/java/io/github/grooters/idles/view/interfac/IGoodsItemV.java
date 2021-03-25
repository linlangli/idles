package io.github.grooters.idles.view.interfac;

import io.github.grooters.idles.bean.User;

public interface IGoodsItemV extends IBaseV{

    void initSellerGoodsInfo(User seller);

    void beCollected();

    void notBeCollected();

    void setSellerAvatar(String url);

    void setDefaultAvatar();
}
