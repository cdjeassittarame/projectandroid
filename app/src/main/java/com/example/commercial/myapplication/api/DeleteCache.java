package com.example.commercial.myapplication.api;

import android.app.Application;

import java.io.File;

import android.app.Application;
import android.util.Log;

/**
 * Created by kabtel on 10/06/16.
 */
public class DeleteCache extends Application {


    private static DeleteCache     instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static DeleteCache getInstance() {
        return instance;
    }

    public void clearApplicationData() {

        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {

            String[] fileNames = applicationDirectory.list();

            for (String fileName : fileNames) {

                if (!fileName.equals("lib")) {

                    deleteFile(new File(applicationDirectory, fileName));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + fileName +" DELETED");

                }

            }

        }

    }

    public static boolean deleteFile(File file) {

        boolean deletedAll = true;

        if (file != null) {

            if (file.isDirectory()) {

                String[] children = file.list();

                for (int i = 0; i < children.length; i++) {

                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;

                }

            } else {

                deletedAll = file.delete();

            }

        }

        return deletedAll;

    }

}
