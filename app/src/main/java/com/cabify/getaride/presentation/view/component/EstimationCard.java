package com.cabify.getaride.presentation.view.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cabify.getaride.R;
import com.cabify.getaride.presentation.utils.Constants;
import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

public class EstimationCard extends LinearLayout {

    private Context context;

    ImageView mainImage;
    ImageView icoImage;
    TextView nameText;
    TextView descriptionText;
    TextView priceFormattedText;

    String ico;
    String name;
    String description;
    String priceFormatted;
    int type;

    String[] vehicleTypes = {Constants.VEHICULE_TYPE_LITE,
            Constants.VEHICULE_TYPE_EXECUTIVE,
            Constants.VEHICULE_TYPE_PET,
            Constants.VEHICULE_TYPE_DELIVERY,
            Constants.VEHICULE_TYPE_BABY,
            Constants.VEHICULE_TYPE_RICKSHAW,
            Constants.VEHICULE_TYPE_TAXI,
            Constants.VEHICULE_TYPE_CABION,
            Constants.VEHICULE_TYPE_LUXURY,
            Constants.VEHICULE_TYPE_ACCESS};

    public EstimationCard(Context context) {
        super(context, null);

        initClientTrack(context, null);
    }

    public EstimationCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        initClientTrack(context, attrs);
    }

    private void initClientTrack(Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EstimationCard, 0, 0);

        try {
            type = a.getInt(R.styleable.EstimationCard_image, 0);
            ico = a.getString(R.styleable.EstimationCard_ico);
            name = a.getString(R.styleable.EstimationCard_name);
            description = a.getString(R.styleable.EstimationCard_description);
            priceFormatted = a.getString(R.styleable.EstimationCard_price_formatted);
        } finally {
            a.recycle();
        }
        
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.estimation_card, this);

        mainImage = (ImageView) findViewById(R.id.estimation_image);
        icoImage = (ImageView) findViewById(R.id.estimation_ico);
        nameText = (TextView) findViewById(R.id.estimation_name);
        descriptionText = (TextView) findViewById(R.id.estimation_description);
        priceFormattedText = (TextView) findViewById(R.id.estimation_price_formatted);

        setLayout();
    }

    private void setLayout() {
        switch (vehicleTypes[type]) {
            case Constants.VEHICULE_TYPE_LITE: default:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_lite));
                break;

            case Constants.VEHICULE_TYPE_EXECUTIVE:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_executive));
                break;

            case Constants.VEHICULE_TYPE_PET:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_pet));
                break;

            case Constants.VEHICULE_TYPE_DELIVERY:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_delivery));
                break;

            case Constants.VEHICULE_TYPE_BABY:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_baby));
                break;

            case Constants.VEHICULE_TYPE_RICKSHAW:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_rickshaw));
                break;

            case Constants.VEHICULE_TYPE_TAXI:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_taxi));
                break;

            case Constants.VEHICULE_TYPE_CABION:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_cabion));
                break;

            case Constants.VEHICULE_TYPE_LUXURY:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_luxury));
                break;

            case Constants.VEHICULE_TYPE_ACCESS:
                mainImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_estimation_image_access));
                break;
        }

        if(!Strings.isNullOrEmpty(ico)) {
            Picasso.with(context).load(ico).into(icoImage);

        }
        if(!Strings.isNullOrEmpty(name)) {
            nameText.setText(name);
        }

        if(!Strings.isNullOrEmpty(description)) {
            descriptionText.setText(description);
        }

        if(!Strings.isNullOrEmpty(priceFormatted)) {
            priceFormattedText.setText(context.getString(R.string.price_formatted, priceFormatted));
        }
    }

    public void setData(String ico, String name, String description, String priceFormatted, String vehicleType) {
        this.ico = ico;
        this.name = name;
        this.description = description;
        this.priceFormatted = priceFormatted;
        for(int i = 0; i< vehicleTypes.length; i++) {
            if(vehicleTypes[i].equals(vehicleType)) {
                type = i;
                break;
            }
        }

        invalidate();
        requestLayout();
    }

    @Override
    public void invalidate() {
        super.invalidate();

        setLayout();
    }
}
