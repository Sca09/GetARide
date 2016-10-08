package com.cabify.getaride.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.utils.Constants;
import com.cabify.getaride.presentation.view.activity.EstimationListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by davidtorralbo on 08/10/16.
 */

@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToEstimationList(Context context, ArrayList<EstimationItem> estimationItemList) {
        if(context != null) {
            Intent intentToLaunch = EstimationListActivity.getCallingIntent(context);

            if(estimationItemList != null) {
                intentToLaunch.putExtra(Constants.INTENT_EXTRA_ESTIMATION_LIST, estimationItemList);
            }

            context.startActivity(intentToLaunch);
        }
    }

}
