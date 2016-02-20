package de.smava.bank.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;


@Transactional("transactionManager")
public abstract class AbsDao<T, PK extends Serializable>  {

    protected final Class<T> type;

    @PersistenceContext(unitName = "unit")
    private EntityManager entityManager;

    protected AbsDao(Class<T> type) {
        this.type = type;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Getter type name
     *
     * @return - the simple name of the underlying class
     */
    public String getTypeName() {
        return type.getSimpleName();
    }

    /**
     * Search entity manager by type and id
     *
     * @param id - id for search
     * @return - found entity manager
     */
    public T find(PK id) {
        return getEntityManager().find(type, id);
    }

    /**
     *
     * @param start - the position of the first result to retrieve
     * @param limit - the maximum number of results to retrieve
     * @return - list with identical objects
     */
    public List<T> list(Integer start, Integer limit) {
        Query query = entityManager.createQuery("from " + getTypeName() + " as e");
        if (start != null) {
            query.setFirstResult(start);
        }
        if (limit != null) {
            query.setMaxResults(limit);
        }
        //noinspection unchecked
        return query.getResultList();
    }

    /**
     * Remove object by ID
     *
     * @param id - ID object for removing
     */
    public void remove(PK id) {
        T obj = find(id);
        getEntityManager().remove(obj);
    }


    /**
     * Persist object with flush
     * @param obj - object for persisting
     */
    public void persist(T obj) {
        getEntityManager().persist(obj);
        getEntityManager().flush();
    }

    /**
     * Simple persist
     * without  flush
     * You should call flush method manually
     * Helpfully when we try insert to DB too many objects
     *
     * @param obj
     */
    public void persistWithoutFlush(T obj) {
        getEntityManager().persist(obj);
    }

    /**
     * Update object with flush
     * @param obj - object for persisting
     * @resturn - the managed instance that the state was merged to
     */
    public T merge(T obj) {
        T result = getEntityManager().merge(obj);
        getEntityManager().flush();
        return result;
    }

    /**
     * Simple update
     * without  flush
     * You should call flush method manually
     * Helpfully when we try update to DB too many objects
     *
     * @param obj
     */
    public void mergeWithoutFlush(T obj) {
        getEntityManager().merge(obj);
    }
}
