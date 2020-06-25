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

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);

        controller = new Controller(getContext(), null);
    }

    private void sendToController() {
        if (!checkField()) {
            Toast.makeText(getContext(), "Make sure your fields are not empty", Toast.LENGTH_SHORT).show();
        } else {
            Task task = new Task();
            task.setTaskName(inputTitle.getText().toString());
            task.setTaskInfo(inputInfo.getText().toString());
            controller.addData(task);
        }
    }

    private boolean checkField() {
        return inputTitle.length() != 0 && inputInfo.length() != 0;
    }

    //Invoked this for the onClickListener just to try it
    //i prefer using self named methods like the other classes
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                sendToController();
                if (checkField()) dismiss();
                break;
            case R.id.cancelBtn:
                dismiss();
            default:
                break;
        }
    }
}
