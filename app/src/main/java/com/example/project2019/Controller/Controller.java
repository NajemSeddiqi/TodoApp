package com.example.project2019.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project2019.CompletedTasks;
import com.example.project2019.MainActivity;
import com.example.project2019.Helpers.EmailNotification;
import com.example.project2019.Helpers.RequestHelper;
import com.example.project2019.Model.Task;
import com.example.project2019.Model.VolleyCrud;
import com.example.project2019.Helpers.taskAdapter;
import com.example.project2019.Modification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//Not a conventional controller or architecture really
//i wanted to try out an MVC version for android even though MVVM is always better
public class Controller implements VolleyCrud {
    private Context context;
    private ListView myListView;
    private RequestHelper requestHelper;
    private ArrayList<Task> theList, completedList, finalList;
    //Needed to instantialize the volley library
    private RequestQueue myQueue;
    private EmailNotification emailNotification;


    public Controller(Context c, ListView listView) {
        this.context = c;
        this.myListView = listView;
        requestHelper = new RequestHelper();
        myQueue = Volley.newRequestQueue(context);
        emailNotification = new EmailNotification();
        //this is needed for the email sending library: see emailNotification for details on the class
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //This method does what the name suggests, it adds data i.e. a new task into the database
    //Using the volley library, we nullify the need for running backend-call methods async
    @Override
    public void addData(Task task) {
        String url = requestHelper.getAddDataUrl();
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(context, "You've added '" + task.getTaskName() + "' to your list", Toast.LENGTH_SHORT).show(),
                error -> Log.v("Error message: ", Objects.requireNonNull(error.getMessage()))) {
            @Override
            //We call the map method to create our params
            //taskname and taskinfo must correspond with the backend method params
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                String title = task.getTaskName();
                String info = task.getTaskInfo();
                params.put("taskname", title);
                params.put("taskinfo", info);
                return params;
            }
        };
        //Then we add the request to the queue
        myQueue.add(request);
    }

    //Method that updates a task, same principle as the addData method
    @Override
    public void updateData(Task task) {
        String url = requestHelper.getUpdateDataUrl();
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(context, "You've updated '" + task.getTaskName() + "'", Toast.LENGTH_SHORT).show(),
                error -> Log.v("Error message: ", Objects.requireNonNull(error.getMessage()))) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                String title = task.getTaskName();
                String info = task.getTaskInfo();
                String id = task.getId();

                params.put("id", id);
                params.put("taskname", title);
                params.put("taskinfo", info);
                return params;
            }
        };
        myQueue.add(request);
    }

    //Method that gets our tasks
    @Override
    public void getData() {
        String url = requestHelper.getDataUrl();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            //We need to add the data into two different lists
            theList = new ArrayList<>();
            //this list is the tasks that have been completed
            completedList = new ArrayList<>();
            //these two if statements checks whether the task is completed or not and adds them to the appropriate list
            try {
                for (int i = 0; i < response.length(); i++) {
                    Task task = new Task();
                    JSONObject jsonObject = response.getJSONObject(i);
                    task.setId(jsonObject.getString("ID"));
                    task.setTaskName(jsonObject.getString("TASKNAME"));
                    task.setTaskInfo(jsonObject.getString("TASKINFO"));
                    task.setDate(jsonObject.getString("DATE"));
                    if (jsonObject.getString("COMPLETED").equalsIgnoreCase("1")) {
                        task.setStatus("Completed");
                    } else {
                        task.setStatus("Not completed");
                    }

                    if (task.getStatus().equalsIgnoreCase("Completed")) {
                        Task taskCompleted = new Task();
                        taskCompleted.setId(jsonObject.getString("ID"));
                        taskCompleted.setTaskName(jsonObject.getString("TASKNAME"));
                        taskCompleted.setTaskInfo(jsonObject.getString("TASKINFO"));
                        taskCompleted.setDate(jsonObject.getString("DATE"));
                        taskCompleted.setStatus("Completed");
                        completedList.add(taskCompleted);
                    } else {
                        theList.add(task);
                    }
                }
                parseDataToListView(theList, completedList);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

        }, error -> Log.v("Error message: ", Objects.requireNonNull(error.getMessage())));
        myQueue.add(request);
    }

    //This is where we parse the data and add them to the listView
    //based on the context sent in, the list is either the tasks that are not completed or completed
    private void parseDataToListView(ArrayList<Task> theList, ArrayList<Task> completedList) {
        finalList = null;
        if (this.context instanceof MainActivity) {
            finalList = theList;
        } else if (this.context instanceof CompletedTasks) {
            finalList = completedList;
        }

        taskAdapter adapter = new taskAdapter(context, finalList);
        //Lists must have a custom adapter if the items are custom and not just one basic string
        myListView.setAdapter(adapter);
        //we set the onItemClick event to start the modification activity
        myListView.setOnItemClickListener((parent, view, position, id) -> {
            Task task = finalList.get(position);
            //a bundle is needed to send the data of the item to the next activity
            Bundle bundle = new Bundle();
            bundle.putSerializable("TASK", task);
            Intent intent = new Intent(context, Modification.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    //Method that removes a task, not the same as completing it
    @Override
    public void removeData(Task task) {
        String url = requestHelper.getRemoveDataUrl();
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(context, "The task has been removed from your list", Toast.LENGTH_SHORT).show(),
                error -> Log.v("Error message: ", Objects.requireNonNull(error.getMessage()))) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                String ID = task.getId();

                params.put("id", ID);
                return params;
            }
        };
        myQueue.add(request);
    }

    //This method changes the task status from not completed to complete
    @Override
    public void statusChange(Task task) {
        String url = requestHelper.getChangeStatusUrl();
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            if (task.getStatus().equalsIgnoreCase("Completed")) {
                Toast.makeText(context, "Task '" + task.getTaskName() + "' has been completed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Task '" + task.getTaskName() + "' is back to your main list", Toast.LENGTH_SHORT).show();
            }
        }, error -> Log.v("Error message: ", Objects.requireNonNull(error.getMessage()))) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                String status = task.getStatus();
                String id = task.getId();
                String stat;
                if (status.equalsIgnoreCase("Completed")) {
                    stat = "1";
                } else {
                    stat = "0";
                }
                params.put("id", id);
                params.put("completed", stat);
                return params;
            }
        };
        myQueue.add(request);
    }

    //This method removes all the completed tasks from the completed list
    public void removeCompletedTasks() {
        if (!completedList.isEmpty()) {
            String url = requestHelper.getDeleteCompletedTasksUrl();
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> Toast.makeText(context, "Completed tasks cleared", Toast.LENGTH_SHORT).show(),
                    error -> Log.v("Error message: ", Objects.requireNonNull(error.getMessage())));
            String message = "Dear Mr supervisors, \n\n You've cleared your completed list";
            emailNotification.notifyViaEmail("najem_f@hotmail.com, lucas.rosendahl95@gmail.com", Controller.class.getName(), message);
            myQueue.add(request);
        } else {
            Toast.makeText(context, "your list is already empty", Toast.LENGTH_SHORT).show();
        }

    }

}