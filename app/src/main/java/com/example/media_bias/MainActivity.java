package com.example.media_bias;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    TextView resultText;
    EditText urlInput;
    Button submitBtn;
    LinearLayout homeBtn, historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = findViewById(R.id.urlInput);
        submitBtn = findViewById(R.id.submitBtn);
        homeBtn = findViewById(R.id.homeBtn);
        historyBtn = findViewById(R.id.historyBtn);
        dbHelper = new DBHelper(this);
        resultText = findViewById(R.id.resultText);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlInput.getText().toString().trim();
                if (!url.isEmpty()) {
                    boolean isInserted = dbHelper.insertUrl(url);
                    if (isInserted) {
                        Toast.makeText(MainActivity.this, "URL saved: " + url, Toast.LENGTH_SHORT).show();
                        new WebScraperTask().execute(url);

                    } else {
                        Toast.makeText(MainActivity.this, "Failed to save URL", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                // Navigate to Home if needed
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
    private class WebScraperTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            try {
                Document doc = Jsoup.connect(urls[0]).get();

                // Extract the page title
                String title = doc.title();

                // Extract all elements with class "HNMDR"
                StringBuilder titlesBuilder = new StringBuilder();
                Elements titleElements = doc.select(".HNMDR");
                for (Element el : titleElements) {
                    titlesBuilder.append("- ").append(el.text().trim()).append("\n");
                }

                // Extract body content from the class "js_tbl_article"
                Element bodyElement = doc.selectFirst(".js_tbl_article");
                String body = (bodyElement != null) ? bodyElement.text().trim() : "No body content found.";

                // Combine everything into one result string
                result = "Page Title: " + title +
                        "\n\nHeadlines:\n" + titlesBuilder.toString() +
                        "\nBody:\n" + body;

            } catch (Exception e) {
                result = "Error scraping the page: " + e.getMessage();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // ✅ FIX: Use MainActivity.this for context
            resultText.setText(result);

        }
    }
}
