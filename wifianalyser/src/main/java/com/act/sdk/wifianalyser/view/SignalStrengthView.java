package com.act.sdk.wifianalyser.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.act.sdk.wifianalyser.R;

public class SignalStrengthView extends FrameLayout {
    public SignalStrengthView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public SignalStrengthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SignalStrengthView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.activity_neighbour_wifi, this);
    }
}