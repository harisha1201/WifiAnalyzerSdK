package com.act.sdk.wifianalyser.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.act.sdk.wifianalyser.R;

public class OptimizeView extends FrameLayout {
    public OptimizeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OptimizeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OptimizeView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.layout_optimize, this);
    }
}