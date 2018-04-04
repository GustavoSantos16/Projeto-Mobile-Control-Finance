package br.com.senaijandira.controlfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class LancamentoActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{
    ListView list_view_lancamentos_principal;
    LancamentoAdapter adapter;
    LancamentoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = LancamentoDAO.getInstance();

        list_view_lancamentos_principal = (ListView) findViewById(R.id.list_view_lancamentos_principal);
        adapter = new LancamentoAdapter(this, new ArrayList<Lancamento>());
        list_view_lancamentos_principal.setAdapter(adapter);


        list_view_lancamentos_principal.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Lancamento> lancamentos;
        lancamentos = dao.selecionarTodos(this);

        //limpar conteudo
        adapter.clear();

        //preenchendo adaptador
        adapter.addAll(lancamentos);


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
