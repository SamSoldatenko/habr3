package ru.soldatenko.habr3.ticket;

import android.databinding.Observable.OnPropertyChangedCallback;

import org.junit.Before;
import org.junit.Test;

import ru.soldatenko.habr3.BR;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TicketSubsystemTest {
    TicketSubsystem subsystem;
    OnPropertyChangedCallback callback;

    @Before
    public void setup() {
        subsystem = new TicketSubsystem();
        callback = mock(OnPropertyChangedCallback.class);
    }

    @Test
    public void testGetTicket() {
        Ticket ticket = mock(Ticket.class);
        subsystem.setTicket(ticket);
        assertSame(ticket, subsystem.getTicket());
    }

    @Test
    public void testSetTicket() {
        subsystem.addOnPropertyChangedCallback(callback);
        subsystem.setTicket(mock(Ticket.class));
        verify(callback).onPropertyChanged(subsystem, BR.ticket);
    }
}