package com.github.vaggos.serviceapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    // Create a database variable.
    DatabaseHelper serviceDb = new DatabaseHelper(DeleteActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the text views.
        final TextView textView_results_delete = (TextView) findViewById(R.id.textView_results_delete);
        final EditText editText_spare_part_delete = (EditText) findViewById(R.id.editText_spare_part_delete);

        // Get the button.
        final Button btn_proceed_delete = (Button) findViewById(R.id.btn_proceed_delete);

        // Build the String Array with the db data.
        Cursor cursor = serviceDb.getAllData();
        final String[][] dataArray = new String[cursor.getCount()][2];
        int j = 0;
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String spare_part = cursor.getString(1);
            dataArray[j][0] = id;
            dataArray[j][1] = spare_part;
            j++;
        }

        // Prepare the message for the spare part selection.
        String message = "";
        for (int i = 0; i < dataArray.length; i++) {
            String name = dataArray[i][1];
            message += "For " + name + ", enter " + (i + 1) + ".\n";
        }

        // Show the message.
        textView_results_delete.setText(message);

        // Set listener on Proceed button.
        btn_proceed_delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = -1;
                        try {
                            index = Integer.parseInt(editText_spare_part_delete.getText().toString());
                        } catch (Exception e) {/*Code here.*/}
                        if (index > -1) {
                            int result = serviceDb.deleteData(dataArray[index - 1][0]);
                            if (result > 0) {
                                Toast.makeText(getApplicationContext(), dataArray[index - 1][1] + " successfully deleted.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), dataArray[index - 1][1] + " failed to delete.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );
    }

}
