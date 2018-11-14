package com.example.aluno.tarefa.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.model.Produto;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ProdutoAdminActivity extends AppCompatActivity {

    private static final String TAG = "produtoAdminActivity";
    private EditText etCodigoDeBarras, etNome, etDescricao, etValor, etQuantidade;
    private Button btInserir;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_admin);

        etCodigoDeBarras = findViewById(R.id.etCodigoProduto);
        etNome = findViewById(R.id.etNomeProdutoAdmin);
        etDescricao = findViewById(R.id.etDescricaoProdutoAdmin);
        etValor = findViewById(R.id.etValorProdutoAdmin);
        etQuantidade = findViewById(R.id.etQuantidadeProdutoAdmin);
        btInserir = findViewById(R.id.btInserirProdutoAdmin);

         produto = new Produto();

        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etCodigoDeBarras.getText().toString().isEmpty() &&
                        !etNome.getText().toString().isEmpty() &&
                        !etDescricao.getText().toString().isEmpty() &&
                        !etValor.getText().toString().isEmpty() &&
                        !etQuantidade.getText().toString().isEmpty()){
                    produto.setCodigoDeBarras(Long.valueOf(etCodigoDeBarras.getText().toString()));
                    produto.setNome(etNome.getText().toString());
                    produto.setDescricao(etDescricao.getText().toString());
                    produto.setValor(Double.valueOf(etValor.getText().toString()));
                    produto.setEstoque(Integer.valueOf(etQuantidade.getText().toString()));
                    produto.setSituacao(true); //rever isso ... colocar a imagem como situação
                    Log.d(TAG, "Produto a ser cadastrado: " + produto);
                    //salva no Firebase
                    AppSetup.getDBInstance().child("produto").push().setValue(produto)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProdutoAdminActivity.this, R.string.toast_otimo_produto_cadastrado, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProdutoAdminActivity.this, R.string.toast_produto_nao_cadastrado, Toast.LENGTH_SHORT).show();
                                }
                            });

                    produto = new Produto();
                    etCodigoDeBarras.setText(null);
                    etNome.setText(null);
                    etDescricao.setText(null);
                    etValor.setText(null);
                    etQuantidade.setText(null);
                }else{
                    Toast.makeText(ProdutoAdminActivity.this, R.string.toast_todos_campos_devem_preenchidos, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, ProdutosActivity.class));
        finish();
    }
}
