package com.tanap.retrofit2rxandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.tanap.retrofit2rxandroid.R;
import com.tanap.retrofit2rxandroid.StatusProfileAdapter;
import com.tanap.retrofit2rxandroid.model.StatusProfileDao;
import com.tanap.retrofit2rxandroid.network.generic.SubscribingState;
import com.tanap.retrofit2rxandroid.network.profile.ProfileController;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends InternetActivity implements View.OnClickListener {

    ProgressBar progressBar;
    TextView tvStatusProfile;
    Button btnRequestStatusProfile;
    Button btnClear;
    RecyclerView recyclerView;
    List<StatusProfileDao> statusProfileDaoList;
    StatusProfileAdapter statusProfileAdapter;
    LinearLayoutManager linearLayoutManager;
    public static final String KEY_STATUS_PROFILE = "key_status_profile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnRequestStatusProfile = (Button) findViewById(R.id.btn_request_status_and_profile);
        btnRequestStatusProfile.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);
        tvStatusProfile = (TextView) findViewById(R.id.tv_loading_status);
        recyclerView = (RecyclerView) findViewById(R.id.rv_container);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        statusProfileDaoList = new ArrayList<>();

        if (savedInstanceState != null) {
            restoreInstance(savedInstanceState);
        }
        statusProfileAdapter = new StatusProfileAdapter(statusProfileDaoList);
        recyclerView.setAdapter(statusProfileAdapter);

        RxBus.get().register(this);
    }

    protected void restoreInstance(Bundle savedInstanceState) {
        statusProfileDaoList = Parcels.unwrap(savedInstanceState.getParcelable(KEY_STATUS_PROFILE));
        Log.d("TRUST", "restoreInstance" + SubscribingState.getInstance().getServiceList());
        setIsLoading(SubscribingState.getInstance().isAnyInProgress());
    }

    protected void setIsLoading(boolean isLoading) {
        if (isLoading) {
            showLoading();
        } else {
            dismissLoading();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_STATUS_PROFILE, Parcels.wrap(statusProfileDaoList));
    }


    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tvStatusProfile.setText("Loading..");
        btnRequestStatusProfile.setEnabled(false);
        btnClear.setEnabled(false);
        Log.d("TRUST", "showLoading: ");
    }

    private void dismissLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        tvStatusProfile.setText("");
        btnRequestStatusProfile.setEnabled(true);
        btnClear.setEnabled(true);
        Log.d("TRUST", "dismissLoading: ");
    }


    public void getStatusAndProfile() {
        showLoading();
        ProfileController.getInstance().getStatusAndProfile();
    }

    private void setStatusProfile(StatusProfileDao statusProfileDao) {
        Log.d("TRUST", "setStatusProfile:");
        tvStatusProfile.setText("");
        statusProfileDaoList.add(statusProfileDao);
        statusProfileAdapter.notifyItemInserted(statusProfileDaoList.size() - 1);
    }

    private void clearList() {
        int removeSize = statusProfileDaoList.size();
        statusProfileDaoList.clear();
        statusProfileAdapter.notifyItemRangeRemoved(0, removeSize);
    }

    @Override
    public void onClick(View view) {
        if (view == btnRequestStatusProfile) {
            getStatusAndProfile();
        } else if (view == btnClear) {
            clearList();
        }
    }

    @Subscribe
    public void onStatusAndProfileFinished(StatusProfileDao statusProfileDao) {
        dismissLoading();
        setStatusProfile(statusProfileDao);
    }

    @Subscribe
    public void onError(Throwable throwable) {
        dismissLoading();
        toastError(throwable);
    }
}
