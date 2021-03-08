package com.act.sdk.wifianalyser.presenter;

import android.app.Application;

import org.jetbrains.annotations.Nullable;

public interface ContextAccessor {
    @Nullable
    Application getApplicationContext();
}
