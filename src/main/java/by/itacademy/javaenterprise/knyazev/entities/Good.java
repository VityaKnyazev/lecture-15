package by.itacademy.javaenterprise.knyazev.entities;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Good {

	private int id;
	private String name;	
	private String sort;	
	private String description;	
	private int categoryId;
	private int producerId;
	
}
