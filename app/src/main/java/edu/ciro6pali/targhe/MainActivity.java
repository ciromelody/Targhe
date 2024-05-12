package edu.ciro6pali.targhe;

import static androidx.room.RoomDatabase.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ciro6pali.targhe.database.AppDatabase;
import edu.ciro6pali.targhe.database.Targa;

public class MainActivity extends AppCompatActivity {
    EditText numeroTarga,marcaveicolo,cognomeProprietario,nomeProprietario,passMacchina,telefonoProprietario,enteDiAppartenenza,noteAggiuntive;
    Button  salvaDati,prelevaDati;
    AppDatabase targheDB;
    List<Targa> listatargaAuto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instanziaCampi();
        salvaDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Targa targa1=new Targa( numeroTarga.getText().toString(),marcaveicolo.getText().toString(),cognomeProprietario.getText().toString(),
                                       nomeProprietario.getText().toString(),passMacchina.getText().toString(),
                                       telefonoProprietario.getText().toString(),enteDiAppartenenza.getText().toString(),
                                        noteAggiuntive.getText().toString());
                //Toast.makeText(MainActivity.this, "Targa salvata:"+targa1.getTarga(), Toast.LENGTH_SHORT).show();
                aggiungiTargaInBackground(targa1);
            }
        });
        //non funziona piu'
        targheDB= Room.databaseBuilder(MainActivity.this,AppDatabase.class,"targhe_database").
                allowMainThreadQueries().fallbackToDestructiveMigration().build();
        RoomDatabase.Callback myCallBack=new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
     //   targheDB=Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"targhe_database").addCallback(myCallBack).build();
       prelevaDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaTargheInBackground();

            }
        });

    }
    public void aggiungiTargaInBackground(Targa targa1){
        ExecutorService eseguiservizio = Executors.newSingleThreadExecutor();
        Handler handler =new Handler(Looper.getMainLooper());
        eseguiservizio.execute(new Runnable() {
                                   @Override
                                   public void run() {
                                       // processo in background
                                       targheDB.gettargaDao().insertAll(targa1);
                                       // fine processo
                                       handler.post(new Runnable() {
                                           @Override
                                           public void run() {
                                               Toast.makeText(MainActivity.this, "Aggiunta targa al database", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   }
                               }

        );
    }
    public void listaTargheInBackground(){
        ExecutorService eseguiservizio = Executors.newSingleThreadExecutor();
        Handler handler =new Handler(Looper.getMainLooper());
        eseguiservizio.execute(new Runnable() {
                                   @Override
                                   public void run() {
                                       // processo in background
                                       listatargaAuto=targheDB.gettargaDao().getAll();
                                       // fine processo
                                       handler.post(new Runnable() {
                                           @Override
                                           public void run() {
                                               StringBuilder sb =new StringBuilder();
                                               for(Targa t :listatargaAuto){
                                                   sb.append(t.getTarga());
                                                   sb.append("\n");
                                               }
                                               String datifinali=sb.toString();
                                               Toast.makeText(MainActivity.this, "Elenco Targhe:"+datifinali, Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   }
                               }

        );
    }
    public void instanziaCampi() {
        numeroTarga=findViewById(R.id.editTextTarga);
        marcaveicolo=findViewById(R.id.editTextMarcaVeicolo);
        cognomeProprietario=findViewById(R.id.editTextCognome);
        nomeProprietario=findViewById(R.id.editTextNome);
        passMacchina=findViewById(R.id.editTextPass);
        telefonoProprietario=findViewById(R.id.editTextTelefono);
        enteDiAppartenenza=findViewById(R.id.editTextEntediappartenenza);
        noteAggiuntive=findViewById(R.id.editTextMessage);
        salvaDati=findViewById(R.id.buttonSave);
        prelevaDati=findViewById(R.id.buttonGetDataTarghe);
    }
}