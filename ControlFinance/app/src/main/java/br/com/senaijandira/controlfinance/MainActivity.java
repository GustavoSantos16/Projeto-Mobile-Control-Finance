package br.com.senaijandira.controlfinance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    ListView list_view_lancamentos ;
    LancamentoDAO dao;
    LancamentoAdapter adapter;
    TextView txt_saldo;
    LinearLayout bg_saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),InserirActivity.class);

                startActivity(intent);
            }
        });

        dao = LancamentoDAO.getInstance();

        txt_saldo = (TextView) findViewById(R.id.txt_saldo);
        list_view_lancamentos = (ListView) findViewById(R.id.list_view_lancamentos);
        bg_saldo = (LinearLayout) findViewById(R.id.bg_saldo);

        adapter = new LancamentoAdapter(this, new ArrayList<Lancamento>());
        list_view_lancamentos.setAdapter(adapter);

        list_view_lancamentos.setOnItemClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        //Pegando os lancamentos do banco
        ArrayList<Lancamento> lancamentos;
        lancamentos = dao.selecionarTodos(this);

        //limpar conteudo
        adapter.clear();

        //preenchendo adaptador
        adapter.addAll(lancamentos);

        Double saldo = dao.mostrarSaldo(this);

        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt","br"));//Formatando em reais
        txt_saldo.setText(f.format(saldo));//Saldo

        if(saldo < 0){
            bg_saldo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }else if(saldo >= 0){
            bg_saldo.setBackgroundColor(getResources().getColor(R.color.verdinho));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SobreActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);


    }

    public void telaLancamentos(View view) {

        Intent intent = new Intent(getApplicationContext(),LancamentoActivity.class);
        startActivity(intent);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //pegando o contato da posição i da lista
        Lancamento item = adapter.getItem(i);

        //Criando objeto de itenção
        Intent intent = new Intent(this,VisualizarActivity.class);

        //passando id do contato
        intent.putExtra("idLancamento",item.getIdLancamento());

        //Abrindo tela de visualizar
        startActivity(intent);
    }


}
