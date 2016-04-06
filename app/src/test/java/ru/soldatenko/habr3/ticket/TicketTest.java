package ru.soldatenko.habr3.ticket;

import org.junit.Before;
import org.junit.Test;

import ru.soldatenko.habr3.BR;

import static android.databinding.Observable.OnPropertyChangedCallback;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TicketTest {

    Ticket ticket;
    OnPropertyChangedCallback callback;

    @Before
    public void setup() {
        ticket = new Ticket();
        callback = mock(OnPropertyChangedCallback.class);
    }

    @Test
    public void testGetNumber() throws Exception {
        ticket.setNumber("A54");
        assertEquals("A54", ticket.getNumber());
    }

    @Test
    public void testSetNumber() throws Exception {
        ticket.addOnPropertyChangedCallback(callback);
        ticket.setNumber("test value");
        verify(callback).onPropertyChanged(ticket, BR.number);
    }

    @Test
    public void testGetPositionInQueue() throws Exception {
        ticket.setPositionInQueue(7);
        assertEquals(7, ticket.getPositionInQueue());
    }

    @Test
    public void testSetPositionInQueue() throws Exception {
        ticket.addOnPropertyChangedCallback(callback);
        ticket.setPositionInQueue(83);
        verify(callback).onPropertyChanged(ticket, BR.positionInQueue);
    }

    @Test
    public void testGetTellerNumber() throws Exception {
        ticket.setTellerNumber("Teller 2");
        assertEquals("Teller 2", ticket.getTellerNumber());
    }

    @Test
    public void testSetTellerNumber() throws Exception {
        ticket.addOnPropertyChangedCallback(callback);
        ticket.setTellerNumber("test teller number");
        verify(callback).onPropertyChanged(ticket, BR.tellerNumber);
    }
}