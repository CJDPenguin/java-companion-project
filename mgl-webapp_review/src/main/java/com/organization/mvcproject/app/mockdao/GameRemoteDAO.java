package com.organization.mvcproject.app.mockdao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableList;
import com.organization.mvcproject.api.mockdao.GameDAO;
import com.organization.mvcproject.api.model.Game;
import com.organization.mvcproject.app.model.GameImpl;

@Repository("gameRemoteDAO")
public class GameRemoteDAO implements GameDAO{


	
	Logger logger = LoggerFactory.getLogger(GameRemoteDAO.class);
	
	private final String RESOURCE_URI = "/game";

	@Value("${game.service.url}")
	private String serviceBaseUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Game saveGame(Game game) {
		String requestUri = serviceBaseUrl + RESOURCE_URI;

		ResponseEntity<Game> response = restTemplate.postForEntity(requestUri, game, Game.class);
		if(response.getStatusCode() != HttpStatus.OK) {
			logger.error("POST to "+ requestUri + " Unsuccessful, Saving Game: {}", game);
			return null;
		} else {
			return response.getBody();
		}
		
	}

	@Override
	public List<Game> findAllGames() {
		String requestUri = serviceBaseUrl + RESOURCE_URI;
		ResponseEntity<List<GameImpl>> response = restTemplate.exchange(
				requestUri, HttpMethod.GET, null,  new ParameterizedTypeReference<List<GameImpl>>(){});
		
		if(response.getStatusCode() != HttpStatus.OK) {
			logger.error("GET: "+ requestUri + " did not recieve an ok status response OK Status");
			return null;
		} else {
			return ImmutableList.copyOf( response.getBody());
		}
	}

	@Override
	public Game findGameById(Long id) {
		String requestUri = serviceBaseUrl + RESOURCE_URI;
		ResponseEntity<Game> response =  restTemplate.getForEntity(requestUri + "/{id}", Game.class, Long.toString(id));
		
		return response.getBody();
	}


	public Game updateGame(Long id, Game game) {		
		String requestUri = serviceBaseUrl + RESOURCE_URI;
		
		ResponseEntity<Game> response = restTemplate.exchange(requestUri + "/{id}", 
				HttpMethod.PUT,
				new HttpEntity<>(game),
				Game.class,
				Long.toString(id));
		
		return response.getBody(); 

	}


	@Override
	public List<Game> findGamesByGenre(String genre) {
		String requestUri = serviceBaseUrl + RESOURCE_URI;
		ResponseEntity<Game[]> games = restTemplate.getForEntity(
				requestUri,
				Game[].class);
				
		return null; 
	}


	@Override
	public boolean deleteGame(Long id) {
		String requestUri = serviceBaseUrl + RESOURCE_URI;
		
		  HttpHeaders headers = new HttpHeaders();
	      HttpEntity<Boolean> entity = new HttpEntity<Boolean>(headers);
	      
		ResponseEntity<Boolean> response =  restTemplate.exchange(
				requestUri +id, HttpMethod.DELETE, entity,  Boolean.class);
		return response.getBody();
	}
}