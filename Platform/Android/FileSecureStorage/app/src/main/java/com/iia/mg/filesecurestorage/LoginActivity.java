package com.iia.mg.filesecurestorage;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iia.mg.filesecurestorage.config.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public String identifiant = "";
    public String pwd = "";
    public JSONObject fluxConn;

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
                TextView tvide = (TextView)findViewById(R.id.textEditLogin);
                identifiant = tvide.getText().toString();

                TextView tvpwd = (TextView)findViewById(R.id.textEditPwd);
                pwd = tvpwd.getText().toString();

                fluxConn = writeJSON(identifiant,pwd);
            }
        });

    }
    public JSONObject writeJSON(String id, String pwd) {
        AppConstants constants = new AppConstants();
        JSONObject object = new JSONObject();

        try {
            object.put(constants.ID_CONNECTION, id);
            object.put(constants.PWD_CONNEXION , pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

}
