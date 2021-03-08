package com.act.sdk.wifianalyser.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.act.sdk.wifianalyser.R;

public class HeadingCenterView extends FrameLayout {
    public HeadingCenterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public HeadingCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeadingCenterView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.layout_heading_center, this);
    }
}