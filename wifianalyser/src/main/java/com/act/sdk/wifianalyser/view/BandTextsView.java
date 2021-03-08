package com.act.sdk.wifianalyser.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.act.sdk.wifianalyser.R;

public class BandTextsView extends FrameLayout {
    public BandTextsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public BandTextsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BandTextsView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.layout_connected_signals_text, this);
    }
}