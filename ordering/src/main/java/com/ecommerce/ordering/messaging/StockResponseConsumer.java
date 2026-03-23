package com.ecommerce.ordering.messaging;

import com.ecommerce.ordering.event.StockReservationFailedEvent;
import com.ecommerce.ordering.event.StockReservedEvent;
import com.ecommerce.ordering.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class StockResponseConsumer {

    @Autowired
    private OrderService orderService;

    @Bean
    public Consumer<StockReservedEvent> handleStockReserved() {
        return event -> {
            log.info("Stock reserved for order: {}. Confirming order.", event.getOrderId());
            orderService.confirmOrder(event.getOrderId());
        };
    }

    @Bean
    public Consumer<StockReservationFailedEvent> handleStockReservationFailed() {
        return event -> {
            log.error("Stock reservation failed for order: {}. Reason: {}. Cancelling order.", 
                    event.getOrderId(), event.getReason());
            orderService.cancelOrder(event.getOrderId());
        };
    }
}
