package org.softry.learnjava;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ReportActivity extends AppCompatActivity {
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

		final RadioGroup rbg = findViewById(R.id.radioGroup);
        submitBtn = findViewById(R.id.btn_reportProblem);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				int selectedId = rbg.getCheckedRadioButtonId();
				if(selectedId != 0) {
					finish();
				}
                else
                    Snackbar.make(v,"Please select a problem to report", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
