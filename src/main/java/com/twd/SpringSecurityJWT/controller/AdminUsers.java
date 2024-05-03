package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ProductDto;
import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.Product;
import com.twd.SpringSecurityJWT.entity.Story;
import com.twd.SpringSecurityJWT.repository.ProductRepo;
import com.twd.SpringSecurityJWT.repository.StotyRepo;
import com.twd.SpringSecurityJWT.service.AdminService;
import com.twd.SpringSecurityJWT.service.AdminUsersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminUsers {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private StotyRepo stotyRepo;
   @Autowired
   private AdminUsersService adminUsersService;
   @Autowired
   private AdminService adminService;

    @PostMapping("/admin/saveproduct")
    public ResponseEntity<Object> signUp(@RequestBody ProductDto productRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return ResponseEntity.ok(adminService.saveProduct(productRequest, username));
    }


    @PostMapping("/admin/updateproduct")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return ResponseEntity.ok(adminService.updateProduct(productRequest, username));
    }


    @PostMapping("/adminuser/sold")
    @Transactional
    public ResponseEntity<Object> sold(@RequestBody List<ProductDto> productRequests) {
        try {

            List<Product> updatedProducts = new ArrayList<>();
            for (ProductDto productRequest : productRequests) {
                // Check if the product exists
                Product productToSave = productRepo.findByName(productRequest.getName());
                if (productToSave == null) {
                    // Product not found, return 404 Not Found status for this product
                    return ResponseEntity.notFound().build();
                }
                // Update the product details
                if (productToSave.getQuantity() >= productRequest.getQuantity()) {
                    productToSave.setQuantity((productToSave.getQuantity()) - (productRequest.getQuantity()));

                } else {
                    throw new RuntimeException("Not enough stock");
                }
                // Save the updated product
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = userDetails.getUsername();
                System.out.println("User: " + username);

                Story story = new Story();
                story.setUserName(username);
                story.setProductName(productRequest.getName());
                story.setQuantity(productRequest.getQuantity());
                story.setPrice(productRequest.getPrice());
                story.setDateTime(LocalDateTime.now());
                story.setType("sold");
                System.out.println(story);
                updatedProducts.add(productRepo.save(productToSave));
                stotyRepo.save(story);
            }
            // Return the list of updated products
            return ResponseEntity.ok(updatedProducts);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/admin/allproducts")
    public ResponseEntity<Object> allProducts() {
        List<Product> allProducts = productRepo.findAll();
        return ResponseEntity.ok(allProducts);

    }

    @PostMapping("/adminuser/infoproduct")
    public ResponseEntity<Object> infoProduct(@RequestBody ProductDto productRequest) {
        Product productToSave = productRepo.findByName(productRequest.getName());
        if (productToSave == null) {
            // Product not found, return 404 Not Found status
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productToSave);
    }


    @PostMapping("/admin/addproduct")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productRequest) {
        // Check if the product exists
        Product productToSave = productRepo.findByName(productRequest.getName());
        if (productToSave == null) {
            // Product not found, return 404 Not Found status
            return ResponseEntity.notFound().build();
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("User: " + username);
        Story story = new Story();
        story.setUserName(username);
        story.setProductName(productRequest.getName());
        story.setQuantity(productRequest.getQuantity());
        story.setPrice(productRequest.getPrice());
        story.setDateTime(LocalDateTime.now());
        story.setType("add");

        System.out.println(story);

        productToSave.setQuantity((productRequest.getQuantity())+(productRequest.getQuantity()));
        Product updatedProduct = productRepo.save(productToSave);
        stotyRepo.save(story);
        return ResponseEntity.ok(updatedProduct);

    }












    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminaAndUsersApi(){
        return ResponseEntity.ok("Both Admin and Users Can  access the api");
    }

}
