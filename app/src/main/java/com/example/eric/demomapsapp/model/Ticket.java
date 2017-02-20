package com.example.eric.demomapsapp.model;

/**
 * Created by Chrispine on 1/19/2017.
 */

public class Ticket {
    private int _id;
    private String _ticket_name;
    private String _ticket_price;
    private String _ticket_description;
    private int _event_id;

    public Ticket(){

    }

    public Ticket(int id, String ticket_name, String ticket_price, String ticket_description, int event_id){
        this._id = id;
        this._ticket_name = ticket_name;
        this._ticket_price = ticket_price;
        this._ticket_description = ticket_description;
        this._event_id = event_id;
    }

    public Ticket(String ticket_name, String ticket_price, String ticket_description, int event_id){
        this._ticket_name = ticket_name;
        this._ticket_price = ticket_price;
        this._ticket_description = ticket_description;
        this._event_id = event_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_ticket_name() {
        return _ticket_name;
    }

    public void set_ticket_name(String _ticket_name) {
        this._ticket_name = _ticket_name;
    }

    public String get_ticket_price() {
        return _ticket_price;
    }

    public void set_ticket_price(String _ticket_price) {
        this._ticket_price = _ticket_price;
    }

    public String get_ticket_description() {
        return _ticket_description;
    }

    public void set_ticket_description(String _ticket_description) {
        this._ticket_description = _ticket_description;
    }

    public int get_event_id() {
        return _event_id;
    }

    public void set_event_id(int _event_id) {
        this._event_id = _event_id;
    }
}
