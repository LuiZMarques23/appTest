package com.luizhenriquemarquessilva.controledeprodutos.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.luizhenriquemarquessilva.controledeprodutos.R;
import com.luizhenriquemarquessilva.controledeprodutos.adapter.AdapterProduto;
import com.luizhenriquemarquessilva.controledeprodutos.autenticacao.LoginActivity2;
import com.luizhenriquemarquessilva.controledeprodutos.helper.FirebaseHelper;
import com.luizhenriquemarquessilva.controledeprodutos.model.Produto;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.onClick {

    private AdapterProduto adapterProduto;
    private final List<Produto> produtoList = new ArrayList<>();
    private SwipeableRecyclerView rvProdutos;

    private ImageButton ibAdd;
    private ImageButton ibVerMais;
    private TextView text_info;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        iniciaComponetes();


        ouvinteCliques();

        configRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        recuperaProdutos();
    }



    private void recuperaProdutos(){
        DatabaseReference produtosRef = FirebaseHelper.getDatabaseReference()
                .child("produtos")
                .child(FirebaseHelper.getIdFirebase());
        produtosRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Produto produto = snap.getValue(Produto.class);
                    produtoList.add(produto);

                }

                verificaQtdLista();

                Collections.reverse(produtoList);


                adapterProduto.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void ouvinteCliques(){
        ibAdd.setOnClickListener(View -> {
            startActivity(new Intent(this, FormProdutoActivity2.class));
        });

        ibVerMais.setOnClickListener(view ->{
            PopupMenu popupMenu = new PopupMenu(this, ibVerMais);
            popupMenu.getMenuInflater().inflate(R.menu.menu_toolbar,popupMenu.getMenu());


            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_sobre){
                    Toast.makeText(this, "controle Estoque  ", Toast.LENGTH_SHORT).show();

                }else if (menuItem.getItemId() == R.id.menu_sair){
                    FirebaseHelper.getAuth().signOut();
                    startActivity(new Intent(this, LoginActivity2.class));
                }
                return true;
            });


            popupMenu.show();



        });
    }

    private void configRecyclerView(){


        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtoList, this);
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {


            }

            @Override
            public void onSwipedRight(int position) {

                Produto produto = produtoList.get(position);



                produtoList.remove(produto);
                adapterProduto.notifyItemRemoved(position);






                verificaQtdLista();


            }
        });
    }

    private void verificaQtdLista(){
        if(produtoList.size() == 0){
            text_info.setText("Nenhum produto cadastrado.");
            text_info.setVisibility(View.VISIBLE);
        }else {
            text_info.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void iniciaComponetes(){
        ibAdd = findViewById(R.id.ib_add);
        ibVerMais = findViewById(R.id.ib_ver_mais);
        rvProdutos = findViewById(R.id.rvProdutos);
        text_info = findViewById(R.id.text_info);
        progressBar = findViewById(R.id.progressBar);
    }


    @Override
    public void onClickListener(Produto produto) {
        Intent intent = new Intent(this, FormProdutoActivity2.class);
        intent.putExtra("produto",produto);
        startActivity(intent);

    }



}