package com.example.jobportal.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jobportal.R;

import okhttp3.RequestBody;

public class BaseUtlis {

    public boolean checkWifiConnection(Context mContext) {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        } else {
            return false;
        }

    }

    public void loadImageGlide(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public ProgressDialog showLoading(Context context) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.show();
        return mProgressDialog;
    }

    public void showErrorDialoge(Context context){
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.error))
                .setMessage(context.getResources().getString(R.string.api_error))
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
