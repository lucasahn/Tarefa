package com.example.aluno.tarefa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.adapter.CestaAdapter;
import com.example.aluno.tarefa.setup.AppSetup;

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
    }
}
