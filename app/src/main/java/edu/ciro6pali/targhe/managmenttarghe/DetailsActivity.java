package edu.ciro6pali.targhe.managmenttarghe;



import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.room.Room;


import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



import edu.ciro6pali.targhe.R;
import edu.ciro6pali.targhe.database.AppDatabase;
import edu.ciro6pali.targhe.database.Targa;

public class DetailsActivity extends AppCompatActivity  {
    private static final String TAG=DetailsActivity.class.getName();
    private static final String KEY=TAG+".action_key";

    private AppDatabase db;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    TextView numeroTarga,marcaveicolo,cognomeProprietario;
    TextView nomeProprietario,passMacchina,telefonoProprietario,enteDiAppartenenza,noteAggiuntive;


    public static Intent getIntentDetails(Context context, Targa targa) {
        Intent intent =new Intent(context,DetailsActivity.class);
        intent.putExtra(KEY,targa);
        return intent;
    }

    // private NoteViewModelA viewModel;

    Targa targa;
    public void instanziaCampi() {
        numeroTarga=findViewById(R.id.id_tx_details_TextTarga);
        marcaveicolo=findViewById(R.id.id_tx_details_TextMarcaVeicolo);
        cognomeProprietario=findViewById(R.id.id_tx_details_TextCognome);
        nomeProprietario=findViewById(R.id.id_tx_details_TextNome);
        passMacchina=findViewById(R.id.id_tx_details_TextPass);
        telefonoProprietario=findViewById(R.id.id_tx_details_TextTelefono);
        enteDiAppartenenza=findViewById(R.id.id_tx_details_TextEntediAppartenenza);
        noteAggiuntive=findViewById(R.id.id_tx_details_TextNote);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        instanziaCampi();

        db = Room.databaseBuilder(DetailsActivity.this, AppDatabase.class, "targhe_database").
                allowMainThreadQueries().fallbackToDestructiveMigration().build();
        targa = (Targa) getIntent().getSerializableExtra(KEY);
        Toast.makeText(DetailsActivity.this, "DetailsActivity:"+targa.getTarga(), Toast.LENGTH_SHORT).show();
        assert targa != null;
        riempiCampiActivity(targa);





        FloatingActionButton editfab = (FloatingActionButton) findViewById(R.id.editfab);
        editfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(EditActivity.getIntentEdit(DetailsActivity.this, 'e', targa));

                finish();
            }
        });

        FloatingActionButton deletetfab = (FloatingActionButton) findViewById(R.id.deletefab);
        deletetfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert("Conferma", "Vuoi davvero cancellare la tua Targa?");
            }
        });

    }

    private void riempiCampiActivity(Targa targa) {
        if(targa.getTarga()==null){
            numeroTarga.setText("Non pervenuto");
        }else {
                 numeroTarga.setText(targa.getTarga().toString());
        }
        if(targa.getMarca()==null){
            marcaveicolo.setText("Non pervenuto");
        }else {
            marcaveicolo.setText(targa.getMarca());
        }
        if(targa.getCognome()==null){
           cognomeProprietario.setText("Non pervenuto");
        }else {
            cognomeProprietario.setText(targa.getCognome());
        }
        if(targa.getNome()==null){
            nomeProprietario.setText("Non pervenuto");
        }else {
            nomeProprietario.setText(targa.getNome());
        }
        if(targa.getPass()==null){
           passMacchina.setText("Non pervenuto");
        }else {
            passMacchina.setText(targa.getPass());
        }
        if(targa.getTelefono()==null){
            telefonoProprietario.setText("Non pervenuto");
        }else {
            telefonoProprietario.setText(targa.getTelefono());
        }
        if(targa.getEntediappartenenza()==null){
            enteDiAppartenenza.setText("Non pervenuto");
        }else {
            enteDiAppartenenza.setText(targa.getEntediappartenenza());
        }

        if(targa.getMessaggio()==null){
            noteAggiuntive.setText("nessuna nota");
        }else {
            noteAggiuntive.setText(targa.getMessaggio());
        }


    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailsActivity.this);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (targa!=null){db.gettargaDao().delete(targa);
                        //prova per viewmodel
                         //  viewModel.deleteNote(Targa);
                         //fine prova viewmodel
                            db.gettargaDao().deleteById(targa.getId());
                            Log.i(TAG,"cancello titolo:"+targa.getTarga().toString());
                        }

                       // db.noteDao().deleteById(Targa.getId());
                       ListaActivity.adapterNotifyAll();
                        Log.i(TAG,"cancello Targa");

                       finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }









}

