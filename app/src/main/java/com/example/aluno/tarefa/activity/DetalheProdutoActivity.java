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
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference ref;


        ref = FirebaseDatabase.getInstance().getReference();


       // ref.child("produto").child(AppSetup.produto.getCodigoDeBarras().toString()).addValueEventListener(new ValueEventListener() {
          //  @Override
           // public void onDataChange(DataSnapshot dataSnapshot) {
              //  produto = dataSnapshot.getValue(Produto.class);

                //produto.setCodigoDeBarras(id);

                TextView tvNome = findViewById(R.id.tvNomeProduto);
                tvNome.setText(AppSetup.produto.getNome());

                TextView tvDescricao = findViewById(R.id.tvDescricaoProduto);
                tvDescricao.setText(AppSetup.produto.getDescricao());

                TextView tvValor = findViewById(R.id.tvValorProduto);
                tvValor.setText(AppSetup.produto.getValor().toString());

                TextView tvEstoque = findViewById(R.id.tvQuantidadeProduto);
                tvEstoque.setText(AppSetup.produto.getEstoque().toString());

                Button btComprar = findViewById(R.id.btnComprar);
                btComprar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AppSetup.cliente == null) {
                            startActivity(new Intent(DetalheProdutoActivity.this, ClientesActivity.class));
                            finish();
                        }
                        else{
                                EditText etQuantidade = findViewById(R.id.etQuantidadeProduto);
                                if (!etQuantidade.getText().toString().matches("") && Integer.valueOf(etQuantidade.getText().toString()) <= AppSetup.produto.getEstoque()) {
                                    AppSetup.produto.setQuantidade(Integer.valueOf(etQuantidade.getText().toString()));
                                    ItemPedido item = new ItemPedido(AppSetup.produto, AppSetup.produto.getValor() * AppSetup.produto.getQuantidade());
                                    Intent intent = new Intent(DetalheProdutoActivity.this, CestaActivity.class);
                                    AppSetup.itens.add(item);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DetalheProdutoActivity.this, "Estoque não possui itens suficientes ou o campo está vazio!", Toast.LENGTH_SHORT).show();
                                }
                        }
                    }
                });
           // }

           // @Override
           // public void onCancelled(DatabaseError databaseError) {

          //  }
        //});
    }
}
