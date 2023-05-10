package com.luizhenriquemarquessilva.controledeprodutos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luizhenriquemarquessilva.controledeprodutos.R;
import com.luizhenriquemarquessilva.controledeprodutos.model.Produto;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder> {

    private List<Produto> produtoList;
    private onClick onClick;

    public AdapterProduto(List<Produto> produtoList, onClick onClick) {
        this.produtoList = produtoList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto,parent,false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = produtoList.get(position);

        holder.textProduto.setText(produto.getNome());
        holder.textestoque.setText("Estoque: " + produto.getEstoque());
        holder.textValor.setText("R$ " + produto.getValor());

        holder.itemView.setOnClickListener(v -> onClick.onClickListener(produto));


    }

    @Override
    public int getItemCount() {

        return produtoList.size();
    }

    public interface onClick{
        void onClickListener(Produto produto);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textProduto, textestoque, textValor;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            textProduto = itemView.findViewById(R.id.text_produto);
            textestoque = itemView.findViewById(R.id.text_estoque);
            textValor = itemView.findViewById(R.id.text_valor);
        }
    }
}
