package com.example.project2019.Model;

import com.example.project2019.Model.Task;

public interface VolleyCrud {
    void addData(Task task);
    void updateData(Task task);
    void getData();
    void removeData(Task task);
    void statusChange(Task task);
}
