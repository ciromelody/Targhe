package edu.ciro6pali.targhe.database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Targhe")
public class Targa implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "targa_column")
    private String targa;

    @ColumnInfo(name = "marca_column")
    private String marca;
    @ColumnInfo(name = "cognome_column")
    private String cognome;
    @ColumnInfo(name = "nome_column")
    private String nome;
    @ColumnInfo(name = "pass_column")
    private String pass;
    @ColumnInfo(name = "telefono_column")
    private String telefono;
    @ColumnInfo(name = "entediappartenenza_column")
    private String entediappartenenza;
    @ColumnInfo(name = "messaggio_column")
    private String messaggio;
    @Ignore
    public Targa(){

    }
    public Targa( String targa, String marca,String cognome, String nome, String pass, String telefono, String entediappartenenza, String messaggio) {
        //this.id = 0;
        this.targa = targa;
        this.marca = marca;
        this.cognome = cognome;
        this.nome = nome;
        this.pass = pass;
        this.telefono = telefono;
        this.entediappartenenza = entediappartenenza;
        this.messaggio = messaggio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEntediappartenenza() {
        return entediappartenenza;
    }

    public void setEntediappartenenza(String entediappartenenza) {
        this.entediappartenenza = entediappartenenza;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
