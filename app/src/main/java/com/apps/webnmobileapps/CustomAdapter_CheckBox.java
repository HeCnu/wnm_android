/*package com.apps.webnmobileapps;

import android.widget.CompoundButton;

public class CustomAdapter_CheckBox {

    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            Todo totoInFocus = (Todo) buttonView.getTag();
            if (totoInFocus.isCompleted == isChecked) return;

        }
    });

}

        Ion.with(this)
                .load(getString(R.string.kIndexRequest))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result != null) {
                            List<Project> projects = new ArrayList<Project>();
                            for (final JsonElement projectJsonElement : result) {
                                projects.add(new Gson().fromJson(projectJsonElement, Project.class));
                            }
                        }
                    }
                });

        JsonObject params = new JsonObject();
        params.addProperty("todoText", {...});
        params.addProperty("projectId", {...});

*/