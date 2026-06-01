package com.example.order.controller;

import com.example.order.common.OrderResponse;
import com.example.order.dto.OrderDTO;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getorder")
    public List<OrderDTO> getAllOrder() {
        return orderService.getAllOrders();
    }

    @PostMapping("/createorder")
    public OrderResponse createOrder(@RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/updateorder")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(orderDTO);
    }

    @DeleteMapping("/deleteorder/{id}")
    public String deleteOrder(@PathVariable int id){
        return orderService.deleteOrder(id);
    }

}
