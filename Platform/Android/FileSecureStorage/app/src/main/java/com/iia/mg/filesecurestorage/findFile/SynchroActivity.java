package com.iia.mg.filesecurestorage.findFile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.iia.mg.filesecurestorage.Connection.RecoversIds;
import com.iia.mg.filesecurestorage.R;
import com.iia.mg.filesecurestorage.findFile.GetFiles;

import org.json.JSONObject;

public class SynchroActivity extends AppCompatActivity {

     String identifiant;
     String code ;
     String result;
    final String url = "";
    final GetFiles getFiles = new GetFiles();
    final RecoversIds recoversIds = new RecoversIds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button uploadButton = (Button)findViewById(R.id.buttonSynchro);
        Bundle extras = getIntent().getExtras();

        identifiant = extras.getString("identifiant");
        code = extras.getString("code");

        //Start Sync on click if send return null success show Dialog success else show error dial
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                final ProgressDialog dialog = ProgressDialog.show(SynchroActivity.this, "", "Uploading file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {

                                JSONObject flux =  getFiles.StartManualUpload(identifiant, code);
                                result =  recoversIds.PostJsonToServer(flux,url);
                                System.out.println("flux fichier = " + flux);
                                dialog.hide();
                                if (result == "null") {
                                    AlertDialog alertDialog = new AlertDialog.Builder(SynchroActivity.this).create();
                                    alertDialog.setTitle("Erreur");
                                    alertDialog.setMessage("Impossible d'effectuer la synchronisation !");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();

                                }
                                else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(SynchroActivity.this).create();
                                    alertDialog.setTitle("Succès");
                                    alertDialog.setMessage("La synchronisation à été effectuée avec succès !");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            }
                        });
                    }
                }).start();
            }

        });
    }

}
