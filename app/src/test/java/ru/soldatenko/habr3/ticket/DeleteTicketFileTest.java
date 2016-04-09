package ru.soldatenko.habr3.ticket;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteTicketFileTest {

    @Mock
    Context context;

    @Mock
    TicketSubsystem ts;

    @InjectMocks
    DeleteTicketFile task;

    @Test
    public void testDoInBackground_shouldDeleteFile() {
        task.fileName = "test";
        task.doInBackground();
        verify(context).deleteFile("test");
    }

    @Test
    public void testOnPostExecute_shouldUpdateTicketSubsystem() {
        task.onPostExecute(null);
        verify(ts).setTicket(null);
    }
}