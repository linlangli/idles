package io.github.grooters.idles.model.interfac;

import java.util.List;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Works;

public interface IWorksM {

    void getWorks(String tokenNumber, ModelCallBack<List<Works>> callBack);

    void pushWorks(String tokenNumber, Works works, ModelCallBack<Result> callBack);
}
