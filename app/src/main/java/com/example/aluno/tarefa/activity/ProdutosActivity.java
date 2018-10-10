package com.example.aluno.tarefa.activity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
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
import com.example.aluno.tarefa.adapter.ProdutosAdapter;
import com.example.aluno.tarefa.barcode.BarcodeCaptureActivity;
import com.example.aluno.tarefa.model.Produto;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity {


    private static final int RC_BARCODE_CAPTURE = 1;
    private static final String TAG = "Erro";
    Context context;
    private FirebaseDatabase fbDataBase;
    private DatabaseReference dtRef;
    final List<Produto> produtos = new ArrayList<>();
    final Produto produto = new Produto();
    ListView lvProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        //mapeia os componentes da View
        lvProdutos = findViewById(R.id.lvProdutos);

        // final ListView lvProdutos = findViewById(R.id.lvProdutos);
        fbDataBase = FirebaseDatabase.getInstance();
        dtRef = fbDataBase.getReference();
        dtRef.child("produto").orderByChild("nome")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap: dataSnapshot.getChildren()) {
                  Produto produto2 = dataSnap.getValue(Produto.class);
                  produto2.setCodigoDeBarras(Long.parseLong(dataSnap.getKey()));
                  produtos.add(produto2);
                }
                atualizarView();
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
                bundle.putLong("id", produto3.getCodigoDeBarras());
                Intent i = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    private void atualizarView() {
        lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, produtos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_produtos, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint("Digite o nome do produto");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //uma lista para nova camada de modelo da RecyclerView
                List<Produto> produtosFilter = new ArrayList<>();

                //um for-each na camada de modelo atual
                for (Produto produto : produtos) {
                    //se o nome do produto comeca com o nome digitado
                    if (produto.getNome().contains(newText)) {
                        //adiciona o produto na nova lista
                        produtosFilter.add(produto);
                    }

                }

                //coloca a nova lista como fonte de dados do novo adapter de RecyclerView
                //(Context, fonte de dados)
                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, produtosFilter));

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
                    //Toast.makeText(this, barcode.displayValue, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    //localiza o produto na lista (ou não)
                    boolean flag = true;
                    for (Produto produto : produtos){
                        if(String.valueOf(produto.getCodigoDeBarras()).equals(barcode.displayValue)){
                            flag = false;
                            Intent intent = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);
                            intent.putExtra("produto", produto);
                            startActivity(intent);
                            break;
                        }
                    }
                    if(flag){
                        Toast.makeText(this, "Produto não cadastrado.", Toast.LENGTH_SHORT).show();
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
