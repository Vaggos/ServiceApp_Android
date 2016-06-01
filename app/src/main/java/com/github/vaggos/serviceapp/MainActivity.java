// Todo: Add scrollview to available activity.
// Todo: Add scrollview to check activity.
package com.github.vaggos.serviceapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the buttons.
        Button btn_check = (Button) findViewById(R.id.btn_check);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        Button btn_insert = (Button) findViewById(R.id.btn_insert);
        Button btn_available = (Button) findViewById(R.id.btn_available);


        // Set a listener to btn_check.
        btn_check.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Check Activity.
                Intent checkIntent = new Intent(MainActivity.this, CheckActivity.class);
                startActivity(checkIntent);
            }
        });

        // Set a listener to btn_update.
        btn_update.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Update Activity.
                Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
                startActivity(updateIntent);
            }
        });

        // Set a listener to btn_insert.
        btn_insert.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Update Activity.
                Intent insertIntent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(insertIntent);
            }
        });

        // Set a listener to btn_available.
        btn_available.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Get all the data from the database.
                Cursor result = db.getAllData();
                // If there are no results, prepopulate the db.
                if (result.getCount() == 0) {
                    // No data; Pre-popuplate.
                } else {
                    // Build and show the results.
                    StringBuffer buffer = new StringBuffer();
                    while (result.moveToNext()) {
                        buffer.append("Id: " + result.getString(0) + "\n");
                        buffer.append("Spare part: " + result.getString(1) + "\n");
                        buffer.append("Date changed: " + result.getString(2) + "\n");
                        buffer.append("Date interval (in months): " + result.getString(3) + "\n");
                        buffer.append("Kms changed: " + result.getString(4) + "\n");
                        buffer.append("Kms interval: " + result.getString(5) + "\n\n");
                    }

                    // Show all the results.
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Available entries.");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            }
        });
    }
}


