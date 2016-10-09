package com.cabify.getaride.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabify.getaride.R;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.view.component.EstimationCard;

import java.util.List;

/**
 * Created by davidtorralbo on 09/10/16.
 */

public class EstimationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<EstimationItem> estimationItemList;

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

        ((EstimationHolder) holder).estimationCard.setData(item.getVehicleType().getIcons().getRegular(), item.getVehicleType().getName(), item.getVehicleType().getDescription(), item.getFormattedPrice(), item.getVehicleType().getIcon());
    }

    @Override
    public int getItemCount() {
        return (estimationItemList != null ? estimationItemList.size() : 0);
    }

    class EstimationHolder extends RecyclerView.ViewHolder {

        public EstimationHolder(View itemView) {
            super(itemView);

            estimationCard = (EstimationCard) itemView.findViewById(R.id.estimation_card);
        }

        EstimationCard estimationCard;
    }
}
