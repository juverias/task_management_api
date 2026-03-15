package com.task.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.Entity.TokenBlockList;

public interface TokenBlockListRepository extends JpaRepository<TokenBlockList,Long>{

	boolean existsByToken(String token);
	
}
