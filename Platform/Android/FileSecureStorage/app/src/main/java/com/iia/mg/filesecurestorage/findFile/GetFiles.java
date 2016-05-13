package com.iia.mg.filesecurestorage.findFile;


import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.iia.mg.filesecurestorage.Connection.RecoversIds;
import com.iia.mg.filesecurestorage.config.AppConstants;
import com.iia.mg.filesecurestorage.entity.FileText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jeoffrey on 20/01/2016.
 * time : 14:29
 */
public class GetFiles {
    String sdCardDirectory;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    AppConstants constants = new AppConstants();
    RecoversIds recover = new RecoversIds();
    byte[] ivBytes =new byte[16];

    /**
     * Starting Sync
     */
    public JSONObject StartManualUpload(String id,String code) {
        JSONObject jsonobject = new JSONObject();
        ArrayList<FileText> filesText = new ArrayList<FileText>();
        sdCardDirectory = Environment.getExternalStorageDirectory().toString();
        File dirFileObj = new File(sdCardDirectory);
        System.out.println("photoDir : " + sdCardDirectory);
        List<File> files = getListFiles(dirFileObj);
        for (File file : files) {
            System.out.println( "Boucle For : " + file.getName());
            String name = file.getName();
            if(name.equals("optionsfile.txt") == true || name.equals("profilesfile.txt"))
            {
                FileText fileText = fileToFileText(file);
                filesText.add(fileText);
            }
            else {
                System.out.println( "Boucle if _ else ");
            }
        }

        if(filesText.size() != 0)
        {
            System.out.println("Creation Json");
            jsonobject = FilesTextTojson(filesText,id,code);
        }
        else {
            System.out.println( "filesText.size : " + filesText.size());
        }

        return jsonobject;
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

    /**
     * Take File and return the Content
     * @param FileGetContent
     * @return content
     */
    private String ContentFileToString(File FileGetContent){

        String content = "";

        ArrayList<File> arrayFile = new ArrayList();

        try {
            InputStream inputStream = new FileInputStream(FileGetContent);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String ligne;

            while ((ligne = bufferedReader.readLine()) != null) {
                content += ligne;
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return content;
    }

    /**
     * Transforme le file en un FileText avec le contenu Chiffré
     * @param file
     * @return fileText
     */
    private FileText fileToFileText(File file)
    {
        String content = ContentFileToString(file);
        String name = file.getName();
        String path = file.getPath();
        Date updated_at = new Date();
        long longDate = file.lastModified();
        System.out.println( "longDate " + longDate);
        String stringDate = Long.toString(longDate);
        System.out.println( "StringDate " + stringDate);
        try{
            updated_at = simpleDateFormat.parse(stringDate);
        }
        catch (ParseException e)
        {
            System.out.println("Exception " + e);
        }
        System.out.println( "Date " + updated_at);
        FileText fileText = chiffrementFileAes128(content, name, path, updated_at);

        return fileText;

    }

    /**
     *  Return FilesText Encrypt
     * @param content
     * @param name
     * @param path
     * @param updated_at
     * @return fileText
     */
    public FileText chiffrementFileAes128(String content, String name,  String path, Date updated_at) {
        byte[] TableContent;    String chiffContentBase64;
        byte[] TableName;       String chiffNameBase64;
        byte[] TablePath;       String chiffPathBase64;
        byte[] TableUpdated_at; String chiffUpdated_atBase64;

        //chiffre content
        TableContent = encryptAES128(content);
        chiffContentBase64 = Base64.encodeToString(TableContent, Base64.URL_SAFE);

        //chiffre name
        TableName = encryptAES128(name);
        chiffNameBase64 = Base64.encodeToString(TableName, Base64.URL_SAFE);

        //chiffre path
        TablePath = encryptAES128(path);
        chiffPathBase64 = Base64.encodeToString(TablePath, Base64.URL_SAFE);

        //chiffre updated_at
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String myDate =  sdf.format(updated_at);
        TableUpdated_at = encryptAES128(myDate);
        chiffUpdated_atBase64 = Base64.encodeToString(TableUpdated_at, Base64.URL_SAFE);


        FileText fileText = new FileText(chiffContentBase64,chiffNameBase64,chiffPathBase64,chiffUpdated_atBase64);



        return fileText;
    }

    //encrypte String to AES 128
    public  byte[] encryptAES128(String toEncrypt) {
        String encrypted = null;
        byte[] cipherText = new byte[]{};
        try {
            // Init le cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE,getKey(constants.KeyChiffrementAes128),ivSpec);

            //fichier chiffré
            cipherText = cipher.doFinal(toEncrypt.getBytes());
        } catch (Exception e) {
            System.out.println("Impossible to encrypt with AES algorithm: string=(" +
                    toEncrypt + ")");
        }

        return  cipherText;
    }

    /**
     *
     * @param filesText
     * @param identifiant
     * @param code
     * @return jsonObject
     */
    public JSONObject FilesTextTojson (ArrayList<FileText> filesText,String identifiant,String code)
    {
        String ivBase64;
        String identifiantBase64;
        String codeBase64;
        byte[] TableId;
        byte [] TableCode;

        //Encrypt id
        TableId = encryptAES128(identifiant);
        identifiantBase64 = Base64.encodeToString(TableId, Base64.URL_SAFE);

        // Table Iv to String
        ivBase64 =Base64.encodeToString(ivBytes,Base64.URL_SAFE);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for(FileText file : filesText)
        {
            try {
                JSONObject fileDetailJson = new JSONObject();
                fileDetailJson.put("name",file.getName());
                fileDetailJson.put("content",file.getContent());
                fileDetailJson.put("path",file.getPath());
                fileDetailJson.put("updated_at",file.getUpdated_at());
                jsonArray.put(fileDetailJson);

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {

            jsonObject.put("conteneurFichier",jsonArray);

        }catch (JSONException e) {
        e.printStackTrace();
    }
        try{
            jsonObject.put("identifiant",identifiantBase64);
            jsonObject.put("code",code);
            jsonObject.put("IV",ivBase64);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Get the Secret Key
     * @param secretKey
     * @return SecretKey
     */
    public SecretKeySpec getKey(String secretKey) {

        byte[] byteKey = secretKey.getBytes();
        SecretKeySpec aesKey = new SecretKeySpec(byteKey, "AES/CBC/PKCS5Padding");
        return aesKey;

    }


}
