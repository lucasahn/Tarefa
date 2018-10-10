package com.example.aluno.tarefa.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.model.Cliente;
import com.example.aluno.tarefa.model.Compra;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Produto;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CompraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        Produto produto = (Produto) getIntent().getSerializableExtra("produto");
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        produto.setEstoque(produto.getEstoque() - produto.getQuantidade());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("produto").child(produto.getCodigoDeBarras().toString()).child("estoque").setValue(produto.getEstoque());

        Date hoje = Calendar.getInstance().getTime();
        SimpleDateFormat dataformato = new SimpleDateFormat("dd-MM-yyyy");
        String data = dataformato.format(hoje);

        Compra compra = new Compra(cliente.getCodigo(), produto.getCodigoDeBarras(), produto.getQuantidade(), data);

        ref.child("compra").push().setValue(compra);
        AppSetup.itens = new ArrayList<ItemPedido>();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(CompraActivity.this, ProdutosActivity.class));
            }
        }, 2000);
    }
}
