package edu.ciro6pali.targhe.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by gustav on 11/06/2018.
 */
@Database(entities = {Targa.class}, version =2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TargaDao gettargaDao();
    private static AppDatabase INSTANCE;

    //aggiunto per viewModel
   public static AppDatabase  getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "targhe_database")
                            .allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

}
