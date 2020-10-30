package com.personal.oyl.im.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author OuYang Liang
 * @since 2020-10-29
 */
@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent>  {

    @Autowired
    private WebsocketServer websocketServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            new Thread(
                    () -> {
                        try {
                            websocketServer.bind(9080);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
        }
    }
}
