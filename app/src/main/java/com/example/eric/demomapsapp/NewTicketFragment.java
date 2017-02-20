package com.example.eric.demomapsapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eric.demomapsapp.model.Event;
import com.example.eric.demomapsapp.model.Ticket;

import java.util.List;

/**
 * Created by Chrispine on 1/19/2017.
 */

public class NewTicketFragment extends DialogFragment {
    int event_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_ticket, container, false);
        String title = "";
        title = getArguments().getString("title");
        getDialog().setTitle(title);

        return rootView;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.new_ticket, null);
        builder.setView(rootView);
        String buttonText = "Add Ticket";

        Bundle args = getArguments();
        if (!args.getString("ticket_id").isEmpty()){
            buttonText = "Edit Ticket";
            EditText ticketName = (EditText) rootView.findViewById(R.id.ticketName);
            EditText ticketPrice = (EditText) rootView.findViewById(R.id.ticketPrice);
            EditText ticketDescription = (EditText) rootView.findViewById(R.id.ticketDescription);
            TextView ticketID = (TextView) rootView.findViewById(R.id.ticketID);

            DbHelper db = new DbHelper(getActivity());
            Ticket ticket = db.getTicket(Integer.parseInt(args.getString("ticket_id")));

            ticketName.setText(ticket.get_ticket_name());
            ticketPrice.setText(ticket.get_ticket_price());
            ticketDescription.setText(ticket.get_ticket_description());
            ticketID.setText(String.valueOf(ticket.get_id()));
        }

        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbHelper db = new DbHelper(getActivity());
                Event event = db.getEvent();
                event_id = event.getId();

                EditText ticketName = (EditText) rootView.findViewById(R.id.ticketName);
                EditText ticketPrice = (EditText) rootView.findViewById(R.id.ticketPrice);
                EditText ticketDescription = (EditText) rootView.findViewById(R.id.ticketDescription);
                TextView ticketID = (TextView) rootView.findViewById(R.id.ticketID);

                String name = String.valueOf(ticketName.getText());
                String price = String.valueOf(ticketPrice.getText());
                String description = String.valueOf(ticketDescription.getText());
                String id = String.valueOf(ticketID.getText());

                if(!id.isEmpty()){
                    Ticket ticket = new Ticket(Integer.parseInt(id), name, price, description, event_id);
                    db.updateTicket(ticket);
                }else {
                    Ticket ticket = new Ticket(name, price, description, event_id);
                    db.addTicket(ticket);
                }

                List<Ticket> ticketList = db.getAllTickets();
                ((EventTickets)getActivity()).createList(ticketList);
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
}
