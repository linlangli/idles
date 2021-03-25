package io.github.grooters.idles.view.interfac;

import java.util.List;

public interface IListOfGoodsWorksV<D> extends IBaseV{

    void initRecyclerView(List<D> data);

    void initNullData();
}
