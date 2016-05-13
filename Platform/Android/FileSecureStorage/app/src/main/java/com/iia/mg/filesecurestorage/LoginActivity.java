package com.iia.mg.filesecurestorage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.mg.filesecurestorage.Connection.RecoversIds;
import com.iia.mg.filesecurestorage.findFile.GetFiles;

import com.iia.mg.filesecurestorage.config.AppConstants;
import com.iia.mg.filesecurestorage.findFile.SynchroActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public String ident;
    public String pwd ;
    public ArrayList<String> authentfication;
    public String code = "152839";
    public JSONObject fluxConn;
    String url = "http://192.168.100.78/Web/Controller/index.php?inputStream=";
    public String isSucess = "ddd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         final RecoversIds recoverIds = new RecoversIds();

        Button synchro = (Button)findViewById(R.id.buttonSynchroFiles);

        synchro.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                TextView tvid = (TextView)findViewById(R.id.textEditLogin);
                ident = tvid.getText().toString();

                TextView tvpwd = (TextView)findViewById(R.id.textEditPwd);
                pwd = tvpwd.getText().toString();

                System.out.println( "id = "+ ident + "pwd : "+ pwd );

                fluxConn = recoverIds.chiffrementIdAes128(ident, pwd);
                System.out.println("flux = " + fluxConn.toString());
                isSucess = recoverIds.PostJsonToServer(fluxConn,url);
                authentfication = recoverIds.AnswerToConnect(isSucess);
                isSucess = authentfication.get(0);
                if(isSucess.equals("vrai") == true)
                {
                    Intent intent = new Intent(LoginActivity.this, SynchroActivity.class);
                    intent.putExtra("identifiant",ident);
                    intent.putExtra("code",authentfication.get(1));
                    startActivity(intent);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Erreur");
                    alertDialog.setMessage("Impossible d'effectuer la connexion !");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                System.out.println(fluxConn);


            }
        });

    }
}
