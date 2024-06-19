package com.example.memorypalaceapp.view;

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
import com.example.memorypalaceapp.view.history.HistoryRoomActivity;
public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_STORAGE_PERMISSION = 1; // Request code for permission
    private SharedPreferences sharedPreferences;
    private ActivityMainBinding activityMainBinding;
    private static final String Key_History_Room_Created = "History_Room_Created"; // Key for storing the value of history room created
    private boolean historyRoomCreated;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enable DataBinding
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Use SharedPreferences to save if a user clicks on the history room or not.
        sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        historyRoomCreated = sharedPreferences.getBoolean(Key_History_Room_Created, false);
        Log.v("tag", "" + historyRoomCreated);
        if (historyRoomCreated) {
            activityMainBinding.buttonHistoryroom.setText("Go to History room");
        } else {
            activityMainBinding.buttonHistoryroom.setText("Create History room");
        }
        activityMainBinding.buttonHistoryroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("tag", "" + historyRoomCreated);
                if (historyRoomCreated) {
                    // history room has already been created, will change the UI and logic here
                    startHistoryRoomActivity();
                } else {
                    // Check for permission before creating history room
//                    if (checkPermission()) {
//                        createHistoryRoom();
//                    } else {
//                        // Request permission
//                        requestStoragePermission();
//                    }
                    createHistoryRoom();
                }
            }
        });
    }
//    private boolean checkPermission() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            //Android is 11(R) or above
//            return Environment.isExternalStorageManager();
//        } else {
//            //Android is below 11(R)
//            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
//        }
//    }

//    private void requestStoragePermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//
//            try {
//                Log.d("TAG", "request permission:try");
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
//                intent.setData(uri);
//                storageActivityresultLauncher.launch(intent);
//            } catch (Exception e) {
//                Log.e("TAG", "request Permissions: catch", e);
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                storageActivityresultLauncher.launch(intent);
//            }
//
//        } else {
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
//        }
//    }
    private void createHistoryRoom() {
        // Perform actions to create history room (like going to History room activity)
        // Once created, update shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key_History_Room_Created, true);
        editor.apply();
        historyRoomCreated = true;
        activityMainBinding.buttonHistoryroom.setText("Go to History Room");
        Toast.makeText(getApplicationContext(), "Success, please create and add your items", Toast.LENGTH_SHORT).show();
        startHistoryRoomActivity();
    }
    private void startHistoryRoomActivity() {
        Intent historyRoomIntent = new Intent(MainActivity.this, HistoryRoomActivity.class);
        startActivity(historyRoomIntent);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_STORAGE_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, create history room
//                createHistoryRoom();
//            } else {
//                // Permission denied, inform user
//                Toast.makeText(this, "Permission denied. Cannot create history room", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private ActivityResultLauncher<Intent> storageActivityresultLauncher=registerForActivityResult(
//
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult o) {
//                    Log.d("TAG","onActivityResult");
//                    //here we will handle the result of Intent
//                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
//
//                        if(Environment.isExternalStorageManager()){
//                            //Manage External Storage Permission is granted.
//                            Log.d("TAG","onActivityResult: Manage External Storage Permission is Granted");
//                            createHistoryRoom();
//
//                        }
//                        else{
//
//                            Toast.makeText(MainActivity.this,"Permission Denied.",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                }
//            }
//
//    );
}