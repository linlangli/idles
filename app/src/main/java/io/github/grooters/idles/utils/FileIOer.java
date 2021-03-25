package io.github.grooters.idles.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import com.orhanobut.logger.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileIOer {

    public static void writeString(String path, String fileName, String message){

        File file = new File(path + "/" + fileName);

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readString(String path, String fileName){

        File file = new File(path+ "/" +fileName);
        StringBuilder message = new StringBuilder();

        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String temp;
            while ((temp = bufferedReader.readLine()) != null){
                message.append(temp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message.toString();
    }

    public static List<Bitmap> getLocalImages(Context context){

        List<Bitmap> bitmaps = new ArrayList<>();

        String queryPath = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q ? MediaStore.Images.Media.RELATIVE_PATH : MediaStore.Images.Media.DATA;

       @SuppressLint("Recycle")
       Cursor cursor =
               context.getContentResolver()
                       .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                               new String[]{MediaStore.Images.Media._ID, queryPath, MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.DISPLAY_NAME},
                null, null, null);

       if(cursor != null && cursor.moveToFirst()){

           do{

               int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));

               String path = cursor.getString(cursor.getColumnIndex(queryPath));

               String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));

               String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));

               Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

               try {

                   bitmaps.add(BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri)));

               } catch (FileNotFoundException e) {

                   e.printStackTrace();
               }

           }while (cursor.moveToNext());

           Logger.d("图片数量: "+ bitmaps.size());

       }

       return bitmaps;

    }

    public static File compressImage(Bitmap bitmap, String path){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int quality = 100;

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

        while( outputStream.toByteArray().length / 1024 > 500 ){
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, --quality, outputStream);
        }

        File file = new File(path);

        Logger.d(path);

        if(file.exists()) file.delete();

        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(outputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static File inputStreamToFile(Context context, Uri uri, String path){

        try {
            InputStream inputStream = Objects.requireNonNull(context.getContentResolver().openInputStream(uri));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[1024];
            File file = new File(path);

            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            int length;

            while ((length = Objects.requireNonNull(bufferedInputStream).read(bytes)) != -1){
                bufferedOutputStream.write(bytes, 0, length);
                bufferedOutputStream.flush();
            }

            inputStream.close();
            bufferedInputStream.close();
            bufferedOutputStream.close();
            return file;
        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }
}
