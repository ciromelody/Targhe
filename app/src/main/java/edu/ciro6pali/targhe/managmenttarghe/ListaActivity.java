package edu.ciro6pali.targhe.managmenttarghe;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.ciro6pali.targhe.R;
import edu.ciro6pali.targhe.database.AppDatabase;
import edu.ciro6pali.targhe.database.Targa;

public class ListaActivity extends AppCompatActivity {
    private static final String TAG ="MAINACTIVITY" ;
    private ListView listView;
    //private static ArrayAdapter<String> adapter;
//private static List<String> lista;
    public static AppDatabase db;
    private static List<Targa> targas;
    private  static RecyclerView recyclerView;
    private static RecyclerViewAdapter adapter;
    private static Context basicContex;
    private static View viewLayout;
    private boolean myItemShouldBeEnabled=false;
    private static final String NOMEFLE_KEY = "NOMEFILE_KEY";
    private static final String SHARED_PREFERENCES_NAME = "FavouriteFile";
    private SharedPreferences mSharedPreferences;
    int requestCodeID=0;
    PendingIntent alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        basicContex=ListaActivity.this;
        viewLayout=findViewById(R.id.viewLayout);
        FloatingActionButton fab = findViewById(R.id.fab);
        //il codice sotto prende l'extra dell'intent che corrisponde all id della Targa allarme

              Intent intent = getIntent();
             if (intent.getExtras()!=null){
                   requestCodeID =intent.getExtras().getInt("requestCode");
             }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(EditActivity.getIntentEdit(ListaActivity.this,'a'));
                //finish();
            }
        });

        if(savedInstanceState==null){
            db= Room.databaseBuilder(ListaActivity.this, AppDatabase.class,"targhe_database").
                    allowMainThreadQueries().fallbackToDestructiveMigration().build();
            getValue();
        }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        adapter=new RecyclerViewAdapter(targas,basicContex,db);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        aggiungiTarga();


    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    private void aggiungiTarga() {
        //db.noteDao().insertAll(new  Targa("mattino", "ciro@email.com","oggetto","messaggio",150000000000L,true,"String Targa"));
    }

    public static void sendObject(Context context, int position) {
        //context.startActivity(DetailsActivity.getIntentDetails(context,targas.get(position)));

    }
    private static void getValue() {
        Log.d(TAG,"getValue  adapter.notifyDataSetChanged();");
        targas=null;

        targas = db.gettargaDao().getAll();
     /* for (Targa nt : targas) {
          //  lista.add("Titolo: " + nt.getTitle() + "\n"+"Testo: " + nt.getTarga() + "\nData: " + nt.getDate());
          avviaschedulazione_Allarme(nt);
          Log.d(TAG,"Targa1:"+nt.getTitle());
       }*/

        recyclerView=viewLayout.findViewById(R.id.recyclerview);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(basicContex);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new RecyclerViewAdapter(targas,basicContex,db);

        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);


    }
    public static  void adapterNotifyAll() {
//        lista.clear();
        getValue();
        Log.d(TAG,"adapterNotifyAll()  adapter.notifyDataSetChanged();");
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
           // startActivity(new Intent(ListaActivity.this, SettingsActivity.class));
           //finish();
            return true;
        }
        if (id == R.id.action_spiega) {
            //startActivity(new Intent(ListaActivity.this, Spiega.class));
            //finish();
            return true;
        }
        if (id == R.id.action_coffee) {
            //startActivity(new Intent(ListaActivity.this, CoffeeActivity.class));
            //finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
