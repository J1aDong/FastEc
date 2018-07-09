package com.mj.fastec.example;

import com.mj.ly_core.activities.ProxyActivity;
import com.mj.ly_core.delegates.LyDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LyDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
