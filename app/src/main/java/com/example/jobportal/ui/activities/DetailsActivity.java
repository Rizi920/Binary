package com.example.jobportal.ui.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobportal.R;
import com.example.jobportal.utils.BaseUtlis;
import com.example.jobportal.utils.DetailsParceableObject;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPrefs;
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "contactsObject";
    TextView first_name;
    TextView last_name;
    TextView email;
    TextView phone_no;
    TextView gender;
    TextView dob;

    ImageView love;
    DetailsParceableObject detailsParceableObject;
    ImageView image;
    BaseUtlis baseUtlis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        findViewById(R.id.iv_close).setOnClickListener(this);


    }

    public void init() {
        sharedPrefs = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phone_no = findViewById(R.id.tv_phone_no);
        first_name = findViewById(R.id.tv_first_name);
        last_name = findViewById(R.id.tv_last_name);
        email = findViewById(R.id.tv_email_address);
        gender = findViewById(R.id.tv_gender_value);
        dob = findViewById(R.id.tv_dob_value);
        love = findViewById(R.id.iv_love_details);
        detailsParceableObject = getIntent().getParcelableExtra(DETAILS_PARCEABLE_OBJECT);
        image = findViewById(R.id.iv_profile_image);
        baseUtlis=new BaseUtlis();

        populateUi();


    }

    public void populateUi() {
        first_name.setText(detailsParceableObject.getFirst_name());
        last_name.setText(detailsParceableObject.getLast_name());
        email.setText(detailsParceableObject.getEmail());
        phone_no.setText(detailsParceableObject.getPhone_no());
        gender.setText(detailsParceableObject.getGender());
        dob.setText(detailsParceableObject.getDob());
        baseUtlis.loadImageGlide(this,detailsParceableObject.getImage(),image);
        if (sharedPrefs.contains("id_" + detailsParceableObject.getId())) {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));
        } else {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
        }
    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.iv_close)) {
            onBackPressed();
        }

    }
}
