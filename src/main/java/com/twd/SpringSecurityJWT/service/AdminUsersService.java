package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.ProductDto;
import com.twd.SpringSecurityJWT.entity.Product;
import com.twd.SpringSecurityJWT.entity.Story;
import com.twd.SpringSecurityJWT.repository.ProductRepo;
import com.twd.SpringSecurityJWT.repository.StotyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminUsersService {
    private ProductRepo productRepo;
    @Autowired
    private StotyRepo stotyRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }



}
