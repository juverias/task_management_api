package com.task.Security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

// Optional used for component 

@Component
public class TokenKillService {
	
	private final Set<String> blockListToken=ConcurrentHashMap.newKeySet();
	
	// Kill the Token 
	public void blockListToken(String token) {
		blockListToken.add(token);
	}
	
	// Check if working / not 
	public boolean isBlockListed(String token) {
		return blockListToken.contains(token);
	}
	
}
