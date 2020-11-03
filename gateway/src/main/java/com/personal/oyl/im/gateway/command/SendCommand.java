package com.personal.oyl.im.gateway.command;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author OuYang Liang
 * @since 2020-11-02
 */
public class SendCommand implements Command {

    private String key;
    private Channel channel;
    private String content;

    private long time;

    public SendCommand(String key) {
        this.key = key;
    }

    public SendCommand(String key, Channel channel, String content, long delay, TimeUnit unit) {
        this.key = key;
        this.channel = channel;
        this.content = content;
        this.time = System.currentTimeMillis() + (delay > 0 ? unit.toMillis(delay) : 0);
    }

    @Override
    public void execute() {
        if (channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(content));
        }
    }

    @Override
    public Command newCommand() {
        return new SendCommand(this.key, this.channel, this.content, Command.delay, Command.unit);
    }

    public long getTime() {
        return time;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return  time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return this.getDelay(TimeUnit.SECONDS) <= o.getDelay(TimeUnit.SECONDS) ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SendCommand that = (SendCommand) o;

        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    /*public static void main(String[] args) {
        DelayQueue<SendCommand> queue = new DelayQueue<>();

        SendCommand c1 = new SendCommand("0", null, "c1", 15, TimeUnit.SECONDS);
        SendCommand c2 = new SendCommand("1", null, "c2", 10, TimeUnit.SECONDS);
        System.out.println(System.currentTimeMillis());
        queue.offer(c1);
        queue.offer(c2);

        SendCommand cz = new SendCommand("1", null, "22123321", 10, TimeUnit.SECONDS);

        try {
            //queue.remove(cz);
            SendCommand c3= queue.take();
            System.out.println(System.currentTimeMillis());
            SendCommand c4= queue.take();
            System.out.println(System.currentTimeMillis());
            System.out.println(c3.content);
            System.out.println(c4.content);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/
}
