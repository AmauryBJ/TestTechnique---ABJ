package com.testech.amaury.findyourrockstar;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.testech.amaury.findyourrockstar.Controllers.AppController;
import com.testech.amaury.findyourrockstar.DataModels.Rockstar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RockstarListFragment extends Fragment {

    //region Properties

    // Store instance variables
    private String title;
    private int page;

    ListView listView ;

    //Controller
    public static final String TAG = AppController.class.getSimpleName();

    //List adapter
    ArrayAdapter<Rockstar> adapter;

    // Progress dialog
    private ProgressDialog pDialog;

    private PagerAdapter mPagerAdapter;

    //buttons
    private Button btnReloadDataRequest;

    // json array response url
    private String urlJson = "http://54.72.181.8/yolo/contacts.json";

    private View myFragmentView;

    //endregion

    //region Constructor

    // newInstance constructor for creating fragment with arguments
    public static RockstarListFragment newInstance(int page, String title) {
        RockstarListFragment fragmentFirst = new RockstarListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    //endregion

    //region FragmentOverride

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");


    }

    //endregion

    //region Methods

    /**
     * Show loading
     */
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Dismiss loading
     */
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        myFragmentView = inflater.inflate(R.layout.fragment_rockstar_list, container, false);

        //region Init Loading

        pDialog = new ProgressDialog(getActivity().getApplicationContext());
        pDialog.setMessage("Wait for rockstars ;)...");
        pDialog.setCancelable(false);

        //endregion

        //region Init Buttons

        //Get BTN from view
        btnReloadDataRequest = (Button) myFragmentView.findViewById(R.id.btnDataRequest);
        btnReloadDataRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("Button", "ReloadData click");

                //Make request
                makeJsonDataRequest();
            }
        });

        //endregion

        //region init ListView

        // Get ListView object from xml
        listView = (ListView) myFragmentView.findViewById(R.id.rockstarList);
        // Defined Array values to show in ListView
        ArrayList<Rockstar> values = new ArrayList<Rockstar>();
        adapter = new ArrayAdapter<Rockstar>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        //Set event tap on rockstar line
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Rockstar objUser = (Rockstar)listView.getAdapter().getItem(position);
            }
        });


        //endregion

        return myFragmentView;
    }

    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonDataRequest() {

        //showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJson, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                //Suppression de la liste
                adapter.clear();

                try {

                    JSONArray contacts = response.getJSONArray("contacts");

                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject rockstar = (JSONObject) contacts
                                .get(i);

                        Rockstar elmt = new Rockstar(rockstar.getString("firstname"),
                                rockstar.getString("lastname"),
                                rockstar.getString("status"),
                                rockstar.getString("hisface"));

                        Log.d("Rockstar : ",elmt.toString());

                        //Ajout de l'élément
                        adapter.add(elmt);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                // hide the progress dialog
                //hidepDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                // hide the progress dialog
                //hidepDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    //endregion
}
