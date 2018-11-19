package com.zxu.util;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class ActivityUtil {
    /**
     * 负责提交事物，并将Fragment添加到Activity中
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
