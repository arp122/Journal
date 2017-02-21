package com.pennapps.arpitsabherwal.journal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by arpitsabherwal on 23/01/16.
 */
public class AutoBioActivity extends ActionBarActivity implements View.OnClickListener {

    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are holded in an Array as you can see

    String TITLES[] = {"Plaid", "Uber", "Google","twitter","facebook"};
    int ICONS[] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic4, R.drawable.pic3, R.drawable.pic6};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Namit Juneja";
    String EMAIL = "hello@namitjuneja.com";
    int PROFILE = R.drawable.profilepic;

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle


    TextView tvToday, tvWeek, tvMonth;
    MessageService gps;
    URL url;
    ListView mListview;
    CustomMultiAdapter multiAdapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Button btFab;
    List<RowItem> rowItems;
    RowItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autobio);

    /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new DrawerAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvToday = (TextView) findViewById(R.id.tvToday);
        tvWeek = (TextView) findViewById(R.id.tvWeek);

        tvMonth.setOnClickListener(this);
        tvToday.setOnClickListener(this);
        tvWeek.setOnClickListener(this);

        gps = new MessageService(this);
        gps.obtainLocation();

        try {
            url = new URL("http://pennpro.herokuapp.com/day");
            FetchStory();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        btFab=(Button)findViewById(R.id.fab);
        btFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvToday:
                toolbar.setTitle("Today");
                tvToday.setTextColor(Color.parseColor("#ffc107"));
                tvWeek.setTextColor(Color.parseColor("#999999"));
                tvMonth.setTextColor(Color.parseColor("#999999"));
                try {
                    url = new URL("http://pennpro.herokuapp.com/day");
                    FetchStory();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvMonth:
                toolbar.setTitle("This Month");
                tvToday.setTextColor(Color.parseColor("#999999"));
                tvWeek.setTextColor(Color.parseColor("#999999"));
                tvMonth.setTextColor(Color.parseColor("#ffc107"));
                try {
                    url = new URL("http://pennpro.herokuapp.com/month");
                    FetchStory();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvWeek:
                toolbar.setTitle("This Week");
                tvToday.setTextColor(Color.parseColor("#999999"));
                tvWeek.setTextColor(Color.parseColor("#ffc107"));
                tvMonth.setTextColor(Color.parseColor("#999999"));
                try {
                    url = new URL("http://pennpro.herokuapp.com/week");
                    FetchStory();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    String response;
    String[] content;
    int[] type;

    private void FetchStory() {

        OkHttpClient client = new OkHttpClient();


        //final MaterialDialog dialog = this.showGroupPicUploadingNotification();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("TAG", "exception", e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String data = response.body().string();

                if (response.isSuccessful()) {
                    Log.d("response", "response" + data);
                    try {
                        JSONObject obj = new JSONObject(data);
                        JSONArray jsonArray = obj.getJSONArray("body");
                        content = new String[jsonArray.length()];
                        type = new int[jsonArray.length()];

                         rowItems= new ArrayList<RowItem>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("json", "js" + jsonArray);
                            type[i] = jsonArray.getJSONObject(i).getInt("type");
                            Log.d("type", "type" + type[i]);
                            content[i] = jsonArray.getJSONObject(i).getString("content");
                            item = new RowItem(content[i], type[i]);
                            rowItems.add(item);

                        }
                        mListview = (ListView) findViewById(R.id.lvStory);


                        multiAdapter = new CustomMultiAdapter(AutoBioActivity.this, rowItems);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListview.setAdapter(multiAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getApplicationContext(),"Data sent to server for analysis",Toast.LENGTH_SHORT).show();
                    /*item=new RowItem(result.get(0),1);
                    rowItems.add(item);
                    multiAdapter = new CustomMultiAdapter(AutoBioActivity.this, rowItems);
                    mListview.setAdapter(multiAdapter);*/

                    //txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }
}
