package cw.boardingschoolmanagement.app;

import java.util.List;

import javax.persistence.EntityManager;

public interface PersistenceManager<T extends CWPersistence> {

    public T create(EntityManager entityManager);
    public void remove(T persistence, EntityManager entityManager);

    public List<T> getAll(EntityManager entityManager);

    public int countAll(EntityManager entityManager);
	
}
