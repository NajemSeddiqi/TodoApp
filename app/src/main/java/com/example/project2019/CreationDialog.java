package com.example.project2019;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.project2019.Controller.Controller;
import com.example.project2019.Model.Task;

public class CreationDialog extends Dialog implements android.view.View.OnClickListener {
    private EditText inputTitle, inputInfo;
    private Button addBtn, cancelBtn;
    private Controller controller;


    public CreationDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_template);

        inputTitle = findViewById(R.id.creationInputTitle);
        inputInfo = findViewById(R.id.creationInputInfo);
        addBtn = findViewById(R.id.addBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        controller = new Controller(getContext());
        addBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    private void sendToController() {
        Task task = new Task();
        task.setTaskName(inputTitle.getText().toString());
        task.setTaskInfo(inputInfo.getText().toString());
        if (!checkField()) {
            Toast.makeText(getContext(), "Make sure your fields are not empty", Toast.LENGTH_SHORT).show();
        } else {
            controller.postData(task);
        }
    }

    private boolean checkField() {
        boolean fields = true;
        if (inputTitle.length() == 0 || inputInfo.length() == 0) {
            fields = false;
        }
        return fields;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                sendToController();
                if (checkField()) {
                    dismiss();
                }
                break;
            case R.id.cancelBtn:
                dismiss();
            default:
                break;
        }
    }
}
