package io.github.grooters.idles.view.interfac;

import io.github.grooters.idles.bean.User;

public interface IPersonalV extends IBaseV {

    void initData(User user);

    void setAvatar(String url);

    void setDefaultAvatar();

    void setResume(String resume);

    void provideLocation(String location);
}
