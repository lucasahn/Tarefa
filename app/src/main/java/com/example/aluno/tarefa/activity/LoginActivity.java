package com.example.aluno.tarefa.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.tarefa.R;
import com.example.aluno.tarefa.setup.AppSetup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



mAuth = FirebaseAuth.getInstance();

final EditText etEmail = findViewById(R.id.etEmailLogin);
final EditText senha = findViewById(R.id.etSenhaLogin);

((Button) findViewById(R.id.btLogin)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        signin(etEmail.getText().toString(), senha.getText().toString());
    }

});

        ((Button) findViewById(R.id.btCadastrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
            }

        });

    }

    private void signin(String email, String senha){
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private static final String TAG = "LoginActivity";

                    @Override
                    public void onComplete (@NonNull Task< AuthResult > task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            AppSetup.user = mAuth.getCurrentUser();
                            carregarProdutos();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }

                });
    }

    private void carregarProdutos(){
        startActivity(new Intent(LoginActivity.this, ProdutosActivity.class));
    }
}