package com.example.epet.calback;



import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUploader extends AsyncTask<String, Void, String> {

    private static final String TAG = "ImageUploader";
    private Bitmap imageBitmap;
    private UploadCallback callback;
    private Context context;

    public ImageUploader(Context context, Bitmap imageBitmap, UploadCallback callback) {
        this.context = context;
        this.imageBitmap = imageBitmap;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {

            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "*****");


            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();


            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + "image.jpg" + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.write(byteArray, 0, byteArray.length);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            byteArrayOutputStream.close();
            outputStream.flush();
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
                Log.d("Response", response);
            }
            reader.close();
            conn.disconnect();
        } catch (IOException e) {
            Log.e(TAG, "Error uploading image: " + e.getMessage());
            response = "Error: " + e.getMessage();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {

        Log.d(TAG, "Server Response: " + result);

        if (callback != null) {
            callback.onUploadComplete(result);
        }
    }

    public interface UploadCallback {
        void onUploadComplete(String response);
    }
}
