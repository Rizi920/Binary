package com.example.jobportal.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobportal.R;
import com.example.jobportal.listeners.ContactsAdapterCallBack;
import com.example.jobportal.utils.BaseUtlis;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> contact_number = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    private ContactsAdapterCallBack contactsAdapterCallBack;
    SharedPreferences sharedPrefs;
    private static final String MY_PREFS_NAME = "Shared_pref";
    private BaseUtlis baseUtlis;

    private Context mContext;

    public ContactsAdapter(ArrayList<String> name, ArrayList<String> email, ArrayList<String> contact_num, ArrayList<String> image, ArrayList<String> id, Context mContext, ContactsAdapterCallBack contactsAdapterCallBack) {
        this.name = name;
        this.email = email;
        this.contact_number = contact_num;
        this.image = image;
        this.mContext = mContext;
        this.id = id;
        this.contactsAdapterCallBack = contactsAdapterCallBack;
        sharedPrefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_job, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        baseUtlis = new BaseUtlis();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(name.get(i));
        viewHolder.email.setText(email.get(i));
        viewHolder.contact_num.setText(contact_number.get(i));
        baseUtlis.loadImageGlide(mContext, image.get(i), viewHolder.image);
        if (sharedPrefs.contains("id_" + id.get(i))) {
            viewHolder.love.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_filled));
        } else {
            viewHolder.love.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like));
        }
        viewHolder.love.setOnClickListener(v -> contactsAdapterCallBack.onLoveClickCallback(i, viewHolder.love));
        viewHolder.jobClick.setOnClickListener(v -> contactsAdapterCallBack.onContactClickCallback(i));

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contact_num;
        TextView email;
        TextView name;
        ImageView image;
        ImageView love;
        ConstraintLayout jobClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_num = itemView.findViewById(R.id.tv_contact_num);
            email = itemView.findViewById(R.id.tv_email);
            name = itemView.findViewById(R.id.tv_first_name);
            image = itemView.findViewById(R.id.iv_main_banner);
            love = itemView.findViewById(R.id.iv_love);
            jobClick = itemView.findViewById(R.id.onClick_layout);

        }

    }
}
