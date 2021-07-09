package com.bkenterprises.inventory.service;

import com.bkenterprises.inventory.dao.InventoryRepository;
import com.bkenterprises.inventory.model.Inventory;
import com.bkenterprises.inventory.model.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class InvoiceConsumer {

    private final InventoryRepository inventoryRepository;

    @Autowired
    SecurityService securityService;

    public InvoiceConsumer(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    private Invoice generateInvoice(String message) throws JsonProcessingException {
        System.out.println(message);
//        message = "{\"uuid\": \"3c4954a5-5bac-43a3-a9ca-74049ad21232\",\"productUUID\": \"000100d6bb5c68757a3bdf1e814cddbe\",\"vendorUUID\": \"test123\",\"quantity\": 10,\"rate\": 13.0,\"totalCost\": 130.0}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(message, Invoice.class);
    }

    @Transactional
    @SqsListener(value="invoices.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(String message) throws JsonProcessingException {
        Invoice invoice = generateInvoice(message);
        Inventory inventory = new Inventory();
        inventory.setUUID(securityService.generateUUID());
        inventory.setDeliveredOn(LocalDateTime.now());
        inventory.setProductUUID(invoice.getProductUUID());
        inventory.setVendorUUID(invoice.getVendorUUID());
        inventory.setQuantity(invoice.getQuantity());
        inventory.setTotalCost(invoice.getTotalCost());
        inventory.setModifiedOn(LocalDateTime.now());
        inventoryRepository.save(inventory);
    }

}
