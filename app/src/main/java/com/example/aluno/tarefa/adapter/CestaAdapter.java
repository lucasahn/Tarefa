package com.example.aluno.tarefa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.activity.CestaActivity;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Produto;

import java.text.NumberFormat;
import java.util.List;

public class CestaAdapter extends ArrayAdapter<ItemPedido>{

    Context context;
    private List<ItemPedido> itens;

    public CestaAdapter(@NonNull Context context, @NonNull List<ItemPedido> itens) {
        super(context, 0, itens);
        this.itens = itens;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemPedido item = itens.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cesta_adapter, parent, false);
        }
        TextView nome = (TextView) convertView.findViewById(R.id.tvNomeProdutoCestaAdapter);
        TextView qtd = (TextView) convertView.findViewById(R.id.tvQuantidadeProdutoCestaAdapter);
        TextView total = (TextView) convertView.findViewById(R.id.tvTotalProdutoCestaAdapter);

        nome.setText(item.getProduto().getNome());
        qtd.setText(item.getProduto().getQuantidade().toString());
        total.setText(NumberFormat.getCurrencyInstance().format(item.getTotal()));

        return convertView;
    }
}
