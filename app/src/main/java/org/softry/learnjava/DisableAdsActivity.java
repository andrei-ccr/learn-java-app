package org.softry.learnjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisableAdsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disable_ads);

        Button btn = findViewById(R.id.btn_getAdFree);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //TODO: Navigate to Google Play Store URL of the ad-free app version
    }
}
