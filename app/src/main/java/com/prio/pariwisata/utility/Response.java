package com.prio.pariwisata.utility;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.prio.pariwisata.authentication.Login;

public class Response {

    @SerializedName("error_message")
    public String error_message;
    @SerializedName("error_code")
    public int error_code;
    @SerializedName("total_page")
    public int total_page;
    @SerializedName("total_data")
    public String total_data;

    public static boolean TreatResponse(Context context, String tag,@Nullable retrofit2.Response response) {
        assert response != null;
        if (response.code() == 200)
            return true;
        if (response.code() == 401 || response.code() == 400){
            Toast.makeText(context, "Authorization Token invalid !", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context, Login.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
            return true;
        }
        if (response.code() == 500){
            Toast.makeText(context, "Something problem from server !", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context,Login.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
            return true;
        }
        Log.e("res error body", (response.errorBody() != null ?
                response.errorBody().toString() : String.valueOf(response.code())));
        Log.i(tag, "onResponseError : " + response.code()+" Message :"+response.message());
        return false;
    }
}
