package com.example.onswipelisteners;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.onswipelisteners.AppController.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public String absent,totals;
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;
    private TextView fromdetails;
    String url3 = "http://192.168.43.78/www/html/shule_one/shuleoneselect.php";
    public  SessionManager sessionManager;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        listView =  view.findViewById(R.id.list);
        fromdetails=view.findViewById(R.id.form_details);

        adapter = new CustomListAdapter(getActivity(), movieList);
        pieChart=(PieChart)view.findViewById(R.id.pieChart);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        getformdetails();





      getjson();



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getEntries() {

    }



    public void getimages(){
        JsonArrayRequest movieReq = new JsonArrayRequest(url3,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        final String [][] arr1 = new String [response.length()][20];
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("admno"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(obj.getString("admno"));
                                movie.setYear(obj.getString("status"));
                                movie.setPrice(obj.getString("status"));









                                // Genre is json array
                               // arr1[i][0]=obj.getString("name");
                                //arr1[i][1]=obj.getString("image");
                                //arr1[i][2]=obj.getString("source");
                               // arr1[i][3]=obj.getString("price");


                                //System.out.println( arr1[i][2]);
                                //System.out.println( arr[0][0]);


                                // Genre is json array
                                movieList.add(movie);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

}

    private void getjson(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {





hidePDialog();




                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){


                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    // arrivals_session playerModel = new arrivals_session();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    Movie movie = new Movie();
                                    movie.setTitle(dataobj.getString("admno"));
                                    movie.setThumbnailUrl(dataobj.getString("image"));
                                    movie.setRating(dataobj.getString("date"));
                                    movie.setPrice(dataobj.getString("status"));
                                  //  movie.setYear(dataobj.getString("date"));

                                    movieList.add(movie);


                                    //statuscheckArrayList.add(playerModel);

                                }







                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hidePDialog();
                    }
                });


        // request queue

        AppController.getInstance().addToRequestQueue(stringRequest);



    }


    public  void getformdetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {


                            hidePDialog();


                            JSONObject obj = new JSONObject(response);
                            if (obj.optString("status").equals("true")) {


                                JSONArray dataArray = obj.getJSONArray("count");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    // arrivals_session playerModel = new arrivals_session();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                  absent=dataobj.getString("absent");
                                totals=dataobj.getString("totals");
                                    fromdetails.setText("form1:   Form2:  Form3:   Form4:  Absent:" + absent+"/"+totals);
                                    //  movie.setYear(dataobj.getString("date"));



                                    pieEntries = new ArrayList<>();

                                    //int totalsi=Integer.parseInt(k);
                                    pieEntries.add(new PieEntry(Integer.parseInt(absent), 0));
                                    pieEntries.add(new PieEntry(Integer.parseInt(totals), 1));
                                    pieDataSet = new PieDataSet(pieEntries, "");
                                    pieData = new PieData(pieDataSet);
                                    pieChart.setData(pieData);
                                    pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                                    pieDataSet.setSliceSpace(2f);
                                    pieDataSet.setValueTextColor(Color.WHITE);
                                    pieDataSet.setValueTextSize(10f);
                                    pieDataSet.setSliceSpace(5f);
                                    pieChart.animateX(5000);

                                    //statuscheckArrayList.add(playerModel);

                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hidePDialog();
                    }
                });


        // request queue

        AppController.getInstance().addToRequestQueue(stringRequest);


    }




    }