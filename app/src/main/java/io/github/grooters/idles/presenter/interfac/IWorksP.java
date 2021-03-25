package io.github.grooters.idles.presenter.interfac;

import io.github.grooters.idles.bean.Works;

public interface IWorksP extends IBaseP{

    void getWorksList();

    void pushWorks(String tokenNumber, Works works);

    void provideStartOrEndTime(String time);
}
