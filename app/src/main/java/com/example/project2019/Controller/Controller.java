package com.example.project2019.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

public class Controller implements VolleyCrud {

    private Context context;
    private ListView myListView;
    private RequestHelper requestHelper;
    private ArrayList<Task> theList, completedList, finalList;
    private RequestQueue myQueue;
    private EmailNotification emailNotification;


    public Controller(Context c, ListView listView) {
        this.context = c;
        this.myListView = listView;
        requestHelper = new RequestHelper();
        myQueue = Volley.newRequestQueue(context);
        emailNotification = new EmailNotification();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public Controller(Context c) {
        this.context = c;
        requestHelper = new RequestHelper();
        myQueue = Volley.newRequestQueue(context);
        emailNotification = new EmailNotification();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public void postData(Task task) {
        String url = requestHelper.getPostData();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "You've added '" + task.getTaskName() + "' to your list", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                String title = task.getTaskName();
                String info = task.getTaskInfo();

                params.put("taskname", title);
                params.put("taskinfo", info);
                return params;
            }
        };
        myQueue.add(request);
    }

    @Override
    public void updateData(Task task) {
        String url = requestHelper.getUpdateData();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "You've updated '" + task.getTaskName() + "'", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
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

    @Override
    public void getData() {
        String url = requestHelper.getGetData();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                theList = new ArrayList<>();
                completedList = new ArrayList<>();
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
                            if (jsonObject.getString("COMPLETED").equalsIgnoreCase("1")) {
                                taskCompleted.setStatus("Completed");
                            } else {
                                taskCompleted.setStatus("Not completed");
                            }
                            completedList.add(taskCompleted);
                        } else {
                            theList.add(task);
                        }
                    }
                    parseDataToListView(theList, completedList);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        myQueue.add(request);
    }

    private void parseDataToListView(ArrayList<Task> theList, ArrayList<Task> completedList) {
        finalList = null;
        if (this.context instanceof MainActivity) {
            finalList = theList;
        } else if (this.context instanceof CompletedTasks) {
            finalList = completedList;
        }
        taskAdapter adapter = new taskAdapter(context, finalList);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = finalList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("TASK", task);
                Intent intent = new Intent(context, Modification.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public void removeData(Task task) {
        String url = requestHelper.getRemoveData();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "You've removed '" + task.getTaskName() + "' from your list", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
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

    @Override
    public void statusChange(Task task) {
        String url = requestHelper.getChangeStatus();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (task.getStatus().equalsIgnoreCase("Completed")) {
                    Toast.makeText(context, "Task '" + task.getTaskName() + "' has been completed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Task '" + task.getTaskName() + "' is back to your main list", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
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

    public void removeCompletedTasks() {
        String url = requestHelper.getDeleteCompletedTasks();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (completedList.size() != 0) {
                    Toast.makeText(context, "Completed tasks cleared", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "your list is already empty", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        String message = "Dear Mr supervisors, \n\n You've cleared your completed list";
        emailNotification.notifyViaEmail("najem_f@hotmail.com, lucas.rosendahl95@gmail.com", Controller.class.getName(), message);
        myQueue.add(request);
    }

}