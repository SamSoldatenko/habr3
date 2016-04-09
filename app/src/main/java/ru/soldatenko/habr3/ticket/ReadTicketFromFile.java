package ru.soldatenko.habr3.ticket;

import android.content.Context;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.inject.Inject;

import roboguice.inject.InjectResource;
import ru.soldatenko.habr3.LogInterface;
import ru.soldatenko.habr3.R;

public class ReadTicketFromFile extends AsyncTask<Void, Void, Void> {
    @Inject
    LogInterface log;

    @Inject
    Context context;

    @InjectResource(R.string.ticketFileName)
    String fileName;

    @Inject
    TicketSubsystem ticketSubsystem;

    String number;

    int positionInQueue;

    String tellerNumber;

    Exception exception;

    boolean fileExists;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            loadTicketFromFile();
            fileExists = true;
        } catch (FileNotFoundException e) {
            fileExists = false;
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    private void loadTicketFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(context.openFileInput(fileName));
        try {
            number = (String) in.readObject();
            positionInQueue = in.readInt();
            tellerNumber = (String) in.readObject();
        } finally {
            in.close();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (!fileExists) {
            return;
        }
        if (exception != null) {
            log.e(exception, "Error reading ticket from file");
        }
        Ticket ticket = new Ticket();
        ticket.setNumber(number);
        ticket.setPositionInQueue(positionInQueue);
        ticket.setTellerNumber(tellerNumber);
        ticketSubsystem.setTicket(ticket);
    }
}
