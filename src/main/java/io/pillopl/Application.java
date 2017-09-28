package io.pillopl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@SpringBootApplication
@EnableBinding(Channels.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {

    private final CommandPublisher commandPublisher;

    public Application(CommandPublisher commandPublisher) {
        this.commandPublisher = commandPublisher;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }

    @StreamListener("users")
    public void handleEvent(DomainEvent event) {
        log.info("received: " + event);

        if (event instanceof UserDeactivated) {
            commandPublisher.sendCmdToPayment("cancelPayment");
            commandPublisher.sendCmdToShipments("cancelShipment");
            commandPublisher.sendCmdToCommunication("sendMail");

        }
    }

}
