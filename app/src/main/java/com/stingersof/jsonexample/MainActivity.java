package com.stingersof.jsonexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Calling method to add a Git Reference", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = findViewById(R.id.class_schedule);

        ArrayList<String> listItems = populateData("appGitReference.json");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Calling expansion Activity with Intent", Toast.LENGTH_SHORT).show();
            }
        });

        String[] files = JsonUtils.getFileList(this);
        for(String s: files) {
            Log.i("FILES", s);

        }

        Log.i("FILES", this.getFilesDir().getAbsolutePath());
        try {
            Log.i("FILES", this.getFilesDir().getCanonicalPath());

        } catch (Exception ex) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> populateData(String jsonFileName) {
        ArrayList<String> returnList = new ArrayList<>();

        String jsonString = processData(jsonFileName);
        Log.i("JSON", jsonString);

        ArrayList<GitReference> references = JsonUtils.populateGitReferences(jsonString);

        for(GitReference g:references) {
            returnList.add(g.getCommand());
        }

        return returnList;
    }

    public String processData(String filename) {
        String jsonString = "";
        boolean isFilePresent = JsonUtils.isFilePresent(this, filename);

        // If editable file is present, load it
        if(isFilePresent) {
            jsonString = JsonUtils.read(this, filename);
            //do the json parsing here and do the rest of functionality of app

            Log.i("JSON", "JSON was present");


        } else {
            // If editable file is not present, create it from resource file
            Log.i("JSON", "JSON file not present. Creating......");
            InputStream is = null;
            try {
                is = getApplicationContext().getAssets().open("gitReference.json");
            } catch (Exception ex) {

            }

            jsonString = JsonUtils.parseJsonToString(is);
            boolean isFileCreated = JsonUtils.create(this, filename, jsonString);

            if(isFileCreated) {
                Log.i("JSON", "Created the filesystem JSON");

            } else {
                //show error or try again.
            }
        }
        return jsonString;
    }

}
