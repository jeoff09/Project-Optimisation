package com.iia.mg.filesecurestorage.Connection;

import android.util.Base64;

import com.iia.mg.filesecurestorage.config.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

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

    //prépare le contenu du flux json avec intitulé et valeur
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

    /* appelle la méthode de chiffrement et converti le tableau de byte reçu en string
        Ensuite inclu ses strings dans le fichiers JSon et le renvoi
    */
    public JSONObject chiffrementIdAes128(String id, String pwd) {
        byte[] TableId; byte[] TablePWD;
        String chiffIDBase64; String chiffPWDBase64; String ivBase64;

        //chiffre id to chiffrId et transforme en string
        TableId = encryptAES256(id);
        chiffIDBase64 = Base64.encodeToString(TableId, Base64.DEFAULT);

        //chiffre PWd to chiffrPWD et transforme en string
        TablePWD = encryptAES256(pwd);
        chiffPWDBase64 = Base64.encodeToString(TablePWD, Base64.DEFAULT);

        // transforme le tableau dIv en string
        ivBase64 =Base64.encodeToString(ivBytes,Base64.DEFAULT);

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

    // Récupère la clé secrète
    public SecretKeySpec getKey(String secretKey) {

        byte[] byteKey = secretKey.getBytes();
        SecretKeySpec aesKey = new SecretKeySpec(byteKey, "AES/CBC/PKCS5Padding");
        return aesKey;

    }

    //déchiffre le tableau de byte
    private  String dechiffrToString(byte[] toDechiffr)
    {
        String dechiffr = null;
        try {
            // Init le cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, getKey(constants.KeyChiffrementAes128),ivSpec);

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
}
