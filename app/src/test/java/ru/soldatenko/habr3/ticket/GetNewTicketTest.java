package ru.soldatenko.habr3.ticket;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileOutputStream;
import java.io.IOException;

import ru.soldatenko.habr3.LogInterface;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetNewTicketTest {
    @Mock
    LogInterface log;

    @Mock(answer = Answers.RETURNS_MOCKS)
    Context context;

    @Mock
    TicketSubsystem ticketSubsystem;

    @Captor
    ArgumentCaptor<Ticket> ticketCaptor;

    @InjectMocks
    GetNewTicket task;

    @Test
    public void testDoInBackground_shouldCreateTicketData() throws Exception {
        task.doInBackground();
        assertNotNull(task.number);
    }

    @Test
    public void testDoInBackground_shouldHandleIOException() throws Exception {
        IOException testException = new IOException("Test exception");
        FileOutputStream out = mock(FileOutputStream.class, new ThrowsException(testException));
        when(context.openFileOutput(anyString(), anyInt())).thenReturn(out);
        task.doInBackground();
        assertSame(testException, task.exception);
    }

    @Test
    public void testOnPostExecute_shouldCreateNewTicket() throws Exception {
        task.number = "N01";
        task.positionInQueue = 2;
        task.tellerNumber = "T03";

        task.onPostExecute(null);

        verify(ticketSubsystem).setTicket(ticketCaptor.capture());
        Ticket ticket = ticketCaptor.getValue();
        assertEquals("N01", ticket.getNumber());
        assertEquals(2, ticket.getPositionInQueue());
        assertEquals("T03", ticket.getTellerNumber());
    }

    @Test
    public void testOnPostExecute_shouldReportException() {
        Exception testException = new Exception("Test Exception");
        task.exception = testException;
        task.onPostExecute(null);
        verify(log).e(argThat(is(testException)), anyString());
    }
}