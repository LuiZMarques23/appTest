package com.luizhenriquemarquessilva.controledeprodutos.activity;

import static com.luizhenriquemarquessilva.controledeprodutos.helper.FirebaseHelper.getAutenticado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.luizhenriquemarquessilva.controledeprodutos.R;
import com.luizhenriquemarquessilva.controledeprodutos.autenticacao.LoginActivity2;
import com.luizhenriquemarquessilva.controledeprodutos.helper.FirebaseHelper;

public class SplashActity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::verificaLogin, 3000);



    }

    private  void verificaLogin() {
        if (FirebaseHelper.getAutenticado()) {
            startActivity(new Intent(this,MainActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity2.class));

        }
    }
}