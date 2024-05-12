package edu.ciro6pali.targhe.managmenttarghe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.ciro6pali.targhe.R;
import edu.ciro6pali.targhe.database.AppDatabase;
import edu.ciro6pali.targhe.database.Targa;

public class EditActivity extends AppCompatActivity {
  private static final String TAG=EditActivity.class.getName();
   private static final String ACTION_KEY=TAG+".action_key";
    private static final String NOTA_KEY=TAG+".nota_key";
    private static final int RESULT_SPEECH_NOTE = 82;
   private char action;
    private Targa targa;



   private int id;
    PendingIntent alarmManager;
    private AppDatabase db;//nosto database
    EditText numeroTarga,marcaveicolo,cognomeProprietario,nomeProprietario,passMacchina,telefonoProprietario,enteDiAppartenenza,noteAggiuntive;
    public void instanziaCampi() {
        numeroTarga=findViewById(R.id.editTextTarga);
        marcaveicolo=findViewById(R.id.editTextMarcaVeicolo);
        cognomeProprietario=findViewById(R.id.editTextCognome);
        nomeProprietario=findViewById(R.id.editTextNome);
        passMacchina=findViewById(R.id.editTextPass);
        telefonoProprietario=findViewById(R.id.editTextTelefono);
        enteDiAppartenenza=findViewById(R.id.editTextEntediappartenenza);
        noteAggiuntive=findViewById(R.id.editTextMessage);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        instanziaCampi();






        db= Room.databaseBuilder(EditActivity.this,AppDatabase.class,"targhe_database").
                allowMainThreadQueries().fallbackToDestructiveMigration().build();
        action=getIntent().getCharExtra(ACTION_KEY,'n');
        targa=(Targa)getIntent().getSerializableExtra(NOTA_KEY);





        if (action=='e'){
            riempiCampiActivity( targa);
            id=targa.getId();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String targaString= numeroTarga.getText().toString().trim();
                String marcaVeicoloString=marcaveicolo.getText().toString();
                String cognomeString= cognomeProprietario.getText().toString().trim();
                String nomeString= nomeProprietario.getText().toString().trim();
                String passMacchinaString=passMacchina.getText().toString();
                String telefonoString= telefonoProprietario.getText().toString();
                String enteDiAppartenenzaString=enteDiAppartenenza.getText().toString();
                if(noteAggiuntive.getText().toString().isEmpty()){noteAggiuntive.setText("note aggiuntive");}
                String noteString=noteAggiuntive.getText().toString();

                if(!targaString.isEmpty()&&!cognomeString.isEmpty()&&!passMacchinaString.isEmpty()&&!nomeString.isEmpty()&&!telefonoString.isEmpty()){
                   if (action=='a'){
                       Targa targa1=new Targa( numeroTarga.getText().toString(),marcaveicolo.getText().toString(),cognomeProprietario.getText().toString(),
                               nomeProprietario.getText().toString(),passMacchina.getText().toString(),
                               telefonoProprietario.getText().toString(),enteDiAppartenenza.getText().toString(),
                               noteAggiuntive.getText().toString());

                       //aggiungi a dataase
                         db.gettargaDao().insertAll(targa1);
                         Log.i(TAG,"Aggingo Targa");
                   }else if(action=='e'){

                      // db.noteDao().delete(nota);
                      // db.noteDao().insertAll(new Nota(editTitle,editText));
                      // nota.setId(id);nota.setTitle(editTitle);nota.setNota(editText);

                       db.gettargaDao().updateTarga(id,numeroTarga.getText().toString(),marcaveicolo.getText().toString(),cognomeProprietario.getText().toString(),
                               nomeProprietario.getText().toString(),passMacchina.getText().toString(),
                               telefonoProprietario.getText().toString(),enteDiAppartenenza.getText().toString(),
                               noteAggiuntive.getText().toString());

 //modifica database
                   }
                   ListaActivity.adapterNotifyAll();
                   finish();
                }else {
                    Snackbar.make(view,"inserisci almeno un dato",Snackbar.LENGTH_SHORT).show();
                }
            }
        }




        );

        FloatingActionButton microfonofab = (FloatingActionButton) findViewById(R.id.speech_recognition);
    microfonofab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ascoltaspeaker(RESULT_SPEECH_NOTE);
        }
    });

        android.widget.TimePicker timePicker ;


    }
    public static Intent getIntentEdit(Context contex, char action, Targa targa){
        Intent intent =new Intent(contex,EditActivity.class);
        intent.putExtra(NOTA_KEY,targa);
        intent.putExtra(ACTION_KEY,action);
        return intent;
    }
    public static Intent getIntentEdit(Context contex, char action){
     Intent intent =new Intent(contex,EditActivity.class);
     intent.putExtra(ACTION_KEY,action);
     return intent;
    }
    private void ascoltaspeaker(int risultato_delparlato) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        try {

            startActivityForResult(intent, risultato_delparlato);
        } catch (ActivityNotFoundException a) {

            Toast.makeText(getApplicationContext(),"Opps! Your device doesn’t support Speech to Text", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == RESULT_SPEECH_NOTE) {
            if (resultCode == RESULT_OK && null != resultData) {
                ArrayList<String> text = resultData
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String[] array = text.toString().split(",");
                // EditText campoedit=findViewById(requestCode);
                if (array.length > 1) {
                    String aggiungistringa = " ";
                    if (noteAggiuntive.getText().toString() != null) {
                        aggiungistringa = noteAggiuntive.getText().toString();

                    }
                    noteAggiuntive.setText(aggiungistringa.concat(array[0].replace("[", "")) + "\n");


                } else {
                   noteAggiuntive.setText(text.toString().replace("[", "").replace("]", ""));
                }
            }
        }

    }







    private int i_campi_sono_tutti_compilati(){



        if(   numeroTarga.getText().toString().trim().isEmpty()||
              marcaveicolo.getText().toString().isEmpty()||
              cognomeProprietario.getText().toString().trim().isEmpty()||
              nomeProprietario.getText().toString().trim().isEmpty()||
              passMacchina.getText().toString().isEmpty()||
              telefonoProprietario.getText().toString().isEmpty()||
              enteDiAppartenenza.getText().toString().isEmpty())
        {
                Snackbar.make(noteAggiuntive,"Icampi dovrebbero essere compilati",Snackbar.LENGTH_SHORT).show();
            return 2;
        }
        if(targa!=null) {
            if (
                    numeroTarga.getText().toString()!=targa.getTarga() ||
                    marcaveicolo.getText().toString()!=targa.getMarca() ||
                    cognomeProprietario.getText().toString()!=targa.getCognome() ||
                    nomeProprietario.getText().toString()!=targa.getNome() ||
                    passMacchina.getText().toString()!=targa.getPass() ||
                    telefonoProprietario.getText().toString()!=targa.getTelefono() ||
                    enteDiAppartenenza.getText().toString()!=targa.getEntediappartenenza() ||
                    nomeProprietario.getText().toString()!=targa.getNome() )
                    {
                Snackbar.make(noteAggiuntive, "I dati non corrispondono salva prima di uscire", Snackbar.LENGTH_SHORT).show();

                return 3;

            }
        }

        return 0;

        }




    @Override
    public void onBackPressed() {

       if( i_campi_sono_tutti_compilati()==3){
           showAlertUscita("Registra", "I campi non corrispondono,evidentemente non hai salvato ." +
                   "vuoi comunque uscire?");

       }else {super.onBackPressed();}

    }
    private void showAlertUscita(String title, String message) {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(EditActivity.this);
        builder2.setTitle(title);
        builder2.setMessage(message);
        builder2.setCancelable(true);
        builder2.setPositiveButton(
                "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        finish();
                    }
                });

        builder2.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert12 = builder2.create();
        alert12.show();
    }
    private void riempiCampiActivity(Targa targa) {
        if(targa.getTarga().toString().isEmpty()){
            numeroTarga.setText("Non pervenuto");
        }else {
            numeroTarga.setText(targa.getTarga().toString());
        }
        if(targa.getMarca().toString().isEmpty()){
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
}
