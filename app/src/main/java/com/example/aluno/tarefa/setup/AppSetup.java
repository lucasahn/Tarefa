package com.example.aluno.tarefa.setup;

import com.example.aluno.tarefa.model.Cliente;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Produto;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AppSetup {

    public static FirebaseUser user = null;
    public static List<ItemPedido> itens = new ArrayList<ItemPedido>();
    public static Cliente cliente = null;
    public static Produto produto = new Produto();
}
