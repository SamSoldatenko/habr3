package ru.soldatenko.habr3.ticket;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import ru.soldatenko.habr3.BR;

public class TicketSubsystem extends BaseObservable {
    private Ticket ticket;

    @Bindable
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        notifyPropertyChanged(BR.ticket);
    }
}
