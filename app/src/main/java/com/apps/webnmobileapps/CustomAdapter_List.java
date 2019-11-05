package com.apps.webnmobileapps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;

class CustomAdapter_List extends BaseAdapter {

    private static final int TODO_ITEM = 0;
    private static final int HEADER = 1;

    private ArrayList<Object> list;
    private LayoutInflater mInflater;

    public CustomAdapter_List(Context context, ArrayList<Object> list) {

        this.list = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Todo)
            return TODO_ITEM;
        else
            return HEADER;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            switch (getItemViewType(position)){
                case TODO_ITEM:
                    convertView = mInflater.inflate(R.layout.snippet_item1, null);
                    break;
                case HEADER:
                    convertView = mInflater.inflate(R.layout.snippet_item2, null);
                    break;
            }
        }
        switch (getItemViewType(position)){
            case TODO_ITEM:
                final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.iscompleted);
                final TextView textView = (TextView) convertView.findViewById(R.id.text);

                checkBox.setChecked(((Todo)list.get(position)).getCompleted());
                textView.setText(((Todo)list.get(position)).getText());
                if(((Todo)list.get(position)).getCompleted() == checkBox.isChecked() && ((Todo)list.get(position)).getCompleted() == true){
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SetTodoIsCompleted setTodoIsCompleted = new SetTodoIsCompleted((Todo)list.get(position), isChecked);
                        setTodoIsCompleted.execute();

                        if(((Todo)list.get(position)).getCompleted() == isChecked && ((Todo)list.get(position)).getCompleted() == true){
                            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }else{
                            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        }

                        System.out.println(isChecked);
                    }
                });


                break;
            case HEADER:
                TextView title = (TextView)convertView.findViewById(R.id.textSeparator);

                title.setText((String)list.get(position));
                break;
        }
        return convertView;

    }

    public static class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }
}

class SetTodoIsCompleted extends AsyncTask<Void, Void, Void>{

    private Todo todo;
    private Boolean isChecked;

    public SetTodoIsCompleted(Todo todo, Boolean isChecked){
        this.todo = todo;
        this.isChecked = isChecked;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        JSONObject todoParams = new JSONObject();
        todoParams.put("isCompleted", isChecked);
        todoParams.put("id", todo.getIdTodo());

        String url = "https://webnmobilestep3.herokuapp.com/todos/" + todo.getIdTodo();
        DefaultHttpClient client = new DefaultHttpClient();
        org.apache.http.client.methods.HttpPatch post = new org.apache.http.client.methods.HttpPatch(url);
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