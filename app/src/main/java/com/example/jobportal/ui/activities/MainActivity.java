package com.example.jobportal.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.jobportal.R;
import com.example.jobportal.adapters.ContactsAdapter;
import com.example.jobportal.adapters.MyPagerAdapter;
import com.example.jobportal.listeners.ContactsAdapterCallBack;
import com.example.jobportal.models.Contacts.ContactsResponse;
import com.example.jobportal.models.Contacts.DataItem;
import com.example.jobportal.ui.fragments.AllContactsFragment;
import com.example.jobportal.ui.fragments.FavouriteContactsFragment;
import com.google.gson.Gson;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements FavouriteContactsFragment.OnFragmentInteractionListener, AllContactsFragment.OnFragmentInteractionListener, View.OnClickListener {

    TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;
    SearchView searchView;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    TextView no_search_results;
    TextView no_search_results_explain;
    ImageView no_search_result;
    ConstraintLayout hidden_layout;
    RecyclerView hiddenRecyclerview;
    ContactsAdapter contactsAdapter;
    ContactsResponse contactsResponse;
    FloatingActionButton addContacts;

    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "contactsObject";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addContacts.setOnClickListener(this);
        visibilityCOntroller();
        setupTabLayout();
        searchView.setOnCloseListener(() -> {
            ConstraintLayout hidden_layout = findViewById(R.id.hidden_constraint_layout);
            hidden_layout.setVisibility(View.INVISIBLE);
            LinearLayout linearLayout = findViewById(R.id.linera_layout_pager);
            linearLayout.setVisibility(View.VISIBLE);
            init();
            setupTabLayout();
            return true;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hidden_layout = findViewById(R.id.hidden_constraint_layout);
                hidden_layout.setVisibility(View.VISIBLE);
                LinearLayout linearLayout = findViewById(R.id.linera_layout_pager);
                linearLayout.setVisibility(View.INVISIBLE);

                Gson gson = new Gson();
                String json = sharedPrefs.getString(DETAILS_PARCEABLE_OBJECT, "");
                contactsResponse = gson.fromJson(json, ContactsResponse.class);
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> email = new ArrayList<>();
                ArrayList<String> contact_number = new ArrayList<>();
                ArrayList<String> image = new ArrayList<>();
                ArrayList<String> id = new ArrayList<>();


                for (int i = 0; i < contactsResponse.getData().size(); i++) {
                    DataItem obj = contactsResponse.getData().get(i);
                    if (obj.getFirstName() != null && obj.getLastName() != null) {
                        if (obj.getFirstName().toLowerCase().contains(query.toLowerCase()) || (obj.getLastName().toLowerCase().contains(query.toLowerCase()))) {
                            name.add(obj.getFirstName() + " " + obj.getLastName());
                            email.add(obj.getEmail());
                            contact_number.add(obj.getPhoneNo());
                            image.add(getResources().getString(R.string.profile_image_url));
                            id.add(obj.getId());
                        }
                    }
                }

                if (id.size() == 0) {
                    hiddenRecyclerview.setVisibility(View.INVISIBLE);
                    no_search_result.setVisibility(View.VISIBLE);
                    no_search_results.setVisibility(View.VISIBLE);
                    no_search_results_explain.setVisibility(View.VISIBLE);
                } else {
                    visibilityCOntroller();
                    RecyclerView recyclerView = findViewById(R.id.rv_hidden_search);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    contactsAdapter = new ContactsAdapter(name, email, contact_number, image, id, MainActivity.this, new ContactsAdapterCallBack() {
                        @Override
                        public void onLoveClickCallback(int position, ImageView love) {

                        }

                        @Override
                        public void onContactClickCallback(int position) {



                        }
                    });

                    recyclerView.setAdapter(contactsAdapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void init() {
        no_search_result = findViewById(R.id.iv_no_search_results);
        no_search_results = findViewById(R.id.tv_no_search_results);
        no_search_results_explain = findViewById(R.id.tv_no_search_results_explain);
        hiddenRecyclerview = findViewById(R.id.rv_hidden_search);
        sharedPrefs = MainActivity.this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = sharedPrefs.edit();
        addContacts = findViewById(R.id.btn_add_contacts);
        searchView = findViewById(R.id.sv_search_contacts);

    }

    public void visibilityCOntroller() {
        tabLayout = findViewById(R.id.tab_layout);
        hiddenRecyclerview.setVisibility(View.VISIBLE);
        no_search_result.setVisibility(View.INVISIBLE);
        no_search_results.setVisibility(View.INVISIBLE);
        no_search_results_explain.setVisibility(View.INVISIBLE);
    }

    private void setupTabLayout() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        ViewPager vpPager = findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(vpPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.btn_add_contacts)) {
            Intent myIntent = new Intent(this, AddContactActivity.class);
            this.startActivity(myIntent);
        }

    }
}
