package com.example.aluno.tarefa.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.adapter.ClientesAdapter;
import com.example.aluno.tarefa.model.Cliente;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Produto;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientesActivity extends AppCompatActivity {

    Context context;
    private FirebaseDatabase fbDataBase;
    private DatabaseReference dtRef;
    final List<Cliente> clientes = new ArrayList<>();
    final Cliente cliente = new Cliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_clientes);
            final ListView lvClientes = findViewById(R.id.lvClientes);
            fbDataBase = FirebaseDatabase.getInstance();
            dtRef = fbDataBase.getReference();
            dtRef.child("cliente").orderByChild("nome")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                Cliente cliente2 = dataSnap.getValue(Cliente.class);
                                //Toast.makeText(ClientesActivity.this, cliente2.toString(), Toast.LENGTH_SHORT).show();
                                cliente2.setCodigo(Long.parseLong(dataSnap.getKey()));
                                clientes.add(cliente2);
                            }
                            lvClientes.setAdapter(new ClientesAdapter(ClientesActivity.this, clientes));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object objeto = lvClientes.getItemAtPosition(position);
                    Cliente cliente3 = (Cliente) objeto;
                    AppSetup.cliente = new Cliente();
                    AppSetup.cliente = cliente3;
                    //Intent i2 = new Intent(ClientesActivity.this, DetalheProdutoActivity.class);
                    //startActivity(i2);
                    finish();
                }
            });
    }
}
