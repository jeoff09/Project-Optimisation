package com.iia.mg.filesecurestorage;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.iia.mg.filesecurestorage.findFile.GetFiles;

import com.iia.mg.filesecurestorage.config.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public String identifiant = "";
    public String pwd = "";
    public JSONObject fluxConn;
    public GetFiles getfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                identifiant = tvid.getText().toString();

                TextView tvpwd = (TextView)findViewById(R.id.textEditPwd);
                pwd = tvpwd.getText().toString();

                fluxConn = getfiles.chiffrementIdAes128(identifiant, pwd);

            }
        });

    }

}
