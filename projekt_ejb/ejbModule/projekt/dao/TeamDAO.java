package projekt.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projektcourse.entities.Team;



@Stateless
public class TeamDAO {
	private final static String UNIT_NAME = "projektcourse-LoC";

	
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Team team) {
		em.persist(team);
	}

	public Team merge(Team team) {
		return em.merge(team);
	}

	public void remove(Team team) {
		em.remove(em.merge(team));
	}

	public Team find(Object id) {
		return em.find(Team.class, id);
	}

	
}

