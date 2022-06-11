package com.prio.pariwisata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prio.pariwisata.R;
import com.prio.pariwisata.databinding.ModelMenuBinding;
import com.prio.pariwisata.model.Model_Menu;

import java.util.List;

public class Adapter_Menu extends RecyclerView.Adapter<Adapter_Menu.view_holder> {

    Context context;
    List<Model_Menu> model_menus;

    public Adapter_Menu(Context context, List<Model_Menu> model_menus) {
        this.context = context;
        this.model_menus = model_menus;
    }

    @NonNull
    @Override
    public view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new view_holder(ModelMenuBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull view_holder holder, int position) {
        Glide.with(context).load(model_menus.get(position).urls)
                .placeholder(R.drawable.background_carousel)
                .into(holder.binding.icon);
        holder.binding.title.setText(model_menus.get(position).menu_name);
    }

    @Override
    public int getItemCount() {
        return model_menus.size();
    }

    public static class view_holder extends RecyclerView.ViewHolder{
        ModelMenuBinding binding;
        public view_holder(ModelMenuBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
