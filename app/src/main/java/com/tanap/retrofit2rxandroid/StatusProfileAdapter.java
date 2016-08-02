package com.tanap.retrofit2rxandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tanap.retrofit2rxandroid.model.ProfileDao;
import com.tanap.retrofit2rxandroid.model.StatusDao;
import com.tanap.retrofit2rxandroid.model.StatusProfileDao;

import java.util.List;

/**
 * Created by trusttanapruk on 8/2/2016.
 */
public class StatusProfileAdapter extends RecyclerView.Adapter<StatusProfileAdapter.StatusProfileVH> {

    List<StatusProfileDao> statusProfileDaoList;

    public StatusProfileAdapter(List<StatusProfileDao> statusProfileDaoList) {
        this.statusProfileDaoList = statusProfileDaoList;
    }

    @Override
    public StatusProfileVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_status_profile, parent, false);
        return new StatusProfileVH(view);
    }

    @Override
    public void onBindViewHolder(StatusProfileVH holder, int position) {
        StatusProfileDao statusProfileDao = statusProfileDaoList.get(position);
        StatusDao statusDao = statusProfileDao.getStatusDao();
        ProfileDao profileDao = statusProfileDao.getProfileDao();
        holder.tvStatusId.setText(statusDao.getResultCode());
        holder.tvStatusMessage.setText(statusDao.getResultDescription());
        holder.tvStatusDev.setText(statusDao.getDeveloperMessage());

        holder.tvProfileId.setText(profileDao.getResultCode());
        holder.tvProfileMessage.setText(profileDao.getName() + " " + profileDao.getSurname());
        holder.tvProfileDev.setText(profileDao.getDeveloperMessage());
    }


    @Override
    public int getItemCount() {
        return statusProfileDaoList.size();
    }

    public class StatusProfileVH extends RecyclerView.ViewHolder {

        TextView tvStatusId;
        TextView tvStatusMessage;
        TextView tvStatusDev;

        TextView tvProfileId;
        TextView tvProfileMessage;
        TextView tvProfileDev;

        public StatusProfileVH(View itemView) {
            super(itemView);
            tvStatusId = (TextView) itemView.findViewById(R.id.tv_status_id);
            tvStatusMessage = (TextView) itemView.findViewById(R.id.tv_status_message);
            tvStatusDev = (TextView) itemView.findViewById(R.id.tv_status_developer);

            tvProfileId = (TextView) itemView.findViewById(R.id.tv_profile_id);
            tvProfileMessage = (TextView) itemView.findViewById(R.id.tv_profile_message);
            tvProfileDev = (TextView) itemView.findViewById(R.id.tv_profile_developer);
        }
    }
}
