package by.itacademy.javaenterprise.knyazev.dto;

import by.itacademy.javaenterprise.knyazev.entities.Category;
import by.itacademy.javaenterprise.knyazev.entities.Producer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	private int id;
	private String name;	
	private String sort;	
	private String description;	
	private Category category;	
	private Producer producer;

}
