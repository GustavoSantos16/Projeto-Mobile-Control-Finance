package br.com.senaijandira.controlfinance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by 17170090 on 20/03/2018.
 */

public class LancamentoAdapter extends ArrayAdapter<Lancamento> {

    public LancamentoAdapter(Context ctx, ArrayList<Lancamento> lstLancamento) {
        super(ctx, 0, lstLancamento);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_lancamento, null);
        }
        Lancamento lancamento = getItem(position);

        TextView txt_descricao = v.findViewById(R.id.txt_descricao);
        TextView txt_valor_lancamento = v.findViewById(R.id.txt_valor_lancamento);
        TextView txt_categoria = v.findViewById(R.id.txt_categoria);

        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt","br"));

        double valor = lancamento.getValor();

        txt_descricao.setText(lancamento.getDescricao());
        txt_categoria.setText(lancamento.getNomeCategoria());
        txt_valor_lancamento.setText(f.format(valor));

        return v;
    }

}
