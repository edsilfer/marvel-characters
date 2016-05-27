package br.com.hole19.marvel.ui.commons.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by edgar on 08-May-16.
 */
public class PermissionsUtil {

    public boolean verifyStoragePermissions(Activity activity, int callbackCode) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        return true;
    }

}
