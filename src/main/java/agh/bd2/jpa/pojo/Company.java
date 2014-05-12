package agh.bd2.jpa.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Company {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	@OneToMany(mappedBy="employer", cascade=CascadeType.ALL)
	private Set<Employee> employees;
	
	@OneToOne
	@JoinColumn(name="ceoID")
	private Employee ceo;
	
	public Employee getCeo() {
		return ceo;
	}
	public void setCeo(Employee ceo) {
		this.ceo = ceo;
	}
	public Company(){
		employees = new HashSet<>();
	}	
	public Company(String name) {
		this.name = name;
		employees = new HashSet<>();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	public void addEmployee(Employee empl) {
		employees.add(empl);
	}
	public void removeEmployee(Employee empl) {
		employees.remove(empl);
	}
}
