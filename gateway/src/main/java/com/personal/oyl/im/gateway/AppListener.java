package com.personal.oyl.im.gateway;

import com.personal.oyl.im.gateway.model.SendWrapper;
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

    private WebsocketServer websocketServer;
    private SendWrapper sendWrapper;

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

            sendWrapper.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() ->  {
                sendWrapper.stop();
            }));
        }
    }

    @Autowired
    public void setWebsocketServer(WebsocketServer websocketServer) {
        this.websocketServer = websocketServer;
    }

    @Autowired
    public void setSendWrapper(SendWrapper sendWrapper) {
        this.sendWrapper = sendWrapper;
    }
}
