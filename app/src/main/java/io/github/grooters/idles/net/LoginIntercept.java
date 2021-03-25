package io.github.grooters.idles.net;

import com.orhanobut.logger.Logger;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginIntercept implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        String api = request.url() + "/" + request.method();
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        StringBuilder params = new StringBuilder();

        if ("GET".equals(request.method())) { // GET方法
            HttpUrl httpUrl = urlBuilder.build();
            // 打印所有get参数
            Set<String> paramKeys = httpUrl.queryParameterNames();
            for (String key : paramKeys) {
                String value = httpUrl.queryParameter(key);
                params.append(key);
                params.append("：");
                params.append(value);
                params.append(" | ");
            }
            // 将最终的url填充到request中
            requestBuilder.url(httpUrl);
        } else if ("POST".equals(request.method())) { // POST方法
            // FormBody和url不太一样，若需添加公共参数，需要新建一个构造器
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            // 把已有的post参数添加到新的构造器
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                for (int i = 0; i < Objects.requireNonNull(formBody).size(); i++) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
            }
            // 这里可以添加一些公共post参数
            bodyBuilder.addEncoded("key_xxx", "value_xxx");
            FormBody newBody = bodyBuilder.build();
            // 打印所有post参数
            for (int i = 0; i < newBody.size(); i++) {
                params.append(newBody.name(i));
                params.append("：");
                params.append(newBody.value(i));
                params.append(" | ");
            }
            // 将最终的表单body填充到request中
            requestBuilder.post(newBody);
        }
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Logger.d("接口方法：" + api + "\n" + "请求参数：" + params.toString() + "\n请求结果：" + responseBody.string());
        return response;
    }
}
