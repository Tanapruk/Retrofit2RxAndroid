package com.tanap.retrofit2rxandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tanap.retrofit2rxandroid.model.StatusProfileDao;
import com.tanap.retrofit2rxandroid.network.profile.ProfileManager;

import rx.Subscriber;


public class MainActivity extends InternetActivity implements View.OnClickListener {

    ProgressBar progressBar;
    TextView tvStatusProfile;
    Button btnRequestStatusProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnRequestStatusProfile = (Button) findViewById(R.id.btn_request_status_and_profile);
        btnRequestStatusProfile.setOnClickListener(this);
        tvStatusProfile = (TextView) findViewById(R.id.tv_status);
        dismissLoading();
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void dismissLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    public void getStatusAndProfile() {
        tvStatusProfile.setText("Loading..");
        btnRequestStatusProfile.setEnabled(false);
        showLoading();
        subscription = ProfileManager.getInstance().getStatusAndProfile()
                .subscribe(new Subscriber<StatusProfileDao>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                        btnRequestStatusProfile.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        tvStatusProfile.setText("Error loading");
                        btnRequestStatusProfile.setEnabled(true);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(StatusProfileDao statusProfileDao) {
                        setStatusProfile(statusProfileDao);
                    }
                });

    }

    private void setStatusProfile(StatusProfileDao statusProfileDao) {
        tvStatusProfile.setText("It is a " + statusProfileDao.getProfileDao().getName() + " " + statusProfileDao.getProfileDao().getSurname());
    }

    @Override
    public void onClick(View view) {
        if (view == btnRequestStatusProfile) {
            getStatusAndProfile();
        }
    }

}
