package com.iia.mg.filesecurestorage.findFile;


import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.iia.mg.filesecurestorage.Connection.RecoversIds;
import com.iia.mg.filesecurestorage.config.AppConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jeoffrey on 20/01/2016.
 * time : 14:29
 */
public class GetFiles {
    AppConstants constants = new AppConstants();
    RecoversIds recover = new RecoversIds();
    byte[] ivBytes =new byte[16];
    /**
     * Démarrage de la synchronisation
     */
    public void StartManualUpload() {
        int count = 0;
        String photoDir;
        photoDir = Environment.getExternalStorageDirectory().toString();

        File dirFileObj = new File(photoDir);
        System.out.println("photoDir : " + photoDir);
        List<File> files = getListFiles(dirFileObj);
        for (File file : files) {
            System.out.println(file.getName());
            try {
                EncryptContentFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the list of file in the dir pass in parameters
     * @param parentDir
     * @return List of file in the parentDir
     */
    public List<File> getListFiles(File parentDir) {

        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();

        if(files != null ) {
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getListFiles(file));
                } else {
                    String name = file.getName();
                    if( name.endsWith(".txt")) {
                        inFiles.add(file);
                    }
                    else{

                    }
                }
            }
        }
        else {
            System.out.println("List null");
        }
        return inFiles;
    }

    private Byte[] EncryptContentFile(File fileToSend) throws IOException {

        Byte[] cipherText = new Byte[]{};
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileToSend));
        //String encryptedContentFile =



        return cipherText;
    }

    public byte[] encodeFile(byte[] key, byte[] fileData) throws Exception
    {

        SecretKeySpec secret = recover.getKey(constants.KeyChiffrementAes128);
        Cipher cipher = Cipher.getInstance("AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secret,ivSpec);

        byte[] encrypted = cipher.doFinal(fileData);

        return encrypted;
    }

    // Récupère la clé secrète
    public SecretKeySpec getKey(String secretKey) {

        byte[] byteKey = secretKey.getBytes();
        SecretKeySpec aesKey = new SecretKeySpec(byteKey, "AES/CBC/PKCS5Padding");
        return aesKey;

    }


}
