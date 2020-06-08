package com.example.project2019.Helpers;

public class RequestHelper {
    private String postData = "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/addTask";
    private String removeData = "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/deleteTask";
    private String updateData = "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/updateTask";
    private String getData = "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/getTasks";
    private String changeStatus = "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/updateStatusByID";
    private String deleteCompletedTasks = "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/deleteCompletedTask";

    public String getPostData() {
        return postData;
    }

    public String getRemoveData() {
        return removeData;
    }

    public String getUpdateData() {
        return updateData;
    }

    public String getGetData() {
        return getData;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public String getDeleteCompletedTasks() {
        return deleteCompletedTasks;
    }
}
