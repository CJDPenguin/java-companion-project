package com.organization.provider.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.organization.mvcproject.api.mockdao.GameDAO;
import com.organization.mvcproject.api.model.Game;
import com.organization.provider.model.GameImpl;

@Repository
public class GameDAOImpl implements GameDAO  {
	
	@Autowired
    private EntityManager em;

	@Override
	public Game findGameById(Long id) {
		Query query = em.createQuery("FROM Game g WHERE g.id = :id"); 
		query.setParameter("id", id);
		return (Game) query.getResultList();
	}

	@Override
	public List<Game> findGamesByGenre(String genre) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<GameImpl> cq = cb.createQuery(GameImpl.class);
	    Root<GameImpl> game = cq.from(GameImpl.class);
        Predicate genrePredicate = cb.equal(game.get("genre"), genre);
        cq.where(genrePredicate);
        TypedQuery<GameImpl> query = em.createQuery(cq);
        return ImmutableList.copyOf(query.getResultList());
	}

	@Override
	public Game saveGame(Game game) {
		em.persist((GameImpl) game);
		return game; //returns with ID
	}

	@Override
	public boolean deleteGame(Long id) {
		Query query = em.createQuery("DELETE FROM Game WHERE ID = :ID");
		query.setParameter("id", id);		
		return (0 > query.executeUpdate());
	}

	@Override
	public List<Game> findAllGames() {
		
		 CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<GameImpl> cq = cb.createQuery(GameImpl.class);
		    Root<GameImpl> root = cq.from(GameImpl.class);
		    CriteriaQuery<GameImpl> all = cq.select(root);
		    return ImmutableList.copyOf(em.createQuery(all).getResultList());
	
	}
	
	
}
