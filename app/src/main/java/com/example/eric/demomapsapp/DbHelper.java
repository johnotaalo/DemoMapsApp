package com.example.eric.demomapsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.eric.demomapsapp.model.Event;
import com.example.eric.demomapsapp.model.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eric on 9/25/2016.
 */

public class DbHelper extends SQLiteOpenHelper{
    public static final String TAG = DbHelper.class.getSimpleName();
    public static final String DB_NAME = "demomapsapp.db";
<<<<<<< Updated upstream
    public static final int DB_VERSION = 3;
=======
    public static final int DB_VERSION = 7;
>>>>>>> Stashed changes

    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "emailaddress";
    //public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_UID = "uniqueID";
    public static final String COLUMN_CREATEDAT = "created_at";


    private static final String TABLE_TICKET = "ticket";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "ticket_name";
    private static final String KEY_PRICE = "ticket_price";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_EVENT_ID = "event_id";

    private static final String TABLE_EVENT = "event";
    private static final String EVENT_ID = "id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_DESCRIPTION = "event_description";
    private static final String EVENT_LOC_LAT = "event_loc_lat";
    private static final String EVENT_LOC_LNG = "event_loc_lng";
    private static final String EVENT_START_TIME  = "event_start_time";
    private static final String EVENT_END_TIME = "event_end_time";
    private static final String EVENT_IMAGE = "event_image";

    private static final String TABLE_EVENTS = "events";
    private static final String EVENT_ID = "eventID";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_DESCRIPTION = "event";
    private static final String EVENT_DATEFROM = "date_from";
    private static final String EVENT_DATETO = "date_to";
    private static final String EVENT_TIMEFROM = "time_from";
    private static final String EVENT_TIMETO = "time_to";
    private static final String EVENT_LOCATION = "location";
    private static final String EVENT_LATITUDE = "lat";
    private static final String EVENT_LONGITUDE = "lng";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_UID + " TEXT "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT); ";
      //      + COLUMN_PASSWORD + " TEXT); ";

    public static final String CREATE_TABLE_TICKET = "CREATE TABLE " + TABLE_TICKET + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_PRICE + " TEXT, "
            + KEY_EVENT_ID + " INTEGER"
            + ");";

    public static final String CREATE_TABLE_EVENT = "CREATE TABLE " + TABLE_EVENT + " ( "
            + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EVENT_NAME + " TEXT,"
            + EVENT_DESCRIPTION + " TEXT,"
            + EVENT_LOC_LAT + " TEXT, "
            + EVENT_LOC_LNG + " TEXT, "
            + EVENT_START_TIME + " TEXT,"
            + EVENT_END_TIME + " TEXT,"
            + EVENT_IMAGE + " TEXT"
            + ");";

    public static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + " ( "
            + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EVENT_NAME + " TEXT, "
            + EVENT_DESCRIPTION + " TEXT, "
            + EVENT_DATEFROM + " DATE, "
            + EVENT_DATETO + " DATE, "
            + EVENT_TIMEFROM + " TIME, "
            + EVENT_TIMETO + " TIME, "
            + EVENT_LOCATION + " TEXT, "
            + EVENT_LATITUDE + " FLOAT, "
            + EVENT_LONGITUDE + " FLOAT); ";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_TICKET);
<<<<<<< Updated upstream
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENTS);
=======
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENT);
>>>>>>> Stashed changes
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET);
<<<<<<< Updated upstream
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
=======
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
>>>>>>> Stashed changes
        onCreate(sqLiteDatabase);
    }

    public void addUser(String username, String email, String uuid, String created_at){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_UID, uuid);
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_CREATEDAT, created_at);

        long id = sqLiteDatabase.insert(USER_TABLE, null, contentValues);
        sqLiteDatabase.close();

        Log.d(TAG, "user inserted" + id);
    }

    //this method fetches users from sqlite db
    public HashMap<String, String>getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("uniqueID", cursor.getString(1));
            user.put("username", cursor.getString(2));
            user.put("emailaddress", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        //return user
        Log.d(TAG, "Fetching from sqlite" + user.toString());
        return user;
    }

    /*
    public boolean getUser(String email, String uuid){
        String selectQuery = "select * from " + USER_TABLE + " where " + COLUMN_USERNAME + " = " + " '"
                + username + "' " + " and " + COLUMN_PASSWORD + " = " + " '" + pass + "' ;";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            return true;
        }
        cursor.close();
        sqLiteDatabase.close();

        return false;
    }*/

    // TICKET TABLE
    public void addTicket(Ticket ticket){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ticket.get_ticket_name());
        values.put(KEY_PRICE, ticket.get_ticket_price());
        values.put(KEY_DESCRIPTION, ticket.get_ticket_description());
        values.put(KEY_EVENT_ID, ticket.get_event_id());

        db.insert(TABLE_TICKET, null, values);
        db.close();
    }

    public Ticket getTicket(int id){
        Ticket ticket = null;
        Cursor cursor = null;
        SQLiteDatabase db = null;

        System.out.println("ID: " + id);

        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_TICKET, new String[]{KEY_ID, KEY_NAME, KEY_PRICE, KEY_DESCRIPTION, KEY_EVENT_ID}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            ticket = new Ticket(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
        }
        catch(final Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
            db.close();
        }
        return ticket;
    }

    public List<Ticket> getAllTickets(){
        List<Ticket> ticketList = new ArrayList<Ticket>();

        String selectQuery = "SELECT * FROM " + TABLE_TICKET;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Ticket ticket = new Ticket();
                ticket.set_id(Integer.parseInt(cursor.getString(0)));
                ticket.set_ticket_name(cursor.getString(1));
                ticket.set_ticket_price(cursor.getString(3));
                ticket.set_ticket_description(cursor.getString(2));
                ticket.set_event_id(Integer.parseInt(cursor.getString(4)));

                ticketList.add(ticket);
            }while (cursor.moveToNext());
        }

        return ticketList;
    }


    public JSONArray getTicketsByEvent(int event_id){
        JSONArray tickets = new JSONArray();
        String selectQuery = "SELECT * FROM " + TABLE_TICKET + " WHERE event_id = " + event_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("ticket_name", cursor.getString(1));
                    jsonObject.put("ticket_price", cursor.getString(3));
                    jsonObject.put("ticket_description", cursor.getString(2));
                    tickets.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }

        return tickets;
    }

    public int getTicketCount(){
        String countQuery = "SELECT * FROM " + TABLE_TICKET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        return cursor.getCount();
    }

    public int updateTicket(Ticket ticket){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ticket.get_ticket_name());
        values.put(KEY_PRICE, ticket.get_ticket_price());
        values.put(KEY_DESCRIPTION, ticket.get_ticket_description());
        values.put(KEY_EVENT_ID, ticket.get_event_id());

        return db.update(TABLE_TICKET, values, KEY_ID + " = ?", new String[]{String.valueOf(ticket.get_id())});
    }

    public void deleteTicket(Ticket ticket){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TICKET, KEY_ID + " = ?",
                new String[] { String.valueOf(ticket.get_id()) });
        db.close();
    }

<<<<<<< Updated upstream
    public void addEvent(String eventName, String description, String eventLocation,
                         String eventLat, String eventLng,  String dateFrom,
                         String dateTo, String timeFrom, String timeTo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, eventName);
        values.put(EVENT_DESCRIPTION, description);
        values.put(EVENT_LOCATION, eventLocation);
        values.put(EVENT_LATITUDE, eventLat);
        values.put(EVENT_LONGITUDE, eventLng);
        values.put(EVENT_DATEFROM, dateFrom);
        values.put(EVENT_DATETO, dateTo);
        values.put(EVENT_TIMEFROM, timeFrom);
        values.put(EVENT_TIMETO, timeTo);

        db.insert(TABLE_EVENTS, null, values);
=======
    public void deleteTicketsByEventID(int event_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TICKET, KEY_EVENT_ID + " = ?",
                new String[] { String.valueOf(event_id) });
        db.close();
    }

    //  EVENTS TABLE
    public int addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EVENT_NAME, event.getEvent_name());
        values.put(EVENT_DESCRIPTION, event.getEvent_description());
        values.put(EVENT_LOC_LAT, event.getEvent_loc_lat());
        values.put(EVENT_LOC_LNG, event.getEvent_loc_lng());
        values.put(EVENT_START_TIME, event.getEvent_start_time());
        values.put(EVENT_END_TIME, event.getEvent_end_time());
        values.put(EVENT_IMAGE, event.getEvent_image());

        int id = Integer.parseInt(String.valueOf(db.insert(TABLE_EVENT, null ,values)));
        return id;
    }

    public Event getEvent(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EVENT, new String[]{EVENT_ID, EVENT_NAME, EVENT_DESCRIPTION, EVENT_LOC_LAT, EVENT_LOC_LNG, EVENT_START_TIME, EVENT_END_TIME, EVENT_IMAGE}, EVENT_ID + "=?", new String[]{String.valueOf(id)}, null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Event event = new Event();

        event.setId(Integer.parseInt(cursor.getString(0)));
        event.setEvent_name(cursor.getString(1));
        event.setEvent_description(cursor.getString(2));
        event.setEvent_loc_lat(cursor.getString(3));
        event.setEvent_loc_lng(cursor.getString(4));
        event.setEvent_start_time(cursor.getString(5));
        event.setEvent_end_time(cursor.getString(6));
        event.setEvent_image(cursor.getString(7));

        return event;
    }

    public Event getEvent(){

        String selectQuery = "SELECT * FROM " + TABLE_EVENT;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        Event event = new Event();
        if(cursor.moveToFirst()){
            event.setId(Integer.parseInt(cursor.getString(0)));
            event.setEvent_name(cursor.getString(1));
            event.setEvent_description(cursor.getString(2));
            event.setEvent_loc_lat(cursor.getString(3));
            event.setEvent_loc_lng(cursor.getString(4));
            event.setEvent_start_time(cursor.getString(5));
            event.setEvent_end_time(cursor.getString(6));
            event.setEvent_image(cursor.getString(7));
        }
        return event;
    }

    public int getEventCount(){
        String countQuery = "SELECT * FROM " + TABLE_EVENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        return cursor.getCount();
    }

    public int updateEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EVENT_NAME, event.getEvent_name());
        values.put(EVENT_DESCRIPTION, event.getEvent_description());
        values.put(EVENT_LOC_LAT, event.getEvent_loc_lat());
        values.put(EVENT_LOC_LNG, event.getEvent_loc_lng());
        values.put(EVENT_START_TIME, event.getEvent_start_time());
        values.put(EVENT_END_TIME, event.getEvent_end_time());
        values.put(EVENT_IMAGE, event.getEvent_image());

        return db.update(TABLE_EVENT, values, EVENT_ID + " = ?", new String[]{String.valueOf(event.getId())});
    }

    public void deleteEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENT, EVENT_ID + " = ?", new String[]{String.valueOf(event.getId())});
>>>>>>> Stashed changes
        db.close();
    }
}
