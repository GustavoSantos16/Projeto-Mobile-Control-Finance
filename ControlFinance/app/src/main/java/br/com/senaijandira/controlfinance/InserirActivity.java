package br.com.senaijandira.controlfinance;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InserirActivity extends AppCompatActivity {

    RadioButton rb_receita, rb_despesa;
    EditText txt_lancamento, txt_descricao, txt_data;
    Button btn_salvar;
    Spinner sp_categorias;

   // private static String[] CATEGORIAS = new String[]
          //  {"Lazer","Transporte","Saúde","Moradia","Salário","Outros"};

    CategoriaDAO dao = CategoriaDAO.getInstance();


    Boolean modoEdicao = false;
    Lancamento lancamento;

    SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_despesa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_lancamento = (EditText) findViewById(R.id.txt_lancamento);
        txt_descricao = (EditText) findViewById(R.id.txt_descricao);
        txt_data = (EditText) findViewById(R.id.txt_data);
        rb_receita = (RadioButton) findViewById(R.id.rb_receita);
        rb_despesa = (RadioButton) findViewById(R.id.rb_despesa);
        btn_salvar = (Button) findViewById(R.id.btn_salvar);

        ArrayList<Categoria> categorias = dao.selecionarTodas(this);

        sp_categorias = (Spinner) findViewById(R.id.sp_categorias);
        ArrayAdapter adapter = new ArrayAdapter<Categoria>(this,R.layout.support_simple_spinner_dropdown_item, categorias);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_categorias.setAdapter(adapter);


        Integer idLancamento = getIntent().getIntExtra("idLancamento", 0);

        String dataFormatada = formataData.format(data);
        txt_data.setText(dataFormatada);

        if(idLancamento != 0){
            modoEdicao = true;
            lancamento = LancamentoDAO.getInstance().selecionarUm(this,idLancamento);

            txt_lancamento.setText(lancamento.getValor().toString());

           if(lancamento.getTipoLancamento().equals("despesa")){
               rb_despesa.setChecked(true);
               txt_lancamento.setBackgroundColor(getResources().getColor(R.color.colorAccent));
               btn_salvar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
           }else if(lancamento.getTipoLancamento().equals("receita")){
               rb_receita.setChecked(true);
               txt_lancamento.setBackgroundColor(getResources().getColor(R.color.verdinho));
               btn_salvar.setBackgroundColor(getResources().getColor(R.color.verdinho));
           }

            int i=0;
           for(Categoria cat : categorias){
               if(cat.getIdCategoria() == lancamento.getIdCategoria()){
                   sp_categorias.setSelection(i);
                   break;
               }
               i++;
           }
            txt_data.setText(lancamento.getData());

           txt_descricao.setText(lancamento.getDescricao());


        }

    }


    /*METODO SALVAR LANCAMENTOS*/
    public void salvar(View view) {
        Lancamento l;

        if(modoEdicao){
            l = lancamento;
        }else{
            l = new Lancamento();
        }
        //Setando valores

        //Valor e TIPO de lançamento
        if(txt_lancamento.getText().toString().isEmpty()){
            txt_lancamento.setError("Campo obrigatório");
            return;
        }

        Double valor = Double.parseDouble(txt_lancamento.getText().toString());//Double to string

        if(valor < 0){
            txt_lancamento.setError("Por favor, apagar o sinal de -");
            return;
        }
        if(rb_despesa.isChecked()){
            valor = valor * -1;//Transformando para negativo
            l.setValor(valor);
            l.setTipoLancamento("despesa");
        }else if(rb_receita.isChecked()){
            l.setValor(valor);
            l.setTipoLancamento("receita");
        }else {
            rb_receita.setError("Por favor, selecione uma das opções");
            rb_despesa.setError("Por favor, selecione uma das opções");
            return;
        }

        //Categoria
        Categoria categoria =(Categoria) sp_categorias.getSelectedItem();
        l.setIdCategoria(categoria.getIdCategoria());

        //Data
        if(txt_data.getText().toString().isEmpty()){
            txt_data.setError("Por favor, preencha uma data correta");
            return;
        }
        l.setData(txt_data.getText().toString());

        //Descrição
        if(txt_descricao.getText().toString().isEmpty()){
            txt_descricao.setError("Campo obrigatório");
            return;
        }
        l.setDescricao(txt_descricao.getText().toString());


        //Método salvar

        if(modoEdicao){
            LancamentoDAO.getInstance().atualizar(this, l);
            Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        }else{
            LancamentoDAO.getInstance().inserir(this,l);
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
        }


        finish();
    }

    public void corReceita(View view) {
        txt_lancamento.setBackgroundColor(getResources().getColor(R.color.verdinho));
        btn_salvar.setBackgroundColor(getResources().getColor(R.color.verdinho));

    }

    public void corDespesa(View view) {
        txt_lancamento.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btn_salvar.setBackgroundColor(getResources().getColor(R.color.colorAccent));

    }
}
