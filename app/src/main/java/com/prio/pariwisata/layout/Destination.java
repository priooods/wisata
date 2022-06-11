package com.prio.pariwisata.layout;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.prio.pariwisata.adapter.Adapter_Carousel;
import com.prio.pariwisata.adapter.Adapter_Menu;
import com.prio.pariwisata.calling.Calling_Carousel;
import com.prio.pariwisata.calling.Calling_Menu;
import com.prio.pariwisata.databinding.LayoutDestinationBinding;
import com.prio.pariwisata.model.Model_User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Destination extends Fragment {

    LayoutDestinationBinding binding;
    private final Handler handler_carousel = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutDestinationBinding.inflate(inflater,container,false);

        binding.names.setText(Model_User.sg.username);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        _setup_carousel();
        _call_menu();
    }

    private void _call_menu(){
        Call<Calling_Menu> menuCall = Model_User.sg.getClient().get_menu(Model_User.sg.Bearer+Model_User.sg.token);
        menuCall.enqueue(new Callback<Calling_Menu>() {
            @Override
            public void onResponse(@NonNull Call<Calling_Menu> call, @NonNull Response<Calling_Menu> response) {
                Calling_Menu calling_menu = response.body();
                if(Calling_Menu.TreatResponse(getContext(),"Destination",response)){
                    assert calling_menu != null;
                    int columns = calling_menu.data.size() / 2;
                    Log.i(TAG, "onResponse: "+columns);
                    binding.listMenu.setLayoutManager(new GridLayoutManager(
                            getContext(),columns));
                    Adapter_Menu adapter_menu = new Adapter_Menu(getContext(),calling_menu.data);
                    binding.listMenu.setAdapter(adapter_menu);
                    binding.listMenu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Calling_Menu> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void _setup_carousel(){
        Call<Calling_Carousel> utilsCall = Model_User.sg.getClient().get_carousel(Model_User.sg.Bearer+Model_User.sg.token);
        utilsCall.enqueue(new Callback<Calling_Carousel>() {
            @Override
            public void onResponse(@NonNull Call<Calling_Carousel> call, @NonNull Response<Calling_Carousel> response) {
                Calling_Carousel calling = response.body();
                if (Calling_Carousel.TreatResponse(getContext(),"Carousel",response)){
                    assert calling != null;
                    binding.carousel.setAdapter(new Adapter_Carousel(binding.carousel
                            ,getContext()
                            ,calling.data));
                    binding.carousel.setClipToPadding(false);
                    binding.carousel.setClipChildren(false);
                    binding.carousel.setOffscreenPageLimit(3);
                    binding.carousel.getChildAt(0)
                            .setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                    CompositePageTransformer compositePageTransformer =
                            new CompositePageTransformer();
                    compositePageTransformer.addTransformer(new MarginPageTransformer(1));
                    compositePageTransformer.addTransformer((page, position) -> {
                        float r = 1 - Math.abs(position);
                        page.setScaleX(0.80f + r * 0.2f);
                    });
                    binding.carousel.setPageTransformer(compositePageTransformer);
                    binding.carousel.registerOnPageChangeCallback(
                            new ViewPager2.OnPageChangeCallback() {
                                @Override
                                public void onPageSelected(int position) {
                                    super.onPageSelected(position);
                                    handler_carousel.removeCallbacks(runnable);
                                    handler_carousel.postDelayed(runnable,3000);
                                }
                            });
                } else {
                    Toast.makeText(getContext(),"carousel null",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Calling_Carousel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            binding.carousel.setCurrentItem(binding.carousel.getCurrentItem() + 1);
        }
    };


}
