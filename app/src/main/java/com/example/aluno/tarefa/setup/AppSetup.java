package com.example.aluno.tarefa.setup;

import android.util.Log;

import com.example.aluno.tarefa.model.Cliente;
import com.example.aluno.tarefa.model.ItemPedido;
import com.example.aluno.tarefa.model.Produto;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppSetup {

    private static final String TAG = "appSetup";
    public static FirebaseUser user = null;
    private static DatabaseReference myRef = null;
    public static List<ItemPedido> itens = new ArrayList<ItemPedido>();
    public static Cliente cliente = null;
    public static Produto produto = new Produto();
    public static List<Produto> produtos = new ArrayList<>();
    public static List<String> keysProdutos = new ArrayList<>();

    public static DatabaseReference getDBInstance(){
        //se ainda não tem a referência para o realtimedatabase, a pega
        if(myRef == null) {
            // obtém o objeto de acesso ao seviço de database do Firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            // obtém o objeto de referência para a coleção de objetos
            myRef = database.getReference();
            Log.d(TAG, "Conectou ao banco de dados? " + myRef);
        }
        //se já tem a referência, só a retorna
        return myRef;
    }
}
