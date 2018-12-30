package org.softry.learnjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch proOnlySwitch = findViewById(R.id.switch1);
        proOnlySwitch.setChecked(Utilities.ShowProOnlyChapters);
        proOnlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utilities.ShowProOnlyChapters = isChecked;
            }
        });

        Switch comingSoonSwitch = findViewById(R.id.switch2);
        comingSoonSwitch.setChecked(Utilities.ShowComingSoonLessons);
        comingSoonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utilities.ShowComingSoonLessons = isChecked;
            }
        });
    }
}
