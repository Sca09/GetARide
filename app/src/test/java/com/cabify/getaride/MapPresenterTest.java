package com.cabify.getaride;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;

import com.cabify.getaride.presentation.AndroidApplication;
import com.cabify.getaride.presentation.internal.di.components.ActivityComponent;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.cabify.getaride.presentation.internal.di.components.DaggerActivityComponent;
import com.cabify.getaride.presentation.internal.di.components.DaggerApplicationComponent;
import com.cabify.getaride.presentation.internal.di.modules.ActivityModule;
import com.cabify.getaride.presentation.internal.di.modules.ApplicationModule;
import com.cabify.getaride.presentation.presenter.MapPresenter;
import com.cabify.getaride.presentation.presenter.MapPresenterImpl;
import com.cabify.getaride.presentation.view.MapView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by davidtorralbo on 10/10/16.
 */

public class MapPresenterTest {

    @Mock
    Context mMockContext;

    @Mock
    MapView mapView = Mockito.mock(MapView.class);

    ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(new AndroidApplication())).build();
    ActivityComponent activityComponent = DaggerActivityComponent.builder().applicationComponent(applicationComponent).activityModule(new ActivityModule(new AccountAuthenticatorActivity())).build();

    MapPresenter presenter;

    @Before
    public void setUp() {
        when(mapView.getApplicationComponentFromApplication()).thenReturn(applicationComponent);
        when(mapView.getActivityComponentFromBaseActivity()).thenReturn(activityComponent);
        presenter = new MapPresenterImpl(mapView);
    }

    @Test
    public void fabCurrentLocationIsClosed_clicked_fabIsOpen() throws Exception {
        presenter.buttonClickedFabCurrentLocation();
        verify(mapView, times(1)).openFab();
    }

    @Test
    public void fabCurrentLocationIsClosed_clicked_focusOnCurrentPosition() throws Exception {
        presenter.buttonClickedFabCurrentLocation();
        verify(mapView, times(1)).focusMapOnCurrentPosition();
    }

    @Test
    public void fabCurrentLocationIsClosed_clicked_setCurrentLocationOnMap() throws Exception {
        presenter.buttonClickedFabCurrentLocation();
        verify(mapView, times(1)).setCurrentLocation();
    }

    @Test
    public void fabCurrentLocationIsOpened_clicked_fabIsClosed() throws Exception {
        presenter.setFabOpenStatus(true);
        presenter.buttonClickedFabCurrentLocation();
        verify(mapView, times(1)).closeFab();
    }

    @Test
    public void fabCurrentLocationIsOpened_clicked_resetViews() throws Exception {
        presenter.setFabOpenStatus(true);
        presenter.buttonClickedFabCurrentLocation();
        verify(mapView, times(1)).resetViews();
    }

    /**
     * And so on with the rest of the logic of our presenter/s
     */
}