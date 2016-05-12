package com.iia.mg.filesecurestorage.findFile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.iia.mg.filesecurestorage.R;
import com.iia.mg.filesecurestorage.findFile.GetFiles;

import org.json.JSONObject;

public class SynchroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String identifiant;
        final String code ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button uploadButton = (Button)findViewById(R.id.buttonSynchro);
        Bundle extras = getIntent().getExtras();

        identifiant = extras.getString("identifiant");
        code = extras.getString("code");


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                final ProgressDialog dialog = ProgressDialog.show(SynchroActivity.this, "", "Uploading file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                final GetFiles getFiles = new GetFiles();
                               JSONObject flux =  getFiles.StartManualUpload(identifiant, code);
                                dialog.hide();
                                System.out.println("flux = " + flux);
                            }
                        });
                    }
                }).start();
            }

        });
    }

}
