package com.example.media_bias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    Button showTableBtn;
    TableLayout historyTable;
    Button homebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        showTableBtn = findViewById(R.id.showTableBtn);
        historyTable = findViewById(R.id.historyTable);
        homebtn = findViewById(R.id.homeBtn);
        showTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyTable.setVisibility(View.VISIBLE);

                // Example data rows
                addRow("1", "https://example.com/article1");
                addRow("2", "https://example.com/article2");
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addRow(String id, String url) {
        TableRow row = new TableRow(this);

        TextView idView = new TextView(this);
        idView.setText(id);
        idView.setTextColor(getResources().getColor(android.R.color.white));
        idView.setPadding(8, 8, 8, 8);

        TextView urlView = new TextView(this);
        urlView.setText(url);
        urlView.setTextColor(getResources().getColor(android.R.color.white));
        urlView.setPadding(8, 8, 8, 8);

        row.addView(idView);
        row.addView(urlView);

        historyTable.addView(row);
    }
}