package io.github.grooters.idles.view.interfac;

import java.util.List;

import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Works;

public interface IGoodsWorksV extends IBaseV {

    void initRecyclerViewWithGoods(List<Goods> goods);

    void initRecyclerViewWithWorks(List<Works> works);

    void showEmptyTextView();

    void hideEmptyTextView();

}
