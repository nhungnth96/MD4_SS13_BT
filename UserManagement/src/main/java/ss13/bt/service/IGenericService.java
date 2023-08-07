package ss13.bt.service;

import java.sql.SQLException;
import java.util.List;

public interface IGenericService<T,E> {
    List<T> getAll() throws SQLException, ClassNotFoundException;
    T findByID(E e) throws SQLException, ClassNotFoundException;
    void save(T t) throws SQLException, ClassNotFoundException;
    void delete(E e) throws SQLException, ClassNotFoundException;
}
