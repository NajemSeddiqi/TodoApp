package com.example.project2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2019.Controller.Controller;
import com.example.project2019.Model.Task;

public class Modification extends AppCompatActivity {
    private TextView idTxt;
    EditText inputTitle, inputTaskInfo;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);

        idTxt = findViewById(R.id.idTxt);
        inputTitle = findViewById(R.id.modifyTitleIInput);
        inputTaskInfo = findViewById(R.id.modifyTaskInfoInput);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton completedBtn = findViewById(R.id.radioCompleted);
        RadioButton nonCompletedBtn = findViewById(R.id.radioUnCompleted);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        Button updateBtn = findViewById(R.id.updateBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        controller = new Controller(this, null);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        Task task = (Task) bundle.getSerializable("TASK");
        assert task != null;
        idTxt.setText(task.getId());
        inputTitle.setText(task.getTaskName());
        inputTaskInfo.setText(task.getTaskInfo());

        Task updateTask = new Task();
        updateTask.setId(idTxt.getText().toString());

        if (task.getStatus().equalsIgnoreCase("Completed")) {
            completedBtn.setChecked(true);
        } else {
            nonCompletedBtn.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        updateBtn.setOnClickListener(v -> sendToController());
        deleteBtn.setOnClickListener(v -> sendToRemove());
        cancelBtn.setOnClickListener(v -> finish());
    }


    //if mark a task as completed, we change the status in the database
    private void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton checkedBtn = group.findViewById(checkedId);
        boolean isChecked = checkedBtn.isChecked();
        if (isChecked) {
            Task task = new Task();
            task.setStatus(checkedBtn.getText().toString());
            task.setId(idTxt.getText().toString());
            task.setTaskName(inputTitle.getText().toString());
            controller.statusChange(task);
        }
    }

    //Where we call the update method
    private void sendToController() {
        if (!checkFields()) {
            Toast.makeText(this, "Make sure your fields are not empty", Toast.LENGTH_SHORT).show();
        } else {
            Task updateTask = new Task();
            updateTask.setId(idTxt.getText().toString());
            updateTask.setTaskName(inputTitle.getText().toString());
            updateTask.setTaskInfo(inputTaskInfo.getText().toString());
            controller.updateData(updateTask);
        }
    }

    private void sendToRemove() {
        Task updateTask = new Task();
        updateTask.setId(idTxt.getText().toString());
        controller.removeData(updateTask);
        Modification.this.finish();
    }

    private boolean checkFields() {
        return inputTitle.length() != 0 && inputTaskInfo.length() != 0;
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
                Intent intent = new Intent(Modification.this, CompletedTasks.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_2:
                Intent intent1 = new Intent(Modification.this, Settings.class);
                startActivity(intent1);
                return true;
            case R.id.menu_item_3:
                Intent intent2 = new Intent(Modification.this, About.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
