package org.softry.learnjava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    Context context;
    TextView tvPagesRead, tvOverallProgress;
    Button btnClear, btnDisableAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = this;

        btnClear = findViewById(R.id.btn_clearProgress);
        btnDisableAds = findViewById(R.id.btn_disableAds);
        tvPagesRead = findViewById(R.id.tvStatsPagesRead);
        tvOverallProgress = findViewById(R.id.tvStatsOverallProgress);

        UpdateStats();

        btnDisableAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(context, DisableAdsActivity.class);
            startActivity(intent);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Your entire progress will be erased. Are you sure you want to do this?");
                dlgAlert.setTitle("Start over");
                dlgAlert.setPositiveButton("Yes, erase", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Utilities.DeleteStorage(context);
                        Utilities.ReloadData(context);
                        UpdateStats();
                        dialog.dismiss();
                    }
                });
                dlgAlert.setNegativeButton("No, go back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }

    private void UpdateStats() {
        tvPagesRead.setText(Utilities.GetTotalPagesRead() + " Pages read");
        tvOverallProgress.setText("Overall progress " + Utilities.GetOverallProgress(this) + "%");
    }
}
