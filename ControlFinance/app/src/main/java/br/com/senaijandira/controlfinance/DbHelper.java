package br.com.senaijandira.controlfinance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 17170090 on 21/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "finance.db";//nome do banco de dados

    private static int DB_VERSION = 1;//versão do banco


    //mETODO CONSTRUTOR DO BANCO
    public DbHelper(Context ctx){super(ctx, DB_NAME, null,DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tabela categorias
        db.execSQL( "CREATE TABLE tbl_categoria(" +
                "_idCategoria INTEGER primary key autoincrement, " +
                "nomeCategoria TEXT);");

        db.execSQL("INSERT INTO tbl_categoria(nomeCategoria) VALUES('Alimentação');");
        db.execSQL("INSERT INTO tbl_categoria(nomeCategoria) VALUES('Lazer');");
        db.execSQL("INSERT INTO tbl_categoria(nomeCategoria) VALUES('Transporte');");
        db.execSQL("INSERT INTO tbl_categoria(nomeCategoria) VALUES('Saúde');");
        db.execSQL("INSERT INTO tbl_categoria(nomeCategoria) VALUES('Moradia');");
        db.execSQL("INSERT INTO tbl_categoria(nomeCategoria) VALUES('Salário');");
        db.execSQL("INSERT INTO tbl_categoria(nomeCategoria) VALUES('Outros');");

        //Tabela de Lançamentos
        db.execSQL("CREATE TABLE tbl_lancamento(" +
                "_idLancamento INTEGER primary key autoincrement," +
                "tipoLancamento TEXT," +
                "valor DOUBLE," +
                "data DATE," +
                "descricao TEXT," +
                "idCategoria INTEGER," +
                "FOREIGN KEY(idCategoria) REFERENCES tbl_categoria(_idCategoria));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tbl_categoria");
        db.execSQL("drop table tbl_lancamento");
        onCreate(db);
    }
}
