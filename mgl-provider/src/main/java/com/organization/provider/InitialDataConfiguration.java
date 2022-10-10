package com.organization.provider;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.organization.mvcproject.api.service.GameService;
import com.organization.provider.model.GameImpl;

@Configuration
public class InitialDataConfiguration {
	
	
	@Autowired
	private GameService gameService; 

    @PostConstruct
    public void postConstruct() {
    	GameImpl game1 = new GameImpl();
		game1.setGenre("Sport");
		game1.setName("Rocket League");

		GameImpl game2 = new GameImpl();
		game2.setGenre("Shooter");
		game2.setName("Halo 3");

		GameImpl game3 = new GameImpl();
		game3.setGenre("MMORPG");
		game3.setName("Runescape");
		
		gameService.saveGame(game1);
		gameService.saveGame(game2);
		gameService.saveGame(game3);
    }

}