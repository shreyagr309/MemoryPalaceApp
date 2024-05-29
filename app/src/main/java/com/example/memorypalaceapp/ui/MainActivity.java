
package com.example.memorypalaceapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.ActivityMainBinding;
import com.example.memorypalaceapp.ui.history.HistoryRoomActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ActivityMainBinding activityMainBinding;

    private static String Key_History_Room_Created = "History_Room_Created";//Key for storing the value of history room created
    private boolean historyRoomCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enable DataBinding
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Used Shared preferences to save if a user clicks on the history room or not.
        sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        historyRoomCreated = sharedPreferences.getBoolean(Key_History_Room_Created, false);
        Log.v("tag",""+historyRoomCreated);
        if(historyRoomCreated){
            activityMainBinding.buttonHistoryroom.setText("Go to History room");
        }
        else{
            activityMainBinding.buttonHistoryroom.setText("Create History room");
        }

        // Create the Intent once
        Intent historyRoomIntent = new Intent(MainActivity.this, HistoryRoomActivity.class);

        activityMainBinding.buttonHistoryroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("tag",""+historyRoomCreated);
                if (historyRoomCreated) {

                    // history room has already created, will change the UI and Logic here
                    startActivity(historyRoomIntent);
                    activityMainBinding.buttonHistoryroom.setText("Go to History Room");
                }
                else
                {
                    // Will Perform actions to create history room(like going to History room activity)
                    // Once created, update shared preferences

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Key_History_Room_Created, true);
                    editor.apply();
                    startActivity(historyRoomIntent);
                    historyRoomCreated=true;
                    activityMainBinding.buttonHistoryroom.setText("Go to History Room");
                    Toast.makeText(getApplicationContext(), "Success, please create and add your items", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}