package com.example.aluno.tarefa.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.model.Cliente;
import com.example.aluno.tarefa.model.Produto;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ClienteAdminActivity extends AppCompatActivity {

    private static final String TAG = "clienteAdminActivity";
    private EditText etCodigoDeBarras, etNome, etCpf;
    private Button btInserir;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_admin);

        etCodigoDeBarras = findViewById(R.id.etCodigoCliente);
        etNome = findViewById(R.id.etNomeCliente);
        etCpf = findViewById(R.id.etCPFCliente);

        btInserir = findViewById(R.id.btInserirProdutoAdmin);

        cliente = new Cliente();

        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etCodigoDeBarras.getText().toString().isEmpty() &&
                        !etNome.getText().toString().isEmpty() &&
                        !etCpf.getText().toString().isEmpty()){
                    cliente.setCodigo(Long.valueOf(etCodigoDeBarras.getText().toString()));
                    cliente.setNome(etNome.getText().toString());
                    cliente.setCpf(etCpf.getText().toString());
                    Log.d(TAG, "Cliente a ser cadastrado: " + cliente);
                    //salva no Firebase
                    AppSetup.getDBInstance().child("cliente").push().setValue(cliente)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ClienteAdminActivity.this, R.string.toast_otimo_cliente_cadastrado, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ClienteAdminActivity.this, R.string.toast_cliente_nao_cadastrado, Toast.LENGTH_SHORT).show();
                                }
                            });

                    cliente = new Cliente();
                    etCodigoDeBarras.setText(null);
                    etNome.setText(null);
                    etCpf.setText(null);
                }else{
                    Toast.makeText(ClienteAdminActivity.this, R.string.toast_todos_campos_devem_preenchidos, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, ProdutosActivity.class));
        finish();
    }
}

