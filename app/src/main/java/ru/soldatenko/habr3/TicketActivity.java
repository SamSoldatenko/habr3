package ru.soldatenko.habr3;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;
import javax.inject.Provider;

import roboguice.activity.RoboActionBarActivity;
import ru.soldatenko.habr3.databinding.ActivityTicketBinding;
import ru.soldatenko.habr3.ticket.GetNewTicket;
import ru.soldatenko.habr3.ticket.TicketSubsystem;

public class TicketActivity extends RoboActionBarActivity {
    @Inject
    TicketSubsystem ticketSubsystem;

    @Inject
    Provider<GetNewTicket> createTicketTask;

    ActivityTicketBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket);
        binding.setTs(ticketSubsystem);
    }

    @Override
    protected void onDestroy() {
        binding.unbind();
        super.onDestroy();
    }

    public void onCreateTicketClick(View view) {
        createTicketTask.get().execute();
    }

    public void onRemoveTicketClick(View view) {
        ticketSubsystem.setTicket(null);
    }
}
