package com.apps.webnmobileapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityTodo extends AppCompatActivity {

    private String dbTextTodo;
    private Integer dbProjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        ListView listView = (ListView) findViewById(R.id.projectsList);

        ArrayList<String> projectsList = getIntent().getStringArrayListExtra("URL_PROJECTS_LIST");

        String projects = getIntent().getStringExtra("URL_PROJECTS");


        System.out.println(projects);

        ArrayAdapter<String> projectsAdapter = new ArrayAdapter<String>(this, R.layout.single_choise_custom, projectsList);

        listView.setAdapter(projectsAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(2, true);
        dbProjectId = 2;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbProjectId = position+1;
                EditText editText = (EditText)findViewById(R.id.todo_text);
                dbTextTodo = editText.getText().toString();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_todo){

            EditText editText = (EditText)findViewById(R.id.todo_text);
            dbTextTodo = editText.getText().toString();
            if(dbTextTodo != ""){
                PostNewTask postNewTask = new PostNewTask();
                postNewTask.execute();
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
    public class PostNewTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            JSONObject todoParams = new JSONObject();
            todoParams.put("text", dbTextTodo);
            todoParams.put("isCompleted", false);
            todoParams.put("project_id", dbProjectId);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://webnmobilestep3.herokuapp.com/todos");
            String response = null;
            JSONObject json = new JSONObject();
            try {
                try {
                    // setup the returned values in case
                    // something goes wrong
                    json.put("success", false);
                    json.put("info", "Something went wrong. Retry!");

                    // add the users's info to the post params
                    StringEntity se = new StringEntity(todoParams.toString());
                    post.setEntity(se);

                    // setup the request headers
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
