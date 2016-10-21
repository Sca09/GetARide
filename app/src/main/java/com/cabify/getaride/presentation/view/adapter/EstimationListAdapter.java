package com.cabify.getaride.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabify.getaride.R;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.view.component.EstimationCard;
import com.cabify.getaride.presentation.view.component.EstimationCard.OnEstimationCardListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidtorralbo on 09/10/16.
 */

public class EstimationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<EstimationItem> estimationItemList;

    private OnEstimationCardListener listener;

    public EstimationListAdapter(Context context, List<EstimationItem> estimationItemList) {
        this.context = context;
        this.estimationItemList = estimationItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estimation_entry, parent, false);
        return new EstimationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EstimationItem item = estimationItemList.get(position);

        ((EstimationHolder) holder).estimationCard.setData(item);
        if(listener != null) {
            ((EstimationHolder) holder).estimationCard.setOnEstimationCardListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return (estimationItemList != null ? estimationItemList.size() : 0);
    }

    public void setOnEstimationCardListener(OnEstimationCardListener listener) {
        this.listener = listener;
    }

    class EstimationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.estimation_card) EstimationCard estimationCard;

        public EstimationHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
