package com.example.eric.demomapsapp;

import android.app.FragmentManager;
<<<<<<< Updated upstream
=======
import android.content.DialogInterface;
>>>>>>> Stashed changes
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eric.demomapsapp.adapter.TicketAdapater;
import com.example.eric.demomapsapp.model.Event;
import com.example.eric.demomapsapp.model.Ticket;

import java.util.List;

public class EventTickets extends AppCompatActivity {
    DbHelper db = new DbHelper(this);
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_tickets);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_event_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Event Tickets");

        listView = (ListView) findViewById(R.id.ticketList);

        List<Ticket> ticketList = db.getAllTickets();
        createList(ticketList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView txtTicketID = (TextView) view.findViewById(R.id.item_ticket_id);
                final int ticket_id = Integer.parseInt(txtTicketID.getText().toString());

                final Ticket ticket = db.getTicket(ticket_id);

                PopupMenu popup = new PopupMenu(EventTickets.this, txtTicketID);
                popup.getMenuInflater().inflate(R.menu.event_popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        switch (id){
                            case R.id.popup_delete:
                                new AlertDialog.Builder(EventTickets.this)
                                        .setTitle("Delete Confirmation")
                                        .setMessage("Are you sure you want to delete this Ticket?")
                                        .setIcon(R.drawable.ic_action_name)
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                db.deleteTicket(ticket);
                                                List<Ticket> ticketList = db.getAllTickets();
                                                createList(ticketList);
                                            }
                                        })
                                .setNegativeButton("No", null)
                                .show();
                                break;
                            case R.id.popup_edit:
                                FragmentManager fm = getFragmentManager();
                                NewTicketFragment dialogFragment = new NewTicketFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("title", "Edit Ticket: " + ticket.get_ticket_name());
                                bundle.putString("ticket_id", String.valueOf(ticket.get_id()));

                                dialogFragment.setArguments(bundle);
                                dialogFragment.show(fm, "Edit Ticket: " + ticket.get_ticket_name());
                                break;
                        }
                        return true;
                    }
                });

                popup.setGravity(Gravity.CENTER);

                popup.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_tickets_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                FragmentManager fm = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("title", "New Ticket");
                bundle.putString("ticket_id", "");
                NewTicketFragment dialogFragment = new NewTicketFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "New Ticket");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createList(List<Ticket> ticketList){
        TextView textView = (TextView) findViewById(R.id.ticketsCount);

        int ticketCount = db.getTicketCount();
        textView.setText(String.valueOf(ticketCount));
        TicketAdapater ticketAdapater = new TicketAdapater(EventTickets.this, ticketList);
        listView.setAdapter(ticketAdapater);
    }

<<<<<<< Updated upstream
    public void goToQrScanner(View view) {
        Intent intent = new Intent(EventTickets.this, QRticketScanner.class);
=======
    public void completeEventRegistration(View view){
        Intent intent = new Intent(EventTickets.this, CompleteRegistration.class);
>>>>>>> Stashed changes
        startActivity(intent);
    }
}
