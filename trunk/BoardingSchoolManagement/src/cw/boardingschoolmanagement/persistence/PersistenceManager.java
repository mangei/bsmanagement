package cw.boardingschoolmanagement.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public interface PersistenceManager<T extends CWPersistence> {

    public T create(EntityManager entityManager);
    public void remove(T persistence);

    public List<T> getAll(EntityManager entityManager);

    public int countAll(EntityManager entityManager);
	
}
