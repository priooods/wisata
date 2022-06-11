package com.prio.pariwisata.parent;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prio.pariwisata.R;
import com.prio.pariwisata.calling.Calling_User;
import com.prio.pariwisata.model.Model_User;
import com.prio.pariwisata.utility.Utils;
import com.prio.pariwisata.databinding.LayoutDashboardBinding;
import com.prio.pariwisata.layout.Destination;
import com.prio.pariwisata.layout.Favorite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityParent extends AppCompatActivity {

    LayoutDashboardBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        Utils._changeStatusBarColor(this,"#ffffff");

        _setNavigation(binding.bottomNavigation);
        _setShowLayout(new Destination());

        setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        UsersMe();
    }

    @SuppressLint("NonConstantResourceId")
    private void _setNavigation(BottomNavigationView navigationView){
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_destination:
                    _setShowLayout(new Destination());
                    break;
                case R.id.menu_favorite:
                    _setShowLayout(new Favorite());
                    break;
            }
            return true;
        });
    }

    private void _setShowLayout(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framing, fragment)
                .commit();
    }

    private void UsersMe(){
        Call<Calling_User> userCall = Model_User.sg.getClient().UserMe(Model_User.sg.Bearer+Model_User.sg.token);
        userCall.enqueue(new Callback<Calling_User>() {
            @Override
            public void onResponse(@NonNull Call<Calling_User> call, @NonNull Response<Calling_User> response) {
                Calling_User callingUser = response.body();
                if (Calling_User.TreatResponse(ActivityParent.this,"Parent",response)){
                    assert callingUser != null;
                    Model_User.sg.username = callingUser.data.username;
                    Model_User.sg.email = callingUser.data.email;
                    Model_User.sg.id = callingUser.data.id;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Calling_User> call, @NonNull Throwable t) {

            }
        });
    }
}
