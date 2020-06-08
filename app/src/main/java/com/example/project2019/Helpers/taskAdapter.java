package com.example.project2019.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project2019.Model.Task;
import com.example.project2019.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class taskAdapter extends ArrayAdapter<Task> {

    public taskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task tasks = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView taskTitle = convertView.findViewById(R.id.taskTitleTxt);
        TextView taskDate = convertView.findViewById(R.id.tastDateTxt);
        TextView taskStatus = convertView.findViewById(R.id.taskStatusTxt);

        taskTitle.setText(tasks.getTaskName());
        taskDate.setText(tasks.getDate());
        taskStatus.setText(tasks.getStatus());

        return convertView;
    }
}
