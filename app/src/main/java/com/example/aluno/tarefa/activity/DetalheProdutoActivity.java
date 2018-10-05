package com.example.aluno.tarefa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Produto;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetalheProdutoActivity extends AppCompatActivity {

    private Produto produto = new Produto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalheproduto);
        AppSetup.itens = new ArrayList<ItemPedido>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference ref;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final Long id = (bundle.getLong("id"));

        ref = FirebaseDatabase.getInstance().getReference();


        ref.child("produto").child(id.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produto = dataSnapshot.getValue(Produto.class);

                produto.setId(id);

                TextView tvNome = findViewById(R.id.tvNomeProduto);
                tvNome.setText(produto.getNome());

                TextView tvDescricao = findViewById(R.id.tvDescricaoProduto);
                tvDescricao.setText(produto.getDescricao());

                TextView tvValor = findViewById(R.id.tvValorProduto);
                tvValor.setText(produto.getValor().toString());

                TextView tvEstoque = findViewById(R.id.tvQuantidadeProduto);
                tvEstoque.setText(produto.getEstoque().toString());

                Button btComprar = findViewById(R.id.btnComprar);
                btComprar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText etQuantidade = findViewById(R.id.etQuantidadeProduto);
                        if (!etQuantidade.getText().toString().matches("") && Integer.valueOf (etQuantidade.getText().toString()) <= produto.getEstoque()) {
                            produto.setQuantidade(Integer.valueOf(etQuantidade.getText().toString()));
                            ItemPedido item = new ItemPedido(produto, produto.getValor() * produto.getQuantidade());
                            AppSetup.itens.add(item);
                            Intent intent = new Intent(DetalheProdutoActivity.this, ClientesActivity.class);
                            //intent.putExtra("produto", produto);
                            startActivity(intent);
                        }else{
                            Toast.makeText(DetalheProdutoActivity.this, "Estoque não possui itens suficientes ou o campo está vazio!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
