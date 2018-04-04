package br.com.senaijandira.controlfinance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class VisualizarActivity extends AppCompatActivity {

    TextView txt_valor, txt_categoria, txt_data, txt_descricao;
    Integer idLancamento;
    LinearLayout cor_linear;

    LancamentoDAO dao = LancamentoDAO.getInstance();

    Lancamento l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_valor = (TextView) findViewById(R.id.txt_valor);
        txt_categoria = (TextView) findViewById(R.id.txt_categoria);
        txt_data = (TextView)findViewById(R.id.txt_data);
        txt_descricao = (TextView)findViewById(R.id.txt_descricao);
        cor_linear = (LinearLayout)findViewById(R.id.cor_linear);

        Intent intent = getIntent();

        idLancamento = intent.getIntExtra("idLancamento",0);


    }

    @Override
    protected void onResume() {
        super.onResume();

        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt","br"));
        l = dao.selecionarUm(this, idLancamento);
        Double valor = l.getValor();

        if(valor < 0){
            cor_linear.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        txt_valor.setText(f.format(valor));
        txt_categoria.setText(l.getNomeCategoria());
        txt_data.setText(l.getData());
        txt_descricao.setText(l.getDescricao());

    }

    public void excluir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir");
        builder.setMessage("Tem certeza que deseja excluir esse lançamento");

        final Context context = this;
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LancamentoDAO.getInstance().remover(context,idLancamento);
                finish();//Fechar tela atual
            }
        });

        builder.setNegativeButton("NÂO", null);
        builder.create().show();

    }

    public void editar(View view) {
        Intent intent = new Intent(this, InserirActivity.class);
        intent.putExtra("idLancamento", idLancamento);
        startActivity(intent);
    }
}
