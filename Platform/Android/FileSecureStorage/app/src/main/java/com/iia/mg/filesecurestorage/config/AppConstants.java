package com.iia.mg.filesecurestorage.config;

import android.app.Application;
import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.iia.mg.filesecurestorage.LoginActivity;

/**
 * Created by jeoffrey on 20/01/2016.
 */
public class AppConstants extends LoginActivity {
    final String pathImage = android.os.Environment.DIRECTORY_DCIM;
    public static final String ID_CONNECTION = "identifiant";
    public static final String PWD_CONNEXION = "password";
    public static final String URL_SRV = "";
    public static final String KeyChiffrementAes128 = "a7656fafe94dae72b1e1487670148412";
    public static final String[] REPOSITORY_PATH = {"pathImage"};

}
