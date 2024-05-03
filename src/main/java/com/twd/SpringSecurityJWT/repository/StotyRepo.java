package com.twd.SpringSecurityJWT.repository;


import com.twd.SpringSecurityJWT.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StotyRepo extends JpaRepository<Story, Integer> {

}
