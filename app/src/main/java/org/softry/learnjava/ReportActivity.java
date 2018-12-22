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

        final RadioButton rb1, rb2, rb3, rb4, rb5;
        submitBtn = findViewById(R.id.btn_reportProblem);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        rb5 = findViewById(R.id.radioButton5);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rb1.isSelected() || rb2.isSelected() || rb3.isSelected() || rb4.isSelected() || rb5.isSelected())
                    finish();
                else
                    Snackbar.make(v,"Please select a problem to report", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
