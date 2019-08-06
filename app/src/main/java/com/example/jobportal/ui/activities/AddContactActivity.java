package com.example.jobportal.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.jobportal.R;
import com.example.jobportal.models.insertContact.InsertUserResponse;
import com.example.jobportal.network.ApiClient;
import com.example.jobportal.network.ApiServices;
import com.example.jobportal.utils.BaseUtlis;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_button;
    Button save;
    EditText first_name;
    EditText last_name;
    Spinner gender;
    EditText dob;
    EditText mob_num;
    EditText email;
    ApiServices apiInterface;
    BaseUtlis baseUtlis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        init();
        back_button.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void init() {
        back_button = findViewById(R.id.iv_back);
        save = findViewById(R.id.btn_save);
        first_name = findViewById(R.id.et_first_name);
        last_name = findViewById(R.id.et_last_name);
        gender = findViewById(R.id.sp_gender);
        dob = findViewById(R.id.et_date);
        mob_num = findViewById(R.id.et_mobile);
        email = findViewById(R.id.et_email);
        apiInterface = ApiClient.contactsInfo().create(ApiServices.class);
        baseUtlis = new BaseUtlis();

    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.iv_back)) {
            onBackPressed();
        } else if (v == findViewById(R.id.btn_save)) {
            String uuid = UUID.randomUUID().toString();
            ProgressDialog mProgressDialog = baseUtlis.showLoading(this);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", uuid);
                jsonObject.put("first_name", first_name.getText().toString());
                jsonObject.put("last_name", last_name.getText().toString());
                jsonObject.put("email", email.getText().toString());
                jsonObject.put("phone_no", mob_num.getText().toString());
                jsonObject.put("date_of_birth", dob.getText().toString());
                jsonObject.put("gender", gender.getSelectedItem().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            apiInterface.insertContact(bodyRequest).enqueue(new Callback<InsertUserResponse>() {
                @Override
                public void onResponse(Call<InsertUserResponse> call, Response<InsertUserResponse> response) {
                    mProgressDialog.dismiss();
                    new AlertDialog.Builder(AddContactActivity.this)
                            .setTitle(getResources().getString(R.string.success))
                            .setMessage(getResources().getString(R.string.contact_added))
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

                @Override
                public void onFailure(Call<InsertUserResponse> call, Throwable t) {
                    mProgressDialog.dismiss();
                    baseUtlis.showErrorDialoge(AddContactActivity.this);
                }
            });
        }
    }


}
