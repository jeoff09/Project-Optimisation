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

public class SynchroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button uploadButton = (Button)findViewById(R.id.buttonSynchro);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                final ProgressDialog dialog = ProgressDialog.show(SynchroActivity.this, "", "Uploading file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                final GetFiles getFiles = new GetFiles();
                                getFiles.StartManualUpload();
                                dialog.hide();
                            }
                        });
                    }
                }).start();
            }

        });
    }

}
