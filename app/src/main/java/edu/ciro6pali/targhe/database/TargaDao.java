package edu.ciro6pali.targhe.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface TargaDao {
    @Query("SELECT * FROM Targhe")
    List<Targa> getAll();



    @Query("select * from Targhe")
    LiveData<List<Targa>> loadAllTarghe();

    @Insert
    void insertAll(Targa... targas);

    @Delete
    void delete(Targa targa);

    @Query("DELETE FROM Targhe WHERE id = :id")
    abstract void deleteById(int id);

    @Query("DELETE FROM Targhe WHERE 0 = 0")
    abstract void deleteAllTable();

    @Query("UPDATE Targhe SET targa_column= :targa ," +
            "marca_column= :marca ," +
            "cognome_column =:cognome," +
            "nome_column =:nome ," +
            "pass_column =:pass ," +
            "telefono_column =:telefono ," +
            "entediappartenenza_column =:entediappartenenza ," +
            "messaggio_column =:messaggio "+
             "WHERE id = :id ")
    int updateTarga(int id, String targa,String marca, String cognome, String nome, String pass, String telefono, String entediappartenenza, String messaggio);

    @Query("UPDATE Targhe SET targa_column =:targa   WHERE id = :id ")
    int updateItem_1(int id,String targa);
}

