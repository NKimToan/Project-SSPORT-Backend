package com.SSPORT.SSPORT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

}
