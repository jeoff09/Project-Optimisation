package com.iia.mg.filesecurestorage.Connection;

import android.util.Base64;
import android.util.Log;

import com.iia.mg.filesecurestorage.config.AppConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jgodar on 26/01/2016.
 * time : 14:38
 */
public class RecoversIds {
    JSONObject object = new JSONObject();
    JSONObject  objectChiff = new JSONObject();
    AppConstants constants = new AppConstants();
    byte[] ivBytes =new byte[16];

    /**
     * Transforme information to Json
     * @param id
     * @param pwd
     * @param Iv
     * @return
     */
    public JSONObject chiffreIdToJson(String id, String pwd,String Iv) {

        try {
            object.put(AppConstants.ID_CONNECTION, id);
            object.put(AppConstants.PWD_CONNEXION, pwd);
            object.put(AppConstants.IV_CONNECTION,Iv);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     *
     * @param id
     * @param pwd
     * @return Json
     */
    public JSONObject chiffrementIdAes128(String id, String pwd) {
        byte[] TableId; byte[] TablePWD; byte[] TableIV;
        String chiffIDBase64; String chiffPWDBase64; String ivBase64;

        //chiffre id to chiffrId et transforme en string
        TableId = encryptAES256(id);
        chiffIDBase64 = Base64.encodeToString(TableId, Base64.URL_SAFE);

        //chiffre PWd to chiffrPWD et transforme en string
        TablePWD = encryptAES256(pwd);
        chiffPWDBase64 = Base64.encodeToString(TablePWD, Base64.URL_SAFE);

        // Make ivBytes to String
        ivBase64 =Base64.encodeToString(ivBytes,Base64.URL_SAFE);


        //make chiffre id to json
        objectChiff = chiffreIdToJson(chiffIDBase64, chiffPWDBase64,ivBase64);

        return objectChiff;
    }

    //encrypte String to AES 256
    public  byte[] encryptAES256(String toEncrypt) {
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
     * Get the Secret Key
     * @param secretKey
     * @return
     */
    public SecretKeySpec getKey(String secretKey) {

        byte[] byteKey = secretKey.getBytes();
        SecretKeySpec aesKey = new SecretKeySpec(byteKey, "AES/CBC/PKCS5Padding");
        return aesKey;

    }

    /**
     *
     * @param toDechiffr
     * @return string
     */
    public  String dechiffrToString(byte[] toDechiffr)
    {
        String dechiffr = null;
        try {
            // Init le cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, getKey(constants.KeyChiffrementAes128), ivSpec);

            //fichier chiffré
            byte[] cipherText = cipher.doFinal(toDechiffr);
            dechiffr = new String(cipherText);

            System.out.println(" Le fichier déchiffré correspond a  : " + dechiffr);

        } catch (Exception e) {
            System.out.println("Impossible to descrypt with AES algorithm: string=(" +
                    dechiffr + ")");
        }

        return  dechiffr;
    }

    /**
     * Function to post Json with Http request
     * @param jsonObject
     * @param url
     * @return String answerPost
     */
    public String PostJsonToServer(JSONObject jsonObject,String url)
    {
        InputStream inputStream;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            HttpResponse httpResponse = httpclient.execute(httpPost);
            HttpEntity entity =  httpResponse.getEntity();
            inputStream = entity.getContent();

            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else {
                result = "false!";
            }
        } catch (Exception e) {
            System.out.println("InputStream  = " + e.getLocalizedMessage());
        }

        return result;
    }

    /**
     * Convert the InputStream to String
     * @param inputStream
     * @return string
     * @throws IOException
     */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    /**
     * Get the answer and cast the value of authentified or not and the Code
     * @param answer
     * @return ArrayList
     */
    public ArrayList<String> AnswerToConnect(String answer)
    {
        ArrayList<String> Connectanswer = new ArrayList<String>();
        if(answer != null) {
            String test = "vrai\\aiz7ux9ChawYL652qL3vhg==";


            int position = test.indexOf((int) '\\'); // trouve la position du '\' dans la chaine
            String t1 = test.substring(0, position);  // extrait la chaine a gauche
            String t2 = test.substring(position + 1, test.length()); // extrait la chaine a droite

            Connectanswer.add(0, t1);
            Connectanswer.add(1, t2);

            System.out.println("Authentifier = " +Connectanswer.get(0) + "Code = "+ Connectanswer.get(1));
        }
        else {
            Connectanswer.add(0,"faux");
            Connectanswer.add(1, "00000");
        }
        return Connectanswer;
    }
}
