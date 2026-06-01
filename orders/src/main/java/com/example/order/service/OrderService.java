package com.example.order.service;
import com.example.inventory.dto.InventoryDTO;
import com.example.order.common.ErrorOrderResponce;
import com.example.order.common.OrderResponse;
import com.example.order.common.SuccessOrderResponse;
import com.example.order.dto.OrderDTO;
import com.example.order.model.Order;
import com.example.order.repo.OrderRepo;
import com.example.product.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional

public class OrderService {

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient, WebClient productWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders(){
        List<Order>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse createOrder(OrderDTO orderDTO){

        Integer itemId = orderDTO.getItemId();
        try{
           InventoryDTO inventoryResponse =  inventoryWebClient.get()
                    //.uri(uriBuilder -> uriBuilder.path("http://localhost:8080/getitembyid/{itemId}").build(itemId)) //The words that are in the lines work for the lines that are in the lines.
                   .uri("/getitembyid/{itemId}", itemId)
                   .retrieve() //get http response
                   .bodyToMono(InventoryDTO.class) // mention return type using Mono
                                                    // use .class  to understand and sent data convert signal JSON to java
                                                    // ParameterizedTypeReference --> use DTO list
                                                    //
                   .block(); // use returns by bodyToMono

         //   assert inventoryResponse != null; // some time can issue. this line miss compile sometimes. therefor use if else statement or java utile.object

            if(inventoryResponse == null){
                return new ErrorOrderResponce("item not found");
            }

            Integer productsId = inventoryResponse.getProductId();  //get productDTO using productId
            ProductDTO productResponse = productWebClient.get()
                    .uri("/getproductbyid/{productId}", productsId)
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            if(productResponse == null){
                return new ErrorOrderResponce("product not found");
            }

            //check item available or not and create order
            if(inventoryResponse.getQuantity() > 0){
                if(productResponse.getForSale() == true) {
                    orderRepo.save(modelMapper.map(orderDTO, Order.class));
                    return new SuccessOrderResponse(orderDTO);
                }else {
                    return new ErrorOrderResponce("product not for sell");
                }
                }else {
                    return new ErrorOrderResponce("item out of stoke");
                }

        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        catch (WebClientResponseException e){
            if(e.getStatusCode().is5xxServerError()){
                return new ErrorOrderResponce("item not found");
            }
        }
        return null;
    }

    // update order details
    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        orderRepo.save(modelMapper.map(OrderDTO, Order.class));
        return OrderDTO;
    }

    public String deleteOrder(Integer id){
        orderRepo.deleteById(id);
        return "Order has been deleted";
    }

}
