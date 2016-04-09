package ru.soldatenko.habr3.ticket;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.os.Handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Provider;

import ru.soldatenko.habr3.BR;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketSubsystemTest {
    @InjectMocks
    TicketSubsystem subsystem;

    @Mock
    OnPropertyChangedCallback callback;

    @Mock
    Provider<ReadTicketFromFile> taskProvider;

    @Mock
    Handler handler;

    @Captor
    private ArgumentCaptor<Runnable> runnableCaptor;

    @Test
    public void testConstructor_shouldSetupInitialization() {
        verify(handler).post(runnableCaptor.capture());
    }

    @Test
    public void testConstructor_shouldStartReadTaskWhenInitialized() {
        ReadTicketFromFile task = mock(ReadTicketFromFile.class);
        when(taskProvider.get()).thenReturn(task);
        verify(handler).post(runnableCaptor.capture());
        Runnable initializationCode = runnableCaptor.getValue();
        initializationCode.run();
        verify(task).execute();
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