package com.tanap.retrofit2rxandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tanap.retrofit2rxandroid.R;
import com.tanap.retrofit2rxandroid.StatusProfileAdapter;
import com.tanap.retrofit2rxandroid.model.StatusProfileDao;
import com.tanap.retrofit2rxandroid.network.profile.ProfileController;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;


public class MainActivity extends InternetActivity implements View.OnClickListener {

    ProgressBar progressBar;
    TextView tvStatusProfile;
    Button btnRequestStatusProfile;
    RecyclerView recyclerView;
    List<StatusProfileDao> statusProfileDaoList;
    StatusProfileAdapter statusProfileAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnRequestStatusProfile = (Button) findViewById(R.id.btn_request_status_and_profile);
        btnRequestStatusProfile.setOnClickListener(this);
        tvStatusProfile = (TextView) findViewById(R.id.tv_loading_status);
        recyclerView = (RecyclerView) findViewById(R.id.rv_container);
        statusProfileDaoList = new ArrayList<>();

        statusProfileAdapter = new StatusProfileAdapter(statusProfileDaoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(statusProfileAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

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
        subscription = ProfileController.getInstance().getStatusAndProfile()
                .subscribe(new Subscriber<StatusProfileDao>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                        btnRequestStatusProfile.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TRUST", "onError: ");
                        dismissLoading();
                        tvStatusProfile.setText("Error loading");
                        toastError(e);
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
        tvStatusProfile.setText("");
        statusProfileDaoList.add(statusProfileDao);
        statusProfileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view == btnRequestStatusProfile) {
            getStatusAndProfile();
        }
    }


}
