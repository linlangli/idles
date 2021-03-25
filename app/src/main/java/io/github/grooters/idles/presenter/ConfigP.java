package io.github.grooters.idles.presenter;

import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.File;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.presenter.interfac.IConfigP;
import io.github.grooters.idles.utils.Constant;
import io.github.grooters.idles.utils.Encrypter;
import io.github.grooters.idles.utils.FileIOer;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.utils.SharePrefer;
import io.github.grooters.idles.view.dialog.ConfigDialog;
import io.github.grooters.idles.view.interfac.IConfigV;

public class ConfigP implements IConfigP {

    static final String SERVER_CONFIG = Encrypter.toBase64("server_config");

    private IConfigV iConfigV;
    private Context context;

    private static User user;

    public ConfigP(Context context, ConfigDialog configDialog){
        iConfigV = configDialog;
        this.context = context;
    }

    @Override
    public void setUrl(String localUrl, String netUrl, String email, String emailKey, String key) {

        if(!key.equals("530412")){
            iConfigV.showMessage("授权密码错误，无法设置");
            return;
        }

        StringBuilder builder = new StringBuilder();
        StringBuilder reallyLocalUrl = new StringBuilder();

        if(!localUrl.equals("")){
            reallyLocalUrl.append("http://").append(localUrl).append(".natappfree.cc/");
            Server.localUrl = reallyLocalUrl.toString();
            builder.append("localUrl-").append(reallyLocalUrl).append("|");
        }

        if(!netUrl.equals("")){
            Server.netLocal = netUrl;
            builder.append("netUrl-").append(netUrl).append("|");
        }
        SharePrefer.set(context, SERVER_CONFIG, "url", Encrypter.toBase64(builder.toString()));

        builder = new StringBuilder();
        if(!email.equals("")){
            Server.email = email;
            builder.append("email-").append(email).append("|");
        }

        if(!emailKey.equals("")){
            Server.emailKey = emailKey;
            builder.append("emailKey-").append(emailKey).append("|");
        }
        SharePrefer.set(context, SERVER_CONFIG, "email", Encrypter.toBase64(builder.toString()));

        iConfigV.showMessage("配置成功");

        Log.d("Logger", "服务器配置" + "本地Url：" + Server.localUrl + "网络Url：" + netUrl + "邮箱服务器：" + email + "邮箱服务器key：" + emailKey);
    }

    public static User getUser(Context context){
        if(user == null){
            user = Jsoner.toObj(Encrypter.fromBase64(FileIOer.readString(context.getFilesDir().getPath(), Constant.ACCOUNT_USER)), User.class);
        }
        Logger.d("user：" +  Jsoner.toJson(user));
        return user;
    }

    public static void setUser(Context context, User user){
        FileIOer.writeString(context.getFilesDir().getPath(), Constant.ACCOUNT_USER, Encrypter.toBase64(Jsoner.toJson(user)));
        ConfigP.user = user;
    }

    public static void deleteUser(Context context){
        File file = new File(context.getFilesDir().getParent() + "/" + Constant.ACCOUNT_USER);
        if(file.exists())
            file.delete();
    }

    @Override
    public void unAttach() {
        context = null;
        iConfigV = null;
    }
}
