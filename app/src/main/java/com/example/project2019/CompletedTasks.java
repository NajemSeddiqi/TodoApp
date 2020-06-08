package com.example.project2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.project2019.Controller.Controller;

public class CompletedTasks extends AppCompatActivity implements android.view.View.OnClickListener {
    private Controller controller;
    private ListView theListView;
    private Button deleteCompletedBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);

        theListView = findViewById(R.id.completedList);
        deleteCompletedBtn = findViewById(R.id.deleteCompletedBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        controller = new Controller(this, theListView);
        controller.getData();
        deleteCompletedBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(v -> finish());
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
                return true;
            case R.id.menu_item_2:
                Intent intent1 = new Intent(CompletedTasks.this, Settings.class);
                startActivity(intent1);
                return true;
            case R.id.menu_item_3:
                Intent intent2 = new Intent(CompletedTasks.this, About.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteCompletedBtn:
                controller.removeCompletedTasks();
                onResume();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.getData();
    }
}
