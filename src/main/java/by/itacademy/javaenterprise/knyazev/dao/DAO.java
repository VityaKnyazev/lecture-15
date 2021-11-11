package by.itacademy.javaenterprise.knyazev.dao;

import java.util.List;

public interface DAO<T> {
	T select(int id);
	List<T> selectAll(); 
	int save(T object);
	int delete(T object);
}