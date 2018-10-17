package com.example.aluno.tarefa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.adapter.CestaAdapter;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.setup.AppSetup;

import java.util.ArrayList;

public class CestaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView lvCesta = findViewById(R.id.lvCesta);

        lvCesta.setAdapter(new CestaAdapter(CestaActivity.this, AppSetup.itens));

        Button bt = findViewById(R.id.btVoltar);
        Button bt2 = findViewById(R.id.btLimpar);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CestaActivity.this, ProdutosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSetup.itens = new ArrayList<ItemPedido>();
                startActivity(getIntent());
            }
        });
    }
}
