package com.example.aluno.tarefa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.aluno.tarefa.setup.AppSetup;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CestaActivity extends AppCompatActivity {

   private ListView lvCesta;
   private Double totalPedido;
   //private TextView tvCliente = findViewById(R.id.tvCestaCliente);
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

        lvCesta.setAdapter(new CestaAdapter(CestaActivity.this, AppSetup.itens));

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
                alertDialogSalvarPedido("Quase lá", "\nTotal do Pedido:" + NumberFormat.getCurrencyInstance().format(10));
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
                Toast.makeText(CestaActivity.this, "Ótimo! Vendido.", Toast.LENGTH_SHORT).show();
                /*
                 *  persistir a o pedido no Firebase aqui!!!!!!!!!!!! Lembrar de atualizar o estoque
                 *  e controlar as exceções.
                 */
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
}
