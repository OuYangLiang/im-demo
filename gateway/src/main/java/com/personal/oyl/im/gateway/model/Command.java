package com.personal.oyl.im.gateway.model;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author OuYang Liang
 * @since 2020-11-02
 */
public interface Command extends Delayed {
    TimeUnit unit = TimeUnit.SECONDS;
    long delay = 5L;

    void execute();

    Command newCommand();
}
