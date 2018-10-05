package com.example.aluno.tarefa.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity{

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        final EditText edEmail = findViewById(R.id.edCadastroEmail);
        final EditText edSenha = findViewById(R.id.edCadastroSenha);
        final EditText edCSenha = findViewById(R.id.edCadastroConfirmaSenha);

        Button btCadastro = findViewById(R.id.btCadastrar);

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String senha = edSenha.getText().toString();
                String CSenha = edCSenha.getText().toString();
               if (email.isEmpty() || senha.isEmpty() || CSenha.isEmpty()) {
                   Toast.makeText(CadastroActivity.this, "Existem campos vazios!", Toast.LENGTH_SHORT).show();
               }
               else if (!senha.equals(CSenha)){
                    Toast.makeText(CadastroActivity.this, "As senha não correspondem!", Toast.LENGTH_SHORT).show();
                }else if (senha.length() < 6){
                   Toast.makeText(CadastroActivity.this, "A senha deve possuir no mínimo 6 dígitos!", Toast.LENGTH_SHORT).show();
               }else if (!isEmailValid(email)){
                   Toast.makeText(CadastroActivity.this, "Digite um Email válido!!", Toast.LENGTH_SHORT).show();
               } else{
                   auth.createUserWithEmailAndPassword(email, senha);
                   startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
               }
            }
        });
    }
}

