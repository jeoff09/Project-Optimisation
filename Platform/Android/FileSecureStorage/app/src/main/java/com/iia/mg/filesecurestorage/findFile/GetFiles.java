package com.iia.mg.filesecurestorage.findFile;


import com.iia.mg.filesecurestorage.config.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jeoffrey on 20/01/2016.
 */
public class GetFiles {
    JSONObject object = new JSONObject();
    JSONObject objectChiff = new JSONObject();
    AppConstants constants = new AppConstants();

    public JSONObject chiffreIdToJson(String id, String pwd) {


        try {
            object.put(constants.ID_CONNECTION, id);
            object.put(constants.PWD_CONNEXION, pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public JSONObject chiffrementIdAes128(String id, String pwd) {
        String chiffrId;
        String chiffrPWD;
        String dechiffreID;
        String dechiffrePWD;

        //chiffre id to chiffrId
        chiffrId = encryptAES256(id);
        dechiffreID = dechiffrToString(chiffrId);
        
        //chiffre PWd to chiffrPWD
        chiffrPWD = encryptAES256(pwd);
        dechiffrePWD = dechiffrToString(chiffrPWD);

        //make chiffre id to json
        objectChiff = chiffreIdToJson(chiffrId, chiffrPWD);

        return objectChiff;
    }

    //encrypte String to AES 256
    public  String encryptAES256(String toEncrypt) {
        String encrypted = null;
        try {
            // Init le cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(constants.KeyChiffrementAes128));

            //fichier chiffré
            byte[] cipherText = cipher.doFinal(toEncrypt.getBytes("ISO-8859-1"));
            encrypted = new String(cipherText);

        } catch (Exception e) {
            System.out.println("Impossible to encrypt with AES algorithm: string=(" +
                    toEncrypt + ")");
        }

        //renvoi le fichier chiffré
        return encrypted;
    }

    // Récupère la clé secrète
    private  SecretKeySpec getKey(String secretKey) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            return new SecretKeySpec(digest.digest(new String(secretKey.getBytes()
                    , "UTF8").getBytes()), "AES/CBC/PKCS5Padding");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;

        }
    }

    //déchiffre la string
    private  String dechiffrToString(String toDechiffr)
    {
        String dechiffr = null;
        try {
            // Init le cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getKey(constants.KeyChiffrementAes128));

            //fichier chiffré
            byte[] cipherText = cipher.doFinal(toDechiffr.getBytes("ISO-8859-1"));
            dechiffr = new String(cipherText);

            System.out.println(" Le fichier déchiffré correspond a  : " + dechiffr);

        } catch (Exception e) {
            System.out.println("Impossible to encrypt with AES algorithm: string=(" +
                    dechiffr + ")");
        }

        return  dechiffr;
    }
}
