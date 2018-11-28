package com.example.aluno.tarefa.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.adapter.ClientesAdapter;
import com.example.aluno.tarefa.adapter.ProdutosAdapter;
import com.example.aluno.tarefa.barcode.BarcodeCaptureActivity;
import com.example.aluno.tarefa.model.Cliente;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Produto;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
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
    ListView lvClientes;
    private static final int RC_BARCODE_CAPTURE = 1;
    private static final String TAG = "Erro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_clientes);

        lvClientes = findViewById(R.id.lvClientes);

            fbDataBase = FirebaseDatabase.getInstance();
            dtRef = fbDataBase.getReference();
            dtRef.child("cliente").orderByChild("nome")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                Cliente cliente2 = dataSnap.getValue(Cliente.class);
                                //Toast.makeText(ClientesActivity.this, cliente2.toString(), Toast.LENGTH_SHORT).show();
                                //cliente2.setCodigo(Long.parseLong(dataSnap.getKey()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_clientes, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint("Digite o nome do cliente");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //uma lista para nova camada de modelo da RecyclerView
                List<Cliente> clientesFilter = new ArrayList<>();

                //um for-each na camada de modelo atual
                for (Cliente cliente : clientes) {
                    //se o nome do produto comeca com o nome digitado
                    if (cliente.getNome().contains(newText)) {
                        //adiciona o produto na nova lista
                        clientesFilter.add(cliente);
                    }

                }

                //coloca a nova lista como fonte de dados do novo adapter de RecyclerView
                //(Context, fonte de dados)
                lvClientes.setAdapter(new ClientesAdapter(ClientesActivity.this, clientesFilter));

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_barcode: {
                Intent intent = new Intent(this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                intent.putExtra(BarcodeCaptureActivity.UseFlash, true);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
                break;
            }
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Toast.makeText(this, barcode.displayValue, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    //localiza o produto na lista (ou não)
                    boolean flag = true;
                    for (Cliente cliente : clientes){
                        if(String.valueOf(cliente.getCodigo()).equals(barcode.displayValue)){
                            flag = false;
                            AppSetup.cliente = cliente;
                            finish();
                            break;
                        }
                    }
                    if(flag){
                        Toast.makeText(this, "Cliente não cadastrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.barcode_failure, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(this, String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
