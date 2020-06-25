package com.example.project2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project2019.Controller.Controller;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.myListView);

        Button addBtn = findViewById(R.id.addTaskBtn);
        addBtn.setOnClickListener(v -> openDialog());
        Calendar calender = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());
        TextView textViewDate = findViewById(R.id.textView_currentDate);
        textViewDate.setText(currentDate);

        controller = new Controller(this, listView);
        controller.getData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_1:
                Intent intent = new Intent(MainActivity.this, CompletedTasks.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_2:
                Intent intent1 = new Intent(MainActivity.this, Settings.class);
                startActivity(intent1);
                return true;
            case R.id.menu_item_3:
                Intent intent2 = new Intent(MainActivity.this, About.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //This opens our add dialog
    private void openDialog() {
        CreationDialog cd = new CreationDialog(this);
        cd.show();
        cd.setOnDismissListener(dialog -> onRestart());
    }

    //This allows the app to refresh after each action i.e add, delete, update and display correct values at all times
    @Override
    protected void onPostResume() {
        super.onPostResume();
        controller.getData();
    }

    //This allows the app to refresh after each action i.e add, delete, update and display correct values at all times
    @Override
    public void onRestart() {
        super.onRestart();
        controller.getData();
    }
}
