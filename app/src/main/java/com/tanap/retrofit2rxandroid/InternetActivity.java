package com.tanap.retrofit2rxandroid;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import rx.Subscription;

/**
 * Created by trusttanapruk on 7/26/2016.
 */
abstract class InternetActivity extends AppCompatActivity {
    protected Subscription subscription;

    protected void toastError() {
        Toast.makeText(InternetActivity.this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscription != null) {
            subscription.unsubscribe();
            Log.d("TRUST", "onStop:Unsubscribed" + subscription.isUnsubscribed());
        }
    }
}
