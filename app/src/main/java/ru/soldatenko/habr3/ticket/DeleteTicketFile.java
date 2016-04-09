package ru.soldatenko.habr3.ticket;

import android.content.Context;
import android.os.AsyncTask;

import javax.inject.Inject;

import roboguice.inject.InjectResource;
import ru.soldatenko.habr3.R;

public class DeleteTicketFile extends AsyncTask<Void, Void, Void> {

    @Inject
    Context context;

    @InjectResource(R.string.ticketFileName)
    String fileName;

    @Inject
    TicketSubsystem ticketSubsystem;

    @Override
    protected Void doInBackground(Void... params) {
        context.deleteFile(fileName);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ticketSubsystem.setTicket(null);
    }
}
