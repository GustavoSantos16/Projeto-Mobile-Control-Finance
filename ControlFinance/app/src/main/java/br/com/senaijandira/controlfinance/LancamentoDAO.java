package br.com.senaijandira.controlfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 17170090 on 21/03/2018.
 */

public class LancamentoDAO {

    private static LancamentoDAO instance;

    public static LancamentoDAO getInstance(){
        if(instance == null){
            instance = new LancamentoDAO();
        }

        return instance;
    }

    public Boolean inserir(Context ctx, Lancamento l){
        //Acessar o banco em modo escrita
        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("tipoLancamento",l.getTipoLancamento());
        valores.put("valor",l.getValor());
        valores.put("data",l.getData());
        valores.put("descricao",l.getDescricao());
        valores.put("idCategoria",l.getIdCategoria());

        Long id = db.insert("tbl_lancamento",null,valores);
        if(id != -1){
            return true;
        }else{
            return false;
        }

    }
    public ArrayList<Lancamento> selecionarTodos(Context context){
        ArrayList<Lancamento> retorno = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "select * from tbl_lancamento l inner join tbl_categoria c on l.idCategoria = c._idCategoria";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Lancamento l = new Lancamento();
            l.setIdLancamento(cursor.getInt(0));
            l.setTipoLancamento(cursor.getString(1));
            l.setValor(cursor.getDouble(2));
            l.setData(cursor.getString(3));
            l.setDescricao(cursor.getString(4));
            l.setIdCategoria(cursor.getInt(5));
            l.setNomeCategoria(cursor.getString(7));


            retorno.add(l);
        }
        return retorno;
    }

    public Double mostrarSaldo(Context context){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "SELECT SUM(valor) FROM tbl_lancamento";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        return cursor.getDouble(0);
    }

    public Lancamento selecionarUm(Context context, int id){
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        //String sql = "select * from tbl_lancamento where _idLancamento = "+ id;

        String sql = "select * from tbl_lancamento l inner join tbl_categoria c on l.idCategoria = c._idCategoria and l._idLancamento = "+id;


        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            Lancamento l = new Lancamento();
            l.setIdLancamento(cursor.getInt(0));
            l.setTipoLancamento(cursor.getString(1));
            l.setValor(cursor.getDouble(2));
            l.setData(cursor.getString(3));
            l.setDescricao(cursor.getString(4));
            l.setIdCategoria(cursor.getInt(5));
            l.setNomeCategoria(cursor.getString(7));

            cursor.close();
            return l;
        }

        return null;
    }

    public Boolean remover(Context context,Integer id){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        db.delete("tbl_lancamento","_idLancamento = ?"
                , new String[]{id.toString()});

        return false;
    }

    public Boolean atualizar (Context context, Lancamento l){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("tipoLancamento",l.getTipoLancamento());
        valores.put("valor",l.getValor());
        valores.put("data",l.getData());
        valores.put("descricao",l.getDescricao());
        valores.put("idCategoria",l.getIdCategoria());

        db.update("tbl_lancamento", valores, "_idLancamento = ?",new String[]{String.valueOf(l.getIdLancamento())});
        return true;
    }


}
