package ru.soldatenko.habr3.ticket;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.UiThread;

import ru.soldatenko.habr3.BR;

@UiThread
public class Ticket extends BaseObservable {
    private String number;
    private int positionInQueue;
    private String tellerNumber;

    public void setNumber(String number) {
        this.number = number;
        notifyPropertyChanged(BR.number);
    }

    @Bindable
    public int getPositionInQueue() {
        return positionInQueue;
    }

    public void setPositionInQueue(int positionInQueue) {
        this.positionInQueue = positionInQueue;
        notifyPropertyChanged(BR.positionInQueue);
    }

    @Bindable
    public String getTellerNumber() {
        return tellerNumber;
    }

    public void setTellerNumber(String tellerNumber) {
        this.tellerNumber = tellerNumber;
        notifyPropertyChanged(BR.tellerNumber);
    }

    @Bindable
    public String getNumber() {
        return number;
    }
}
