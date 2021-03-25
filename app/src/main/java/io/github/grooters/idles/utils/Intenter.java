package io.github.grooters.idles.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;


public class Intenter {

    public static <T extends Parcelable> void jumpActivity(Context context, Class<?> cls, String name, T data){

        Intent intent = new Intent(context, cls);

        intent.putExtra(name, data);

        context.startActivity(intent);
    }

    public static void jumpActivity(Context context, Class<?> cls, String name, String data){

        Intent intent = new Intent(context, cls);

        intent.putExtra(name, data);

        context.startActivity(intent);
    }

    public static void jumpActivity(Context context, Class<?> cls){

        Intent intent = new Intent(context, cls);

        context.startActivity(intent);
    }

}
