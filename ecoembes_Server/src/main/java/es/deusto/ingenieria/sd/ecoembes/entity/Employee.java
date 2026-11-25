package es.deusto.ingenieria.sd.ecoembes.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;
	private String password;

	//CONSTRUCTORS
	public Employee() { }

	public Employee(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Employee(long id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	//GETTERS AND SETTERS
	
	//Name
	public String getName() { 
		return name; 
		}
	
	public void setName(String name) { 
		this.name = name; 
		}

	//Email
	public String getEmail() { 
		return email; 
		}
	
	public void setEmail(String email) { 
		this.email = email; 
		}

	//Password
	public String getPassword() { 
		return password; 
		}
	
	public void setPassword(String password) { 
		this.password = password; 
		}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		return id == ((Employee) obj).id;
	}

	@Override
	public int hashCode() { return Objects.hash(id); }

	public boolean checkPassword(String pass) {
	    return this.password != null && this.password.equals(pass);
	}
}
