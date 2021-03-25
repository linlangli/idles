package io.github.grooters.idles.view.dialog;

import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.presenter.PersonalP;
import io.github.grooters.idles.presenter.interfac.IPersonalP;
import io.github.grooters.idles.utils.ResourcEr;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.fragment.MyDataFragment;
import io.github.grooters.idles.view.interfac.IResumeV;

public class ResumeDialog extends BaseDialog implements IResumeV {

    @BindView(R.id.dialog_editText_resume)
    EditText resumeEditText;

    private IPersonalP iPersonalP;
    private MyDataFragment myDataFragment;
    public static String resume;

    public ResumeDialog(MyDataFragment myDataFragment){
        this.myDataFragment = myDataFragment;
    }

    @OnClick(R.id.dialog_resume_button_ok)
    void okButtonOnClick(){
        isEmpty(resumeEditText);
        resume = resumeEditText.getText().toString();
        iPersonalP.setResume(resume);
        dismiss();
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_resume;
    }

    @Override
    public void initView(View view) {
        iPersonalP = new PersonalP(getContext(), myDataFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTransaction();
        setSize((int) ResourcEr.dp2px(350), (int) ResourcEr.dp2px(280));
    }

    @Override
    public void unAttach() {
        iPersonalP = null;
    }

    @Override
    public void startLoading() { }

    @Override
    public void stopLoading() { }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public void loginInvalid() {
        invalid();
    }
}
