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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;


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
    public static final String KEY_STATUS_PROFILE_MANAGER_STATE = "key_status_profile_state";


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
            Log.d("TRUST", "onCreate: " + savedInstanceState.size());
            Log.d("TRUST", "onCreate: " + savedInstanceState);
            restoreInstance(savedInstanceState);
        }
        statusProfileAdapter = new StatusProfileAdapter(statusProfileDaoList);
        recyclerView.setAdapter(statusProfileAdapter);


    }

    protected void restoreInstance(Bundle savedInstanceState) {
        statusProfileDaoList = Parcels.unwrap(savedInstanceState.getParcelable(KEY_STATUS_PROFILE));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("TRUST", "onSaveInstanceState: " + outState);
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_STATUS_PROFILE, Parcels.wrap(statusProfileDaoList));

    }


    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("TRUST", "showLoading: ");
    }

    private void dismissLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        Log.d("TRUST", "dismissLoading: ");
    }

    private Subscriber<StatusProfileDao> statusProfileDaoSubscriber = new Subscriber<StatusProfileDao>() {
        @Override
        public void onCompleted() {
            Log.d("TRUST", "onCompleted: ");
            dismissLoading();
            tvStatusProfile.setText("");
            btnRequestStatusProfile.setEnabled(true);
            btnClear.setEnabled(true);
        }

        @Override
        public void onError(Throwable e) {
            Log.d("TRUST", "onError: ");
            dismissLoading();
            tvStatusProfile.setText("Error loading");
            btnClear.setEnabled(true);
            toastError(e);
            btnRequestStatusProfile.setEnabled(true);
            e.printStackTrace();
        }

        @Override
        public void onNext(StatusProfileDao statusProfileDao) {
            Log.d("TRUST", "onNext: ");
            setStatusProfile(statusProfileDao);
        }
    };

    public void getStatusAndProfile() {
        tvStatusProfile.setText("Loading..");
        btnRequestStatusProfile.setEnabled(false);
        btnClear.setEnabled(false);
        showLoading();
        subscription = ProfileController.getInstance().getStatusAndProfile()
                .subscribe(statusProfileDaoSubscriber);
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


}
