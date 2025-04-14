package com.example.media_bias;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    Button showTableBtn;
//    TableLayout historyTable;
    Button homebtn;
    DBHelper dbHelper;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        showTableBtn = findViewById(R.id.showTableBtn);
//        historyTable = findViewById(R.id.historyTable);
        homebtn = findViewById(R.id.homeBtn);
        resultText = findViewById(R.id.resultText);
        resultText.setMovementMethod(new android.text.method.ScrollingMovementMethod());


        dbHelper = new DBHelper(this);
        showTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder historyOutput = new StringBuilder();

                Cursor cursor = dbHelper.getAllData();

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String id = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
                        String url = cursor.getString(cursor.getColumnIndexOrThrow("URL"));
                        historyOutput.append("ID: ").append(id).append("\nURL: ").append(url).append("\n\n");
                    } while (cursor.moveToNext());

                    resultText.setText(historyOutput.toString());
                    Toast.makeText(HistoryActivity.this, "History loaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    resultText.setText("No data found.");
                    Toast.makeText(HistoryActivity.this, "No data found in history", Toast.LENGTH_SHORT).show();
                }

                if (cursor != null) {
                    cursor.close();
                }
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

//    private void addRow(String id, String url) {
//        TableRow row = new TableRow(this);
//
//        TextView idView = new TextView(this);
//        idView.setText(id);
//        idView.setTextColor(getResources().getColor(android.R.color.white));
//        idView.setPadding(8, 8, 8, 8);
//
//        TextView urlView = new TextView(this);
//        urlView.setText(url);
//        urlView.setTextColor(getResources().getColor(android.R.color.white));
//        urlView.setPadding(8, 8, 8, 8);
//
//        row.addView(idView);
//        row.addView(urlView);
//
//        historyTable.addView(row);
//    }
}