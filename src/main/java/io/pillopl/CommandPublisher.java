package io.pillopl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Publisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandPublisher {

    @Publisher(channel = "payments")
    public String sendCmdToPayment(String cmd) {
        log.info("about to send: " + cmd);
        return cmd;

    }

    @Publisher(channel = "shipments")
    public String sendCmdToShipments(String cmd) {
        log.info("about to send: " + cmd);
        return cmd;

    }

    @Publisher(channel = "communication")
    public String sendCmdToCommunication(String cmd) {
        log.info("about to send: " + cmd);
        return cmd;

    }


}
