package ru.soldatenko.habr3.ticket;

import android.content.Context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import ru.soldatenko.habr3.LogInterface;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadTicketFromFileTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Captor
    ArgumentCaptor<Ticket> ticketCaptor;

    @Mock
    LogInterface log;

    @Mock(answer = Answers.RETURNS_MOCKS)
    Context context;

    @Mock
    TicketSubsystem ts;

    @InjectMocks
    ReadTicketFromFile task;

    @Test
    public void testDoInBackground_shouldHandleFileNotFound() throws Exception {
        doThrow(new FileNotFoundException("test exception")).when(context).openFileInput(anyString());
        task.doInBackground();
        assertFalse(task.fileExists);
        assertNull(task.exception);
    }

    @Test
    public void testDoInBackground_shouldReadFile() throws Exception {
        File file = tempFolder.newFile("test-serialized-ticket.bin");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject("T32");
        out.writeInt(5);
        out.writeObject(null);
        out.close();
        when(context.openFileInput(anyString())).thenReturn(new FileInputStream(file));
        task.doInBackground();
        assertEquals("T32", task.number);
        assertEquals(5, task.positionInQueue);
        assertEquals(null, task.tellerNumber);
        assertTrue(task.fileExists);
    }

    @Test
    public void testDoInBackground_shouldRememberException() throws Exception {
        IOException testException = new IOException("test io exception");
        FileInputStream stream = mock(FileInputStream.class, new ThrowsException(testException));
        when(context.openFileInput(anyString())).thenReturn(stream);
        task.doInBackground();
        assertSame(task.exception, testException);
    }

    @Test
    public void testOnPostExecute_shouldSkipFileNotFound() {
        task.fileExists = false;
        task.onPostExecute(null);
        verifyNoMoreInteractions(log, context, ts);
    }

    @Test
    public void testOnPostExecute_shouldReportException() {
        task.exception = new IOException("Test exception");
        task.fileExists = true;
        task.onPostExecute(null);
        verify(log).e(argThat(is(task.exception)), anyString());
    }

    @Test
    public void testOnPostExecute_shouldSetTicket() {
        task.fileExists = true;
        task.number = "T67";
        task.positionInQueue = 209;
        task.tellerNumber = "test number";
        task.exception = null;
        task.onPostExecute(null);
        verify(ts).setTicket(ticketCaptor.capture());
        Ticket ticket = ticketCaptor.getValue();
        assertEquals("T67", ticket.getNumber());
        assertEquals(209, ticket.getPositionInQueue());
        assertEquals("test number", ticket.getTellerNumber());
    }
}