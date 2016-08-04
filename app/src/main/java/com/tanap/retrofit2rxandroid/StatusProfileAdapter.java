package com.tanap.retrofit2rxandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    int lastPosition = -1;

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

        holder.tvStatusId.setText(statusDao == null ? "" : statusDao.getResultCode());
        holder.tvStatusMessage.setText(statusDao == null ? "" : statusDao.getResultDescription());
        holder.tvStatusDev.setText(statusDao == null ? "" : statusDao.getDeveloperMessage());

        holder.tvProfileId.setText(profileDao == null ? "" : profileDao.getResultCode());
        holder.tvProfileMessage.setText(profileDao == null ? "" : profileDao.getName() + " " + profileDao.getSurname());
        holder.tvProfileDev.setText(profileDao == null ? "" : profileDao.getDeveloperMessage());

        setAnimation(holder.cvContainer, position);

    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.push_up_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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

        View cvContainer;

        public StatusProfileVH(View itemView) {
            super(itemView);

            cvContainer = itemView.findViewById(R.id.layout_cv_container);
            tvStatusId = (TextView) itemView.findViewById(R.id.tv_status_id);
            tvStatusMessage = (TextView) itemView.findViewById(R.id.tv_status_message);
            tvStatusDev = (TextView) itemView.findViewById(R.id.tv_status_developer);

            tvProfileId = (TextView) itemView.findViewById(R.id.tv_profile_id);
            tvProfileMessage = (TextView) itemView.findViewById(R.id.tv_profile_message);
            tvProfileDev = (TextView) itemView.findViewById(R.id.tv_profile_developer);
        }
    }
}
