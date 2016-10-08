package com.cabify.getaride.presentation.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.cabify.getaride.R;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.cabify.getaride.presentation.presenter.MapPresenter;
import com.cabify.getaride.presentation.presenter.MapPresenterImpl;
import com.cabify.getaride.presentation.utils.Constants;
import com.cabify.getaride.presentation.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class MainActivity extends BaseActivity implements MapView, OnMapReadyCallback, OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private FloatingActionButton fabCurrentLocation;
    private FloatingActionButton fabGetEstimation;
    private CardView newEstimationStopsLayout;
    private AppCompatEditText fromEditText;
    private ImageView removeFromCurrentLocation;
    private LinearLayout toEditTextLayout;
    private AppCompatEditText toEditText;
    private ImageView removeToCurrentLocation;
    private AppCompatEditText startAtEditText;
    private ImageView removeStartAt;

    private Animation fab_open;
    private Animation fab_close;
    private Animation rotate_forward;
    private Animation rotate_backward;

    private GoogleMap mMap;
    private MapPresenter presenter;

    private Marker fromMarker;
    private LatLng fromLatLng;
    private Address fromAddress;
    private Marker toMarker;
    private LatLng toLatLng;
    private Address toAddress;
    private Calendar startAtCalendar;
    private boolean startAtDatSet = false;

    private  boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().hide();
    }

    @Override
    protected void onStart() {
        super.onStart();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        fabCurrentLocation = (FloatingActionButton) findViewById(R.id.current_location_button);
        fabGetEstimation = (FloatingActionButton) findViewById(R.id.get_estimation_button);
        newEstimationStopsLayout = (CardView) findViewById(R.id.new_estimation_stops_layout);
        fromEditText = (AppCompatEditText) findViewById(R.id.from_edit_text);
        removeFromCurrentLocation = (ImageView) findViewById(R.id.remove_from_current_location);
        toEditTextLayout = (LinearLayout) findViewById(R.id.to_edit_text_layout);
        toEditText = (AppCompatEditText) findViewById(R.id.to_edit_text);
        removeToCurrentLocation = (ImageView) findViewById(R.id.remove_to_current_location);
        startAtEditText = (AppCompatEditText) findViewById(R.id.start_at_edit_text);
        removeStartAt = (ImageView) findViewById(R.id.remove_start_at);

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        presenter = new MapPresenterImpl(this);

        setLayout();
    }

    private void setLayout() {
        startAtEditText.setText(getString(R.string.start_asap));
        fabCurrentLocation.setOnClickListener(this);
        fabGetEstimation.setOnClickListener(this);
        removeFromCurrentLocation.setOnClickListener(this);
        removeToCurrentLocation.setOnClickListener(this);
        removeStartAt.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(presenter != null) {
            presenter.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(presenter != null) {
            presenter.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(presenter != null) {
            presenter.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        if(newEstimationStopsLayout.getVisibility() == View.VISIBLE) {
            closeEstimationLayout();
            if(isFabOpen){
                closeFab();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng madridCoordinates = new LatLng(40.4168, -3.7038);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madridCoordinates, 13));

        focusOnCurrentPosition();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                setPoint(point);
            }
        });
    }

    private void focusOnCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkNeededPermissions();
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                focusOnPoint(location.getLatitude(), location.getLongitude());
            }
        }
    }

    private void setCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkNeededPermissions();
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                setPoint(point);
            }
        }
    }

    private void checkNeededPermissions() {
        final List<String> permissionsNeeded = new ArrayList<String>();

        if(!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if(permissionsNeeded.size() > 0) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showMessageOKCancel(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case -1: // OK
                                ActivityCompat.requestPermissions(MainActivity.this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                break;

                            case -2: // Cancel
                                showSnackbar(getString(R.string.permissions_not_granted), coordinatorLayout);
                                break;
                        }
                    }
                });
            } else {
                ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            if (permissions.length > 0 && grantResults.length > 0) {
                boolean allGranted = true;

                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        allGranted = false;
                        break;
                    }
                }
                // In case we want to manage the user in different ways
                if (allGranted) {
                    focusOnCurrentPosition();
                } else {
                    showSnackbar(getString(R.string.permissions_not_granted), coordinatorLayout);
                }
            }
        }
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.DialogStyle)
                .setTitle(getString(R.string.location_permissions_title))
                .setMessage(getString(R.string.location_permissions_message))
                .setPositiveButton(getString(R.string.grant).toUpperCase(), okListener)
                .setNegativeButton(getString(R.string.cancel).toUpperCase(), okListener)
                .setCancelable(false)
                .create();

        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));
    }

    // MapView implementation

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEstimateResults(List<EstimationItem> estimationItemList) {
        //closeEstimationLayout();
        showSnackbar("Found "+ estimationItemList.size() +" possibilities", coordinatorLayout);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        showSnackbar(errorMessage, coordinatorLayout);
    }

    @Override
    public void showDefaultErrorMessage() {
        showSnackbar(getString(R.string.default_error_message), coordinatorLayout);
    }

    @Override
    public ApplicationComponent getApplicationComponentFromApplication() {
        return getApplicationComponent();
    }

    @Override
    public void onClick(View v) {
        if(v == fabCurrentLocation) {
            if(isFabOpen){
                closeFab();
                closeEstimationLayout();
            } else {
                openFab();
                focusOnCurrentPosition();
                setCurrentLocation();
            }
        } else if(v == fabGetEstimation) {
            presenter.getEstimation(fromLatLng, fromAddress, toLatLng, toAddress, (startAtDatSet) ? startAtCalendar : null);
        } else if(v == removeFromCurrentLocation) {
            closeEstimationLayout();
            if(isFabOpen){
                closeFab();
            }
        } else if(v == removeToCurrentLocation) {
            closeToLayout();
        } else if(v == removeStartAt) {
            if(startAtDatSet) {
                resetStartAtDate();
            } else {
                initDatePicker();
            }
        }
    }

    private void setPoint(LatLng point) {
        Address address = null;
        try {
            Geocoder geo = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(point.latitude, point.longitude, 1);
            if (addresses.isEmpty()) {
                Log.d("GetARide", "Waiting for Location");
            }
            else if (addresses.size() > 0) {
                address = addresses.get(0);
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

        if(fromLatLng == null) {
            fromLatLng = point;
            fromMarker = mMap.addMarker(new MarkerOptions().position(point).title(getString(R.string.from)));
            if(address != null) {
                fromAddress = address;
                fromEditText.setText(getString(R.string.address_in_box, address.getAddressLine(0), address.getAddressLine(1)));
            } else {
                fromEditText.setText(point.toString());
            }

            openFromLayout();
            focusOnPoint(point.latitude, point.longitude);
        } else if(toLatLng == null){
            toLatLng = point;
            toMarker = mMap.addMarker(new MarkerOptions().position(point).title(getString(R.string.to)));
            if(address != null) {
                toAddress = address;
                toEditText.setText(getString(R.string.address_in_box, address.getAddressLine(0), address.getAddressLine(1)));
            } else {
                toEditText.setText(point.toString());
            }

            openToLayout();

            if(!isFabOpen) {
                openFab();
            }
        }
    }

    private void focusOnPoint(double latitude, double longitude){
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));

        LatLng currentCoordinates = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentCoordinates) // Sets the center of the map to location user
                .zoom(16)                   // Sets the zoom
                .build();                   // Creates a CameraPosition fromLatLng the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void openFab() {
        fabCurrentLocation.setImageResource(R.drawable.ic_clear_black_24dp);
        fabCurrentLocation.startAnimation(rotate_forward);
        isFabOpen = true;
    }

    private void closeFab() {
        fabCurrentLocation.setImageResource(R.drawable.ic_my_location_black_24dp);
        fabCurrentLocation.startAnimation(rotate_backward);
        isFabOpen = false;
    }

    private void openFromLayout() {
        newEstimationStopsLayout.startAnimation(fab_open);
        newEstimationStopsLayout.setVisibility(View.VISIBLE);
    }

    private void closeFromLayout() {
        newEstimationStopsLayout.startAnimation(fab_close);
        newEstimationStopsLayout.setVisibility(View.GONE);

        fromEditText.setText("");
        if(fromMarker != null) {
            fromMarker.remove();
        }
        fromLatLng = null;
    }

    private void openToLayout() {
        toEditTextLayout.setVisibility(View.VISIBLE);
        //fabGetEstimation.startAnimation(fab_open);
        fabGetEstimation.setVisibility(View.VISIBLE);
    }

    private void closeToLayout() {
        toEditTextLayout.setVisibility(View.GONE);
        //fabGetEstimation.startAnimation(fab_close);
        fabGetEstimation.setVisibility(View.GONE);

        toEditText.setText("");
        if(toMarker != null) {
            toMarker.remove();
        }
        toLatLng = null;
    }

    private void closeEstimationLayout() {
        closeFromLayout();
        closeToLayout();
        resetStartAtDate();

        mMap.clear();
    }

    public void resetStartAtDate() {
        startAtDatSet = false;
        startAtEditText.setText(getString(R.string.start_asap));
    }

    private void initDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void initTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);
        tpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("GetARide", "year["+ year +"] - monthOfYear["+ monthOfYear +"] - dayOfMonth["+ dayOfMonth +"]");
        startAtCalendar = Calendar.getInstance();
        startAtCalendar.set(Calendar.YEAR, year);
        startAtCalendar.set(Calendar.MONTH, monthOfYear);
        startAtCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        initTimePicker();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Log.d("GetARide", "hourOfDay["+ hourOfDay +"] - minute["+ minute +"] - second["+ second +"]");
        startAtCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        startAtCalendar.set(Calendar.MINUTE, minute);

        startAtDatSet = true;
        startAtEditText.setText(Utils.getDateStringFromDate(startAtCalendar));
    }
}
