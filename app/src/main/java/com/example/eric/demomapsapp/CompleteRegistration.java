package com.example.eric.demomapsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eric.demomapsapp.adapter.TicketAdapater;
import com.example.eric.demomapsapp.model.Event;
import com.example.eric.demomapsapp.model.Ticket;
import com.example.eric.demomapsapp.modules.AppHelper;
import com.example.eric.demomapsapp.modules.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompleteRegistration extends AppCompatActivity {

    DbHelper dbHelper = new DbHelper(this);
    Event _event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.confirmation_action_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Event Tickets");

        final Event event = dbHelper.getEvent();
        _event = dbHelper.getEvent();

        TextView txtEventID = (TextView) findViewById(R.id.event_id);
        TextView txtEventName = (TextView) findViewById(R.id.eventName);
        TextView txtEventDescription = (TextView) findViewById(R.id.eventDescription);
        TextView txtEventStart = (TextView) findViewById(R.id.eventStartDate);
        TextView txtEventEnd = (TextView) findViewById(R.id.eventEndDate);
        TextView txtEventLocation = (TextView) findViewById(R.id.eventLocation);
        ImageView imageView = (ImageView) findViewById(R.id.eventImage);
        TextView editEventDetails = (TextView) findViewById(R.id.changeEventDetails);
        TextView editEventImage = (TextView) findViewById(R.id.changeEventImage);
        TextView editEventTickets = (TextView) findViewById(R.id.changeTickets);

        txtEventID.setText(String.valueOf(event.getId()));
        txtEventName.setText(event.getEvent_name());
        txtEventDescription.setText(event.getEvent_description());
        txtEventStart.setText(event.getEvent_start_time());
        txtEventEnd.setText(event.getEvent_end_time());
        txtEventLocation.setText(event.getEvent_loc_lat() + "," + event.getEvent_loc_lng());
        imageView.setImageBitmap(BitmapFactory.decodeFile(event.getEvent_image()));

        List<Ticket> ticketList = dbHelper.getAllTickets();
        createList(ticketList);

        editEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompleteRegistration.this, PostGig.class);
                startActivity(intent);
            }
        });

        editEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompleteRegistration.this, ImageUpload.class);

                Bundle bundle = new Bundle();
                bundle.putString("eventName", event.getEvent_name());
                bundle.putString("eventDescription", event.getEvent_description());
                bundle.putString("eventId", String.valueOf(event.getId()));
                bundle.putString("eventStart", event.getEvent_start_time());
                bundle.putString("eventEnd", event.getEvent_end_time());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        editEventTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompleteRegistration.this, EventTickets.class);
                startActivity(intent);
            }
        });
    }

    public void createList(List<Ticket> ticketList){
        ListView listView = (ListView) findViewById(R.id.ticketsList);

        int ticketCount = dbHelper.getTicketCount();
        TicketAdapater ticketAdapater = new TicketAdapater(CompleteRegistration.this, ticketList);
        listView.setAdapter(ticketAdapater);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirmation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_delete:
                new AlertDialog.Builder(CompleteRegistration.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you wnat to delete this event?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deleteTicketsByEventID(_event.getId());
                                dbHelper.deleteEvent(_event);
                                Intent intent = new Intent(CompleteRegistration.this, PostGig.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;

            case R.id.action_upload:
                final JSONArray ticketDetails = dbHelper.getTicketsByEvent(_event.getId());
                System.out.println(ticketDetails);
                VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST, Config.base_url + "addEvent.php", new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try {
                            JSONObject result = new JSONObject(resultResponse);
                            System.out.println(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        String errorMessage = "Unknown error";
                        if (networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                errorMessage = "Request timeout";
                            } else if (error.getClass().equals(NoConnectionError.class)) {
                                errorMessage = "Failed to connect server";
                            }
                        } else {
                            String result = new String(networkResponse.data);
                            try {
                                JSONObject response = new JSONObject(result);
                                String status = response.getString("status");
                                String message = response.getString("message");

                                Log.e("Error Status", status);
                                Log.e("Error Message", message);

                                if (networkResponse.statusCode == 404) {
                                    errorMessage = "Resource not found";
                                } else if (networkResponse.statusCode == 401) {
                                    errorMessage = message+" Please login again";
                                } else if (networkResponse.statusCode == 400) {
                                    errorMessage = message+ " Check your inputs";
                                } else if (networkResponse.statusCode == 500) {
                                    errorMessage = message+" Something is getting wrong";
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("Error", errorMessage);
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("eventName", _event.getEvent_name());
                        params.put("eventDescription", _event.getEvent_description());
                        params.put("eventLocLat", _event.getEvent_loc_lat());
                        params.put("eventLocLng", _event.getEvent_loc_lng());
                        params.put("eventStartTime", _event.getEvent_start_time());
                        params.put("eventEndTime", _event.getEvent_end_time());
                        params.put("tickets", ticketDetails.toString());
                        return params;
                    }
                    @Override
                    protected Map<String, DataPart> getByteData(){
                        Map<String, DataPart> params = new HashMap<>();

                        Drawable d = Drawable.createFromPath(_event.getEvent_image());
                        String mimeType = Config.getMimeType(_event.getEvent_image());
                        System.out.println(mimeType);
                        params.put("event_image", new DataPart(_event.getEvent_image(), AppHelper.getFileDataFromDrawable(getBaseContext(), d), mimeType));
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
