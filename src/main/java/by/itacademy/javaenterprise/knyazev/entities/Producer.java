package by.itacademy.javaenterprise.knyazev.entities;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producer {

	private int id;
	private String name;
	private String postalCode;
	private String country;
	private String region;
	private String locality;
	private String street;	
	private String building;	
}
