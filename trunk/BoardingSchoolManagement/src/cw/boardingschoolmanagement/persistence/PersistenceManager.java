package cw.boardingschoolmanagement.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public interface PersistenceManager<T extends CWPersistence> {

    public void remove(T persistence);
    
    public T get(Long id, EntityManager entityManager);
    
    public List<T> getAll(EntityManager entityManager);

    public int countAll(EntityManager entityManager);
	
}
