package com.mmabnets.mvvmrecyclerview.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mmabnets.mvvmrecyclerview.Models.Mydata;
import com.mmabnets.mvvmrecyclerview.R;
import com.mmabnets.mvvmrecyclerview.utils.NetworkState;

public class Videoadapter extends PagedListAdapter<Mydata,RecyclerView.ViewHolder> {
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;


    public Videoadapter(Context context) {
        super(Diff_callback);
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_PROGRESS) {
            return new NetworkStateItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_network_state, parent, false));
        }else{
            return new VideoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dfiles, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof VideoHolder) {
            Mydata mydata=getItem(position);
            if(mydata!=null){
                VideoHolder Holder = (VideoHolder) holder;
                Holder.filz.setText(mydata.photo);
                Holder.dnld.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }else{
                Toast.makeText(context, "data is null", Toast.LENGTH_SHORT).show();
            }
        }else{
           NetworkStateItemViewHolder Holder = (NetworkStateItemViewHolder) holder;

            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                Holder.pgbar.setVisibility(View.VISIBLE);
            } else {
                Holder.pgbar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                Holder.statez.setVisibility(View.VISIBLE);
                Holder.statez.setText(networkState.getMsg());
            } else {
                Holder.statez.setVisibility(View.GONE);
            }

        }


    }
    private  static DiffUtil.ItemCallback<Mydata> Diff_callback=new DiffUtil.ItemCallback<Mydata>() {
        @Override
        public boolean areItemsTheSame(@NonNull Mydata oldItem, @NonNull Mydata newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Mydata oldItem, @NonNull Mydata newItem) {
            return oldItem.equals(newItem);
        }
    };


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }



    public static class VideoHolder extends RecyclerView.ViewHolder {
        private TextView filz;
        private TextView dnld;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            filz = (TextView) itemView.findViewById(R.id.fnm);
            dnld = (TextView) itemView.findViewById(R.id.fdnld);
        }
    }

    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
        private TextView statez;
        private ProgressBar pgbar;
        public NetworkStateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            statez=(TextView)itemView.findViewById(R.id.error_msg);
            pgbar=(ProgressBar)itemView.findViewById(R.id.progress_bar);

        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}