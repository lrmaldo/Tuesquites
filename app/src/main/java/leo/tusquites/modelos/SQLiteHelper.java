package leo.tusquites.modelos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leo on 14/11/2016.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table productos (id  INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, precio text,cantidad text)");
        db.execSQL("create table productos_ini (id  INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, precio real,cantidad integer)");
        db.execSQL("create table productos_imp (id  INTEGER PRIMARY KEY AUTOINCREMENT, descripcion text, subtotal real, precio text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table productos_imp (id  INTEGER PRIMARY KEY AUTOINCREMENT, descripcion text, subtotal real, precio text)");
    }
}
