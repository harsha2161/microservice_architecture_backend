package com.example.inventory.controller;

import com.example.inventory.dto.InventoryDTO;
import com.example.inventory.service.InventotyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/inventory")

public class InventoryController {
    @Autowired
    private InventotyService inventotyService;

    @GetMapping("/getitems")
    public List<InventoryDTO> getItems(){
        return inventotyService.getAllItems();
    }

    @GetMapping("/getitembyid/{itemId}")
    public InventoryDTO getItemById(@PathVariable Integer itemId){
        return inventotyService.getItemById(itemId);
    }

    @PostMapping("/additems")
    public InventoryDTO addItem(@RequestBody InventoryDTO inventoryDTO){
        return inventotyService.saveItem(inventoryDTO);
    }

    @PutMapping("/updateitems")
    public InventoryDTO updateItem(@RequestBody InventoryDTO inventoryDTO){
        return inventotyService.updateItem(inventoryDTO);
    }

    @DeleteMapping("/deleteitems/{itemId}")
    public String deleteItem(@PathVariable int itemId){
        return inventotyService.deleteItem(itemId);
    }
}
