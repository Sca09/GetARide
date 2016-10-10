package com.cabify.getaride.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cabify.getaride.R;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.utils.Constants;
import com.cabify.getaride.presentation.view.fragment.EstimationListFragment;

import java.util.ArrayList;
import java.util.List;

public class EstimationListActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, EstimationListActivity.class);
        return callingIntent;
    }

    private CoordinatorLayout coordinatorLayout;
    private List<EstimationItem> estimationItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimation_list);

        overrideOpenTransition();
    }

    @Override
    protected void onStart() {
        super.onStart();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showToolBarActionButton();

        estimationItemList = (List<EstimationItem>) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_ESTIMATION_LIST);

        EstimationListFragment estimationListFragment = EstimationListFragment.newInstance((ArrayList<EstimationItem>) estimationItemList);
        addFragment(R.id.fragmentContainer, estimationListFragment, true, false);
    }

    private void showToolBarActionButton() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overrideCloseTransition();
    }

    private void overrideOpenTransition() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    private void overrideCloseTransition() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    public void showSnackbar(String message) {
        showSnackbar(message, coordinatorLayout);
    }
}
