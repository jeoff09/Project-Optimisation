package com.iia.mg.filesecurestorage;

import android.app.Activity;
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

public class LoginActivity extends AppCompatActivity {

    public String ident;
    public String pwd ;
    public String code;
    public JSONObject fluxConn;
    String url = "http://192.168.100.78/Web/Controller/index.php?inputStream=";
    public String isSucess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         final RecoversIds recoverIds = new RecoversIds();

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

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

                fluxConn = recoverIds.chiffrementIdAes128(ident, pwd,"false");
                System.out.println("flux = " + fluxConn.toString());
                //isSucess = recoverIds.PostJsonToServer(fluxConn,url);
                isSucess = "true";
                if(isSucess == "true")
                {
                    Intent intent = new Intent(LoginActivity.this, SynchroActivity.class);
                    intent.putExtra("identifiant",ident);
                    intent.putExtra("code",code);
                    startActivity(intent);
                }
                else {

                }
                System.out.println(fluxConn);


            }
        });

    }
}
