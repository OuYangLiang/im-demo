package com.personal.oyl.im.gateway.model;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;

/**
 * @author OuYang Liang
 * @since 2020-11-03
 */
@Component
public class SendWrapperImpl implements SendWrapper {

    private DelayQueue<Command> queue = new DelayQueue<>();
    private Thread thread;

    public SendWrapperImpl() {

    }

    public void send(Channel channel, Protocol protocol) {
        String content = protocol.toJson();
        SendCommand command = new SendCommand(protocol.getMsgId(), channel, content, Command.delay, Command.unit);
        command.execute();
        queue.offer(command);
    }

    public void start() {
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Command command = queue.take();
                    command.execute();
                    queue.offer(command.newCommand());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void stop() {
        if (null != thread) {
            thread.interrupt();
        }
    }

    public void clear(String msgId) {
        this.queue.remove(new SendCommand(msgId));
    }
}
