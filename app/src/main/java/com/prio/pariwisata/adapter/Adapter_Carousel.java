package com.prio.pariwisata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.prio.pariwisata.R;
import com.prio.pariwisata.databinding.ModelCarouselBinding;
import com.prio.pariwisata.model.Model_Carousel;

import java.util.List;

public class Adapter_Carousel extends RecyclerView.Adapter<Adapter_Carousel.view_holder> {

    ViewPager2 viewPager2;
    Context context;
    List<Model_Carousel> model_carousels;

    public Adapter_Carousel(ViewPager2 viewPager2, Context context, List<Model_Carousel> model_carousels) {
        this.viewPager2 = viewPager2;
        this.context = context;
        this.model_carousels = model_carousels;
    }

    @NonNull
    @Override
    public view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new view_holder(ModelCarouselBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull view_holder holder, int position) {
        Glide.with(context).load(model_carousels.get(position).urls)
                .placeholder(R.drawable.ic_favorite)
                .into(holder.binding.imgCarousel);
        if (position == model_carousels.size() - 2){
            viewPager2.post(runnable_adapter);
        }
    }

    @Override
    public int getItemCount() {
        return model_carousels.size();
    }

    public static class view_holder extends RecyclerView.ViewHolder{
        ModelCarouselBinding binding;
        public view_holder(ModelCarouselBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    private final Runnable runnable_adapter = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            model_carousels.addAll(model_carousels);
            notifyDataSetChanged();
        }
    };
}
