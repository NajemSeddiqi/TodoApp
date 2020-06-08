package com.example.project2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.project2019.Controller.Controller;

public class Settings extends AppCompatActivity {
    private EditText emailInput;
    private Button enableBtn, cancelBtn;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        controller = new Controller(this);
        emailInput = findViewById(R.id.emailinput);
        enableBtn = findViewById(R.id.enableEmailBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(v -> finish());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_item_1:
                Intent intent = new Intent(Settings.this, CompletedTasks.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_2:
                return true;
            case R.id.menu_item_3:
                Intent intent2 = new Intent(Settings.this, About.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
