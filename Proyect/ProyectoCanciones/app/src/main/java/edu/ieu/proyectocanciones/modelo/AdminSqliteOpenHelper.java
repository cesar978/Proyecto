package edu.ieu.proyectocanciones.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class AdminSqliteOpenHelper extends SQLiteOpenHelper {
    private static final String NAME="administracion";
    private static final int VERSION=1;
    public AdminSqliteOpenHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase ) {
        String sql="CREATE TABLE canciones("+
                "id INTEGER primary key,"+
                "titulo text,"+
                "artista text," +
                "album text,"+
                "compositor text,"+
                "genero text,"+
                "fecha text)";
        sqLiteDatabase.execSQL(sql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
