package ru.soldatenko.habr3;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import javax.inject.Provider;

import roboguice.activity.RoboActionBarActivity;
import ru.soldatenko.habr3.databinding.ActivityTicketBinding;
import ru.soldatenko.habr3.ticket.GetNewTicket;
import ru.soldatenko.habr3.ticket.TicketSubsystem;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(MockitoJUnitRunner.class)
@PrepareForTest({RoboActionBarActivity.class, DataBindingUtil.class})
public class TicketActivityTest {
    @Mock
    TicketSubsystem ts;

    @Mock
    Provider<GetNewTicket> getNewTicketProvider;

    @Mock
    ActivityTicketBinding binding;

    @InjectMocks
    TicketActivity activity;

    @Test
    public void onCreate_ShouldSetupTicketSubsystem() throws Exception {
        suppress(RoboActionBarActivity.class.getDeclaredMethod("onCreate", Bundle.class));
        mockStatic(DataBindingUtil.class);
        when(DataBindingUtil.setContentView(any(TicketActivity.class), anyInt())).thenReturn(binding);
        activity.onCreate(null);
        verify(activity.binding).setTs(ts);
    }

    @Test
    public void onDestroy_ShouldUnbind() throws Exception {
        suppress(RoboActionBarActivity.class.getDeclaredMethod("onDestroy"));
        activity.onDestroy();
        verify(binding).unbind();
    }

    @Test
    public void onCreateTicketClick_ShouldStartNewTicketTask() {
        GetNewTicket task = mock(GetNewTicket.class);
        when(getNewTicketProvider.get()).thenReturn(task);
        activity.onCreateTicketClick(mock(Button.class));
        verify(getNewTicketProvider).get();
        verify(task).execute();
    }

    @Test
    public void testOnRemoveTicketClick() {
        activity.onRemoveTicketClick(mock(Button.class));
        verify(ts).setTicket(null);
    }
}