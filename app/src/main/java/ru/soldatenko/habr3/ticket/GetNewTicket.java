package ru.soldatenko.habr3.ticket;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import roboguice.inject.InjectResource;
import ru.soldatenko.habr3.LogInterface;
import ru.soldatenko.habr3.R;

public class GetNewTicket extends AsyncTask<Void, Void, Void> {

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

    @Override
    protected Void doInBackground(Void... params) {
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(2));

        Random random = new Random();
        number = "A" + random.nextInt(100);
        positionInQueue = random.nextInt(10);
        tellerNumber = null;

        try {
            saveTicketToFile();
        } catch (IOException e) {
            exception = e;
        }
        return null;
    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    private void saveTicketToFile() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
        try {
            out.writeObject(number);
            out.writeInt(positionInQueue);
            out.writeObject(tellerNumber);
        } finally {
            out.close();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (exception != null) {
            log.e(exception, "Failed to get new ticket");
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setNumber(number);
        ticket.setPositionInQueue(positionInQueue);
        ticket.setTellerNumber(tellerNumber);

        ticketSubsystem.setTicket(ticket);
    }
}
