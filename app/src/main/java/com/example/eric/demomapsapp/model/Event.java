package com.example.eric.demomapsapp.model;

/**
 * Created by Chrispine on 2/11/2017.
 */

public class Event {
    private int id;
    private String event_name, event_description, event_loc_lat, event_loc_lng, event_start_time, event_end_time, event_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_loc_lat() {
        return event_loc_lat;
    }

    public void setEvent_loc_lat(String event_loc_lat) {
        this.event_loc_lat = event_loc_lat;
    }

    public String getEvent_loc_lng() {
        return event_loc_lng;
    }

    public void setEvent_loc_lng(String event_loc_lng) {
        this.event_loc_lng = event_loc_lng;
    }

    public String getEvent_start_time() {
        return event_start_time;
    }

    public void setEvent_start_time(String event_start_time) {
        this.event_start_time = event_start_time;
    }

    public String getEvent_end_time() {
        return event_end_time;
    }

    public void setEvent_end_time(String event_end_time) {
        this.event_end_time = event_end_time;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }
}
