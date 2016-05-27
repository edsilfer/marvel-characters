package br.com.hole19.marvel.ui.commons.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import br.com.hole19.marvel.ui.infrastructure.App;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 08-May-16.
 */
public class ImageWarehouse implements Callback {

    private static final String TAG = "ImageWarehouse";

    private String mDirectory;
    private String mFileName;
    private ImageView mContainer;
    @Inject
    App mApplication;

    public ImageWarehouse(String fileName, ImageView container, String directory) {
        this.mFileName = fileName;
        this.mContainer = container;
        this.mDirectory = directory;
        this.getStorageDir();
    }

    @Override
    public void onSuccess() {
        if (this.isExternalStorageWritable()) {
            final Bitmap bitmap = ((BitmapDrawable) this.mContainer.getDrawable()).getBitmap();
            new AsyncTask<Void, Void, File>() {
                @Override
                protected File doInBackground(Void... params) {
                    File file = null;
                    try {
                        file = new File(ImageWarehouse.this.getStorageDir().getPath().concat("/").concat(ImageWarehouse.this.mFileName.concat(Constants.MEDIA_EXTENSION)));
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        Log.e(TAG, "External Storage is not available");
                    }
                    return file;
                }
            }.execute();
        } else {
            Log.e(TAG, "External Storage is not available");
        }
    }

    @Override
    public void onError() {

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getStorageDir() {
        File file = new File(Environment.getExternalStorageDirectory(), Constants.MEDIA_DIRECTORY.concat(this.mDirectory));
        if (!file.mkdirs()) {
        }
        return file;
    }
}
