package model;

import lombok.Getter;
import lombok.Setter;

public class Empleado {
	
	@Setter
	@Getter
	private Integer id;
	
	@Getter
	@Setter
	private String nombre;
	
	@Getter
	@Setter
	private Double salario;

	@Getter
	private Departamento departamento;
	
	// Se llama en el show
	public Empleado(Integer id, String nombre, Double salario, Integer departamento) {
		this.setId(id);
		this.setNombre(nombre);
		this.setSalario(salario);
		this.setDepartamento(new Departamento(departamento));
	}
	
	// Se llama al añadir empleado
	public Empleado(String nombre, Double salario, Departamento departamento) {
		setSalario(salario);
		setDepartamento(departamento);
		setNombre(nombre);
	}
	
	// Se llama cuando se añade departamento
	public Empleado(Integer idEmpleado ) {
		setId(idEmpleado);
	}
	
	// Se llama al modificar un empleado
	public Empleado(Integer id,Double salario, Departamento departamento) {
		setId(id);
		setSalario(salario);
		setDepartamento(departamento);
	}
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	@Override
	public String toString() {
		return "\n\tID: " + getId() + ", NOMBRE: " + getNombre() + ", SALARIO: " + getSalario()
				+ ", ID_DEPART: " + getDepartamento().getId();
	}	
}
