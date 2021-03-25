package io.github.grooters.idles.view.dialog;

import android.view.View;
import java.util.Objects;
import butterknife.OnClick;
import io.github.grooters.idles.R;

public class PushDialog extends BaseDialog {

    private GoodsDialog goodsDialog;
    private WorksDialog worksDialog;

    @OnClick(R.id.dialog_push_imageButton_pushGoods)
    void pushGoodsImageButtonOnClick(){
        goodsDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),"GoodsDialog");
    }

    @OnClick(R.id.dialog_push_imageButton_pushWorks)
    void pushWorksImageButtonOnClick(){
        worksDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),"WorksDialog");
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_push;
    }

    @Override
    public void initView(View view) {
        setTransaction();

        goodsDialog = new GoodsDialog();
        worksDialog = new WorksDialog();
    }
}
