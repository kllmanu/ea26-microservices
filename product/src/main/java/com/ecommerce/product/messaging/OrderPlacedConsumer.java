package com.ecommerce.product.messaging;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.product.domain.ProductId;
import com.ecommerce.product.event.OrderPlacedEvent;
import com.ecommerce.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import com.ecommerce.product.event.StockReservationFailedEvent;
import com.ecommerce.product.event.StockReservedEvent;
import org.springframework.cloud.stream.function.StreamBridge;

@Configuration
@Slf4j
public class OrderPlacedConsumer {

    @Autowired
    private ProductService productService;

    @Autowired
    private StreamBridge streamBridge;

    @Bean
    public Consumer<OrderPlacedEvent> reduceStock() {
        return event -> {
            log.info("Received OrderPlacedEvent for order: {}", event.getOrderId());
            try {
                productService.processOrder(event);
                log.info("Stock reserved successfully for order: {}", event.getOrderId());
                streamBridge.send("stockReserved-out-0", StockReservedEvent.builder()
                        .orderId(event.getOrderId())
                        .build());
            } catch (Exception e) {
                log.error("Failed to reserve stock for order: {}. Error: {}", event.getOrderId(), e.getMessage());
                streamBridge.send("stockReservationFailed-out-0", StockReservationFailedEvent.builder()
                        .orderId(event.getOrderId())
                        .reason(e.getMessage())
                        .build());
            }
        };
    }
}
