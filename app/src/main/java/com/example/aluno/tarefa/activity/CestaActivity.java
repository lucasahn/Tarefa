package com.example.aluno.tarefa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.adapter.CestaAdapter;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Pedido;
import com.example.aluno.tarefa.model.Produto;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CestaActivity extends AppCompatActivity {

   private ListView lvCesta;
   private Double totalPedido;
   private TextView tvCliente;
   private TextView tvValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lvCesta = findViewById(R.id.lvCesta);
        tvCliente = findViewById(R.id.tvCestaCliente);
        tvCliente.setText(AppSetup.cliente.getNome());
        atualizarView();

        lvCesta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialogExcluirItem("Atenção", "Você realmente deseja excluir este item?", position);

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_carrinho, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.menuitem_salvar: {
                 if(AppSetup.itens.isEmpty()){
                     Toast.makeText(this, R.string.toast_carrinho_esta_vazio, Toast.LENGTH_SHORT).show();
                 }else {
                     alertDialogSalvarPedido("Quase lá", "\nTotal do Pedido:" + NumberFormat.getCurrencyInstance().format(totalPedido));
                 }
                 break;
             }
             case R.id.menuitem_cancelar:{
                 alertDialogCancelarPedido("Ops, vai cancelar?", "Tem certeza que quer cancelar o pedido?");
                 break;
             }
         }

         return true;
    }

    private void alertDialogSalvarPedido(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                salvarEstoque();
               // Toast.makeText(CestaActivity.this, "Ótimo! Vendido.", Toast.LENGTH_SHORT).show();
                AppSetup.itens.clear();
                AppSetup.cliente = null;
                finish();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Ótimo.Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void alertDialogCancelarPedido(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Que pena! Pedido cancelado.", Toast.LENGTH_SHORT).show();
                AppSetup.itens.clear();
                AppSetup.cliente = null;
                finish();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Ótimo! Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void alertDialogExcluirItem(String titulo, String mensagem, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppSetup.itens.remove(position); //remove do carrinho
                Toast.makeText(CestaActivity.this, "Produto removido.", Toast.LENGTH_SHORT).show();
                atualizarView();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void atualizarView() {
        lvCesta.setAdapter(new CestaAdapter(CestaActivity.this, AppSetup.itens));
        //totaliza o pedido
        totalPedido = new Double(0);
        for(ItemPedido itemPedido : AppSetup.itens){
            totalPedido += itemPedido.getTotal();
        }
        tvValor = findViewById(R.id.tvCestaValor);
       tvValor.setText(NumberFormat.getCurrencyInstance().format(totalPedido));
    }

    private boolean salvarEstoque() {
        DatabaseReference estoqueRef;
       // for (final ItemPedido item : AppSetup.itens) {
            estoqueRef = FirebaseDatabase.getInstance().getReference("produto");
            estoqueRef.runTransaction(new Transaction.Handler() {
                public static final String TAG = "CestaActivity";

                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    Array produtos = mutableData.getValue(Array.class);
                   // if (estoque != null && estoque >= item.getProduto().getQuantidade()) {
                       // mutableData.setValue(estoque - item.getProduto().getQuantidade());
                    //}
                    Log.d(TAG, produtos.toString());
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                    Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                }
            });
      //  }

        return true;
    }
}
