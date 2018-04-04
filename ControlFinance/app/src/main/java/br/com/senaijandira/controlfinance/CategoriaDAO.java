package br.com.senaijandira.controlfinance;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 17170090 on 21/03/2018.
 */

public class CategoriaDAO {

    private static CategoriaDAO instance;

    public static CategoriaDAO getInstance(){
        if(instance == null){
            instance = new CategoriaDAO();
        }

        return instance;
    }


    public ArrayList<Categoria> selecionarTodas(Context context){
        ArrayList<Categoria> retorno = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM tbl_categoria;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Categoria c = new Categoria();
            c.setIdCategoria(cursor.getInt(0));
            c.setNomeCategoria(cursor.getString(1));

            retorno.add(c);
        }

        return retorno;
    }

}
