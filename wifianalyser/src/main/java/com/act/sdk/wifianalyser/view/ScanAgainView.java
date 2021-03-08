package com.act.sdk.wifianalyser.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.act.sdk.wifianalyser.R;

public class ScanAgainView extends FrameLayout {
    public ScanAgainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public ScanAgainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ScanAgainView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.layout_scan_again, this);
    }
}