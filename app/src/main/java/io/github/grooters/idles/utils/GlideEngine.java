package io.github.grooters.idles.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.engine.ImageEngine;
import com.orhanobut.logger.Logger;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideEngine implements ImageEngine {

    private static class MyInstance{

        static final GlideEngine glideEngine = new GlideEngine();

    }

    public static GlideEngine getGlideEngine(){
        return MyInstance.glideEngine;
    }

    @Override
    public void loadPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView) {
        Logger.d("uri = " + uri.getPath());
        Glide.with(context).load(uri).into(imageView);
    }

    @Override
    public void loadGifAsBitmap(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asBitmap().load(gifUri).into(imageView);
    }

    @Override
    public void loadGif(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asGif().load(gifUri).transition(withCrossFade()).into(imageView);
    }

    @Override
    public Bitmap getCacheBitmap(@NonNull Context context, @NonNull Uri uri, int width, int height) throws Exception {
        return Glide.with(context).asBitmap().load(uri).submit(width, height).get();
    }
}
