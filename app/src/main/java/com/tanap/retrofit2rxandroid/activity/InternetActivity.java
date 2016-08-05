package com.tanap.retrofit2rxandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by trusttanapruk on 7/26/2016.
 */
abstract class InternetActivity extends AppCompatActivity {
    protected void toastError(Throwable throwable) {
        Log.e("TRUST", throwable.getClass().getSimpleName() + " with a message of " + throwable.getMessage());
        if (throwable instanceof NullPointerException) {
            Toast.makeText(InternetActivity.this, "Null Pointer", Toast.LENGTH_LONG).show();
        } else if (throwable instanceof JSONException) {
            Toast.makeText(InternetActivity.this, "JSONException", Toast.LENGTH_LONG).show();
        } else if (throwable instanceof ConnectException) {
            //no internet connection
            Toast.makeText(InternetActivity.this, "ConnectException", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof SocketTimeoutException) {
            //network timeout exception
            Toast.makeText(InternetActivity.this, "SocketTimeoutException", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof IOException) {
            Toast.makeText(InternetActivity.this, "IOException", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(InternetActivity.this, "Other", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}
