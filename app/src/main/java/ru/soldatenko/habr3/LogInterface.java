package ru.soldatenko.habr3;

import com.google.inject.ImplementedBy;

import roboguice.util.LnInterface;

@ImplementedBy(LogImpl.class)
public interface LogInterface extends LnInterface {
}

