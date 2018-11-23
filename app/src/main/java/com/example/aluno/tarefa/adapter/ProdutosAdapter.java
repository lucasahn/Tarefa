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
import com.example.aluno.tarefa.model.Produto;

import java.text.NumberFormat;
import java.util.List;

public class ProdutosAdapter extends ArrayAdapter<Produto> {

    Context context;
    private List<Produto> produtos;

    public ProdutosAdapter(@NonNull Context context, @NonNull List<Produto> produtos) {
        super(context, 0, produtos);
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Produto produto = produtos.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_produtos_adapter, parent, false);
        }
        TextView nome = (TextView) convertView.findViewById(R.id.tvNomeProdutoAdapter);
        TextView preco = (TextView) convertView.findViewById(R.id.tvQuantidadeProdutoCestaAdapter);
        TextView id = (TextView) convertView.findViewById(R.id.txtid);

        nome.setText(produto.getNome());
        preco.setText(NumberFormat.getCurrencyInstance().format(produto.getValor()));
        id.setText(produto.getKey());

        return convertView;
    }
}
