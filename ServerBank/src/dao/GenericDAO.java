package dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDAO<T extends Serializable> {

    T findById(long id);

    List<T> findAll();

    T makePersistent(T entity);
    
      
     long deleteById(long id);

     long updateByid(long id);

}
