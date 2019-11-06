package com.apps.webnmobileapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.scalified.fab.ActionButton;
import java.util.ArrayList;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private Toolbar toolbar;
    private CustomAdapter_List mAdapter;
    private ActionButton btnAdd;
    private ArrayList<String> projectsList;
    private ArrayList<Project> projects;
    private Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetDataFromServer getDataFromServer = new GetDataFromServer(this);
        getDataFromServer.execute();


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initButton();

        btnAdd = (ActionButton)findViewById(R.id.action_button);
        btnAdd.setOnClickListener(this);


    }

    public void initButton(){

        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);

        actionButton.setButtonColor(getResources().getColor(R.color.pinkSearch));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.fab_material_pink_900));

        actionButton.setImageResource(R.drawable.fab_plus_icon);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.action_button:

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("URL_PROJECTS_LIST", projectsList);

                Intent intent = new Intent(this, ActivityTodo.class);//TODO Call second activity
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public class GetDataFromServer extends AsyncTask<Void, Void, Void>{

        private Context mContext;
        private ArrayList<Project> projectsTodoAsynk;

        public GetDataFromServer(Context context){
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            final ArrayList<Project> projectsTodo = new ArrayList<>();
            Ion.with(mContext)
                    .load("https://webnmobilestep3.herokuapp.com/projects.json")
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            projects = new ArrayList<>();
                            System.out.println(result);
                            System.out.println(result);

                            for (final JsonElement projectJsonElement : result) {
                                projects.add(new Gson().fromJson(projectJsonElement, Project.class));
                            }

                            ArrayList<Object> projectObjects = new ArrayList<>();

                            for (Project project: projects) {
                                projectObjects.add(project.getTitle());
                                for (Todo todo : project.getTodos()) {
                                    projectObjects.add(todo);
                                }
                            }

                            listView = (ListView) findViewById(R.id.listView);
                            listView.setAdapter(new CustomAdapter_List(mContext, projectObjects));

                            projectsList = new ArrayList<>();
                            for (Project project : projects){
                                projectsList.add(project.getTitle());
                            }
                        }
                    });
            return null;
        }
    }
}
