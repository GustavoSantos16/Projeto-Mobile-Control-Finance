package br.com.senaijandira.controlfinance;

import android.graphics.Bitmap;

/**
 * Created by 17170090 on 20/03/2018.
 */

public class Categoria {
    private int idCategoria;
    private String nomeCategoria;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString() {
        return nomeCategoria;
    }
}
