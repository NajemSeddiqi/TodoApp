package com.example.project2019.Model;

import com.example.project2019.Model.Task;

public interface VolleyCrud {
    void postData(Task task);
    //void updateData(String id,String taskName,String taskInfo);
    void updateData(Task task);
    void getData();
    void removeData(Task task);
    void statusChange(Task task);
}
