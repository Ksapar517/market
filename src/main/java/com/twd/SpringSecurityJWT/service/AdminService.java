package com.twd.SpringSecurityJWT.service;


import com.twd.SpringSecurityJWT.dto.ProductDto;
import com.twd.SpringSecurityJWT.entity.Product;
import com.twd.SpringSecurityJWT.entity.Story;
import com.twd.SpringSecurityJWT.repository.ProductRepo;
import com.twd.SpringSecurityJWT.repository.StotyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private StotyRepo stotyRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Transactional
    public ResponseEntity<Object> saveProduct(ProductDto productRequest, String username) {
     try {
        Product productToSave = new Product();
        productToSave.setName(productRequest.getName());
        productToSave.setType(productRequest.getType());
        productToSave.setQuantity(productRequest.getQuantity());
        productToSave.setPrice(productRequest.getPrice());

        Story story = new Story();
        story.setUserName(username);
        story.setProductName(productRequest.getName());
        story.setQuantity(productRequest.getQuantity());
        story.setPrice(productRequest.getPrice());
        story.setDateTime(LocalDateTime.now());
        story.setType("save");

        stotyRepo.save(story);
        productRepo.save(productToSave);
         return ResponseEntity.ok(productToSave);
    }
     catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
     }
    }

    @Transactional
    public ResponseEntity<Object> updateProduct(ProductDto productRequest, String username) {
        try {

            Product productToUpdate = productRepo.findByName(productRequest.getName());
            if (productToUpdate == null) {
                throw new RuntimeException("Product not found");
            }

            String aa = String.valueOf(productRequest.getQuantity());
            String bb = String.valueOf(productRequest.getPrice());
            String cc = String.valueOf(productRequest.getPlace());
            if(aa!= null)
                productToUpdate.setQuantity(productRequest.getQuantity());
            if(bb != null)
                productToUpdate.setPrice(productRequest.getPrice());
            if (cc != null)
                productToUpdate.setPlace(cc);

            Story story = new Story();
            story.setUserName(username);
            story.setProductName(productRequest.getName());
            story.setQuantity(productRequest.getQuantity());
            story.setPrice(productRequest.getPrice());
            story.setDateTime(LocalDateTime.now());
            story.setType("update");

            productRepo.save(productToUpdate);
            stotyRepo.save(story);
            return ResponseEntity.ok(productToUpdate);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
