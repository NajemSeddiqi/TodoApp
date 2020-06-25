package com.example.project2019.Helpers;

//basic class that return our backend urls
public class RequestHelper {

    public String getAddDataUrl() {
        return "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/addTask";
    }

    public String getRemoveDataUrl() {
        return "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/deleteTask";
    }

    public String getUpdateDataUrl() {
        return "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/updateTask";
    }

    public String getDataUrl() {
        return "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/getTasks";
    }

    public String getChangeStatusUrl() {
        return "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/updateStatusByID";
    }

    public String getDeleteCompletedTasksUrl() {
        return "http://users.du.se/~h17lucro/IK2019/Project_Work/IK2019_Project_Work/index1.php?controller/deleteCompletedTask";
    }
}
