package com.example.aluno.tarefa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.activity.ClientesActivity;
import com.example.aluno.tarefa.model.Cliente;

import java.util.List;

public class ClientesAdapter extends ArrayAdapter<Cliente> {
    Context context;
    private List<Cliente> clientes;

    public ClientesAdapter(@NonNull Context context, @NonNull List<Cliente> clientes) {
        super(context, 0, clientes);
        this.clientes = clientes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cliente cliente = clientes.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_clientes_adapter, parent, false);
        }
        TextView nome = (TextView) convertView.findViewById(R.id.tvNomeClienteAdapter);
        TextView cpf = (TextView) convertView.findViewById(R.id.tvCpfClienteAdapter);

        nome.setText(cliente.getNome());
        cpf.setText(String.valueOf(cliente.getCpf()));

        return convertView;
    }
}
