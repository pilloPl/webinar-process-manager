package io.pillopl;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    @Input("users")
    SubscribableChannel users();

    @Output("payments")
    MessageChannel payments();

    @Output("shipments")
    MessageChannel shipments();

    @Output("communication")
    MessageChannel communication();



}
