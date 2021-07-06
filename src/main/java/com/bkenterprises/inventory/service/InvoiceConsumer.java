package com.bkenterprises.inventory.service;

import com.bkenterprises.inventory.dao.InventoryRepository;
import com.bkenterprises.inventory.model.Inventory;
import com.bkenterprises.inventory.model.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    public InvoiceConsumer(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    private Invoice generateInvoice(String message) throws JsonProcessingException {
        System.out.println(message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(message, Invoice.class);
    }

    @Transactional
    @SqsListener(value="invoices.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(String message) throws JsonProcessingException {
        Invoice invoice = generateInvoice(message);
        Inventory inventory = new Inventory();
        inventory.setDeliveredOn(LocalDateTime.now());
        inventory.setProductUUID(invoice.getProductUUID());
        inventory.setVendorUUID(invoice.getVendorUUID());
        inventory.setQuantity(invoice.getQuantity());
        inventory.setTotalCost(invoice.getTotalCost());
        inventory.setModifiedOn(LocalDateTime.now());
        inventoryRepository.save(inventory);
    }

}
