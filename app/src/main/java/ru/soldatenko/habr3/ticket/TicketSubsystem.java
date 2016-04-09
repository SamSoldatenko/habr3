package ru.soldatenko.habr3.ticket;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Handler;
import android.support.annotation.UiThread;

import com.google.inject.Inject;

import javax.inject.Provider;
import javax.inject.Singleton;

import ru.soldatenko.habr3.BR;

@Singleton
@UiThread
public class TicketSubsystem extends BaseObservable {
    private Ticket ticket;

    @Inject
    public TicketSubsystem(Handler handler, final Provider<ReadTicketFromFile> task) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                task.get().execute();
            }
        });
    }

    @Bindable
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        notifyPropertyChanged(BR.ticket);
    }
}
