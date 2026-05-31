package com.example.order.service;

import com.example.inventory.dto.InventoryDTO;
import com.example.order.common.ErrorOrderResponce;
import com.example.order.common.OrderResponse;
import com.example.order.common.SuccessOrderResponse;
import com.example.order.dto.OrderDTO;
import com.example.order.model.Order;
import com.example.order.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional

public class OrderService {

    private final WebClient webClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<OrderDTO> getAllOrders(){
        List<Order>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse createOrder(OrderDTO orderDTO){

        Integer itemId = orderDTO.getItemId();

        try{
           InventoryDTO inventoryResponse =  webClient.get()
                    //.uri(uriBuilder -> uriBuilder.path("http://localhost:8080/getitembyid/{itemId}").build(itemId)) //The words that are in the lines work for the lines that are in the lines.
                   .uri("http://localhost:8080/api/v1/getitembyid/{itemId}", itemId)
                   .retrieve() //get http response
                    .bodyToMono(InventoryDTO.class) // mention return type using Mono
                                                    // use .class  to understand and sent data convert signal JSON to java
                                                    // ParameterizedTypeReference --> use DTO list
                                                    //
                    .block(); // use returns by bodyToMono

           // assert inventoryResponse != null; // some time can issue. this line miss compile sometimes. therefor use if else statement or java utile.object
            if(inventoryResponse == null){
                return new ErrorOrderResponce("item not found");
            }

            if(inventoryResponse.getQuantity() > 0){
                orderRepo.save(modelMapper.map(orderDTO, Order.class));
                return new SuccessOrderResponse(orderDTO);
            }else {
                return new ErrorOrderResponce("Item not available. place buy later");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        orderRepo.save(modelMapper.map(OrderDTO, Order.class));
        return OrderDTO;
    }








}
