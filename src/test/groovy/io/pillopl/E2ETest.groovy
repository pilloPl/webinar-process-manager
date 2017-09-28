package io.pillopl

import org.spockframework.runtime.model.BlockInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.test.binder.MessageCollector
import org.springframework.messaging.support.GenericMessage
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.Instant
import java.util.concurrent.BlockingQueue

@SpringBootTest
@ContextConfiguration(classes = [Application], loader = SpringBootContextLoader)
class E2ETest extends Specification {

    @Autowired Channels channels

    @Autowired MessageCollector messageCollector

    BlockingQueue payments
    BlockingQueue shipments
    BlockingQueue communication

    def setup() {
        payments = messageCollector.forChannel(channels.payments())
        shipments = messageCollector.forChannel(channels.shipments())
        communication = messageCollector.forChannel(channels.communication())
    }

    def 'when user is deactivated, then 3 commnands are thrown'() {
        when:
            channels.users().send(new GenericMessage<Object>(new UserDeactivated(Instant.now())))
        then:
            (payments.poll() as String).contains("cancelPayment")
            (shipments.poll() as String).contains("cancelShipment")
            (communication.poll() as String).contains("sendMail")

    }

    def 'when user is activated, no command are thrown'() {
        when:
            channels.users().send(new GenericMessage<Object>(new UserActivated(Instant.now())))
        then:
            payments.isEmpty()
            communication.isEmpty()
            shipments.isEmpty()

    }
}
