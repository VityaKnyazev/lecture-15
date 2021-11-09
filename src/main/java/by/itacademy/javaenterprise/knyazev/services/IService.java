package by.itacademy.javaenterprise.knyazev.services;

import java.util.List;

public interface IService<T> {
	public T represent(int id);
	public List<T> represent();
}
