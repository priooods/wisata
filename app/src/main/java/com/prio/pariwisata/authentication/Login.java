package com.prio.pariwisata.authentication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prio.pariwisata.calling.Calling_User;
import com.prio.pariwisata.model.Model_User;
import com.prio.pariwisata.service.Client;
import com.prio.pariwisata.utility.Utils;
import com.prio.pariwisata.databinding.LayoutLoginBinding;
import com.prio.pariwisata.parent.ActivityParent;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    LayoutLoginBinding binding;
    Model_User model_user;
    Client client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        Utils._changeStatusBarColor(this,"#ffffff");

        binding.goRegister.setOnClickListener(v-> startActivity(new Intent(this,Register.class)));
        binding.btnLogin.setOnClickListener(v-> {

            if (Objects.requireNonNull(binding.username.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(binding.password.getText()).toString().isEmpty()
            )
                Toast.makeText(this,"Harap lengkapi informasi anda !",Toast.LENGTH_SHORT).show();
            else {
                Model_User.sg = model_user = new Model_User(
                        binding.username.getText().toString(),
                        binding.password.getText().toString());
                client = model_user.getClient();
                if (client == null) return;

                RequestLogin(
                        Objects.requireNonNull(binding.username.getText()).toString(),
                        Objects.requireNonNull(binding.password.getText()).toString());
            }

        });

        setContentView(view);
    }

    private void RequestLogin(String username,String password){
        Call<Calling_User> calling_user = client.UserLogin(username,password);
        calling_user.enqueue(new Callback<Calling_User>() {
            @Override
            public void onResponse(@NonNull Call<Calling_User> call, @NonNull Response<Calling_User> response) {
                Calling_User callingUser = response.body();
                if (Calling_User.TreatResponse(Login.this,"register",response)){
                    assert callingUser != null;
                    Model_User.sg.token = callingUser.data.token;
                    startActivity(new Intent(Login.this, ActivityParent.class));
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Calling_User> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

}
