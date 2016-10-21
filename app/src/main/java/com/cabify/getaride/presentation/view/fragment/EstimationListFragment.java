package com.cabify.getaride.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cabify.getaride.R;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.utils.Constants;
import com.cabify.getaride.presentation.view.activity.EstimationListActivity;
import com.cabify.getaride.presentation.view.adapter.EstimationListAdapter;
import com.cabify.getaride.presentation.view.component.EstimationCard.OnEstimationCardListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EstimationListFragment extends BaseFragment implements OnEstimationCardListener {


    private LinearLayout rootView;

    List<EstimationItem> estimationItemList;

    @BindView(R.id.estimations_recycler_view) RecyclerView estimationsRecyclerView;
    private EstimationListAdapter adapter;

    private Unbinder unbinder;

    public EstimationListFragment() {
        // Required empty public constructor
    }

    public static EstimationListFragment newInstance(ArrayList<EstimationItem> estimationItemList) {
        EstimationListFragment fragment = new EstimationListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.INTENT_EXTRA_ESTIMATION_LIST, estimationItemList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estimationItemList = (List<EstimationItem>) getArguments().getSerializable(Constants.INTENT_EXTRA_ESTIMATION_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_estimation_list, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        adapter = new EstimationListAdapter(getActivity(), estimationItemList);
        adapter.setOnEstimationCardListener(this);
        estimationsRecyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        estimationsRecyclerView.setLayoutManager(layoutManager);

        adapter.notifyDataSetChanged();
        estimationsRecyclerView.scrollToPosition(0);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        setLayout();
    }

    private void setLayout() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onEstimationCardClicked(EstimationItem item) {
        ((EstimationListActivity) getActivity()).showSnackbar(getString(R.string.book_vehicle, item.getVehicleType().getName()));
    }
}
