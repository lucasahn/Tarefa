package com.example.aluno.tarefa.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.adapter.ProdutosAdapter;
import com.example.aluno.tarefa.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity {

    Context context;
    private FirebaseDatabase fbDataBase;
    private DatabaseReference dtRef;
    final List<Produto> produtos = new ArrayList<>();
    final Produto produto = new Produto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        final ListView lvProdutos = findViewById(R.id.lvProdutos);
        fbDataBase = FirebaseDatabase.getInstance();
        dtRef = fbDataBase.getReference();
        dtRef.child("produto").orderByChild("nome")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap: dataSnapshot.getChildren()) {
                  Produto produto2 = dataSnap.getValue(Produto.class);
                  produto2.setId(Long.parseLong(dataSnap.getKey()));
                  produtos.add(produto2);
                }
                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, produtos));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object objeto = lvProdutos.getItemAtPosition(position);
                Produto produto3 = (Produto) objeto;
                Bundle bundle = new Bundle();
                bundle.putLong("id", produto3.getId());
                Intent i = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
