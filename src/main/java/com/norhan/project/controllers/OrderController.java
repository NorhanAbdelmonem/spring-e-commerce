package com.norhan.project.controllers;


import com.norhan.project.common.ErrorDto;
import com.norhan.project.dtos.OrderDto;
import com.norhan.project.orders.OrderNotFoundException;
import com.norhan.project.orders.OrderService;
import com.norhan.project.user.AccessDeniedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
    @RestController
    @RequestMapping("/orders")
    public class OrderController {
        private final OrderService orderService;

        @GetMapping
        public List<OrderDto> getAllOrders() {
            return orderService.getAllOrders();
        }

        @GetMapping("/{orderId}")
        public OrderDto getOrder(@PathVariable("orderId") Long orderId) {
            return orderService.getOrder(orderId);
        }

        @ExceptionHandler(OrderNotFoundException.class)
        public ResponseEntity<Void> handleOrderNotFound() {
            return ResponseEntity.notFound().build();
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorDto> handleAccessDenied(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ErrorDto(ex.getMessage()));
        }

    }
