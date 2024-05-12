package edu.ciro6pali.targhe.managmenttarghe;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import edu.ciro6pali.targhe.R;
import edu.ciro6pali.targhe.database.AppDatabase;
import edu.ciro6pali.targhe.database.Targa;

/**
 * Created by gustav on 13/10/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewholder>
        {
private List<Targa> targas;
private List<Targa> copyList;
private Context context;
AppDatabase db_adapter;

    public RecyclerViewAdapter(List<Targa> targas, Context context) {
        this.targas = targas;
        copyList = new ArrayList<Targa>();
        copyList.addAll(targas);
        this.context = context;

    }

      public RecyclerViewAdapter(List<Targa> targas, Context context, AppDatabase dbase) {
                this.targas = targas;
                copyList = new ArrayList<Targa>();
                if (targas!=null){
                    copyList.addAll(targas);}
                this.context = context;
                db_adapter=dbase;
                if (targas!=null){
                    for (Targa nt : targas) {
                        //  lista.add("Titolo: " + nt.getTitle() + "\n"+"Testo: " + nt.getTarga() + "\nData: " + nt.getDate());
                        Log.d(TAG,"targas da costruttore adapter:"+nt.getTarga());
                    }}
      }

            @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_lista_targhe,parent,false);
        return new MyViewholder(v);
    }


    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {
        Targa targa =targas.get(position);
        //listaOggetto(Targa);
        if((targa.getTarga()!=null)&&(targa.getCognome()!=null)&&(targa.getNome()!=null)) {
            holder.targa.setText(targa.getTarga());
            holder.cognome.setText(targa.getCognome());
            holder.nome.setText(targa.getNome());
        }

        holder.touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // ListaActivity.sendObject(context,position);
                context.startActivity(DetailsActivity.getIntentDetails(context,targa));
                Toast.makeText(context, "Touch targa:"+targa.getTarga(), Toast.LENGTH_SHORT).show();
                notifyItemChanged(holder.getAdapterPosition());

            }
        });

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (targas!=null){
        return targas.size();}else {return 0;}
    }



           // Targa(String title, String email,String oggetto,Long datadiinvio,boolean allarmeattivo,String Targa, String
    public static class  MyViewholder extends RecyclerView.ViewHolder{
        private TextView targa;
        private TextView marcaveicolo;
        private TextView cognome;
        private TextView nome;

        private CardView touch_layout;

        public MyViewholder(View itemView) {
            super(itemView);
            targa=itemView.findViewById(R.id.textviewTextTarga);
           // marcaveicolo=itemView.findViewById(R.id.);
            cognome=itemView.findViewById(R.id.textviewTextCognome);
            nome=itemView.findViewById(R.id.textviewTextNome);

            touch_layout=itemView.findViewById(R.id.id_cardview);
        }
    }


    public void filter(String queryText)
    {
        targas.clear();

        if(queryText.isEmpty())
        {
            targas.addAll(copyList);
        }
        else
        {

            for(Targa ogettoTarga: copyList)
            {
                if(ogettoTarga.getTarga().toLowerCase().contains(queryText.toLowerCase()))
                {
                    targas.add(ogettoTarga);
                }
            }

        }

        notifyDataSetChanged();
    }

            public static void listaOggetto(Targa nt){
                //for (Targa nt : obj) {
                Log.d(TAG,"Titolo: " + nt.getTarga() + "\n"+"Targa: " + nt.getTarga() + "\nCognome: " + nt.getCognome()+"\n"+"nome:"+nt.getNome()+"\n");




                //}
            }
}
