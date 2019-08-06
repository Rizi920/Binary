package com.example.jobportal.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobportal.R;
import com.example.jobportal.adapters.ContactsAdapter;
import com.example.jobportal.listeners.ContactsAdapterCallBack;
import com.example.jobportal.models.Contacts.ContactsResponse;
import com.example.jobportal.models.Contacts.DataItem;
import com.example.jobportal.ui.activities.DetailsActivity;
import com.example.jobportal.utils.DetailsParceableObject;
import com.google.gson.Gson;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteContactsFragment extends Fragment implements ContactsAdapterCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "contactsObject";

    ContactsAdapter contactsAdapter;

    ContactsResponse contactsResponse;
    View mView;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    ArrayList<String> id;


    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public FavouriteContactsFragment() {
        // Required empty public constructor
    }

    public static FavouriteContactsFragment newInstance(String param1, String param2) {
        FavouriteContactsFragment fragment = new FavouriteContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favourite_contacts, container, false);

        return mView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            sharedPrefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json = sharedPrefs.getString(DETAILS_PARCEABLE_OBJECT, "");
            contactsResponse = gson.fromJson(json, ContactsResponse.class);
            ImageView no_saved = mView.findViewById(R.id.iv_no_Save);
            TextView no_save = mView.findViewById(R.id.tv_no_save_jobs);
            TextView no_save_explain = mView.findViewById(R.id.tv_no_save_explain);
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> email = new ArrayList<>();
            ArrayList<String> contact_number = new ArrayList<>();
            ArrayList<String> image = new ArrayList<>();
            id = new ArrayList<>();


            for (int i = 0; i < contactsResponse.getData().size(); i++) {
                if (sharedPrefs.contains("id_" + contactsResponse.getData().get(i).getId())) {
                    DataItem obj=contactsResponse.getData().get(i);
                    name.add(obj.getFirstName()+" "+obj.getLastName());
                    email.add(obj.getEmail());
                    contact_number.add(obj.getPhoneNo());
                    image.add(getResources().getString(R.string.profile_image_url));
                    id.add(obj.getId());
                }
            }

            if (id.size() != 0) {
                no_saved.setVisibility(View.INVISIBLE);
                no_save.setVisibility(View.INVISIBLE);
                no_save_explain.setVisibility(View.INVISIBLE);
            } else {
                no_saved.setVisibility(View.VISIBLE);
                no_save.setVisibility(View.VISIBLE);
                no_save_explain.setVisibility(View.VISIBLE);
            }


            RecyclerView recyclerView = mView.findViewById(R.id.rv_saved);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contactsAdapter = new ContactsAdapter(name,email,contact_number, image, id, getContext(), this);
            recyclerView.setAdapter(contactsAdapter);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoveClickCallback(int position, ImageView love) {

    }

    @Override
    public void onContactClickCallback(int position) {
        DataItem obj = contactsResponse.getData().get(contactsResponse.getData().size()-position-1);
        String first_name=obj.getFirstName();
        String last_name=obj.getLastName();
        String dob=obj.getDateOfBirth();
        String email=obj.getEmail();
        String phone_no=obj.getPhoneNo();
        String image=getResources().getString(R.string.profile_image_url);
        String gender=obj.getGender();
        String id = obj.getId();
        DetailsParceableObject detailsParceableObject = new DetailsParceableObject(first_name,last_name,email,phone_no,dob,gender,id,image);
        Intent myIntent = new Intent(getContext(), DetailsActivity.class);
        myIntent.putExtra(DETAILS_PARCEABLE_OBJECT, detailsParceableObject);
        getActivity().startActivity(myIntent);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
