package model;

import lombok.Getter;
import lombok.Setter;

public class Departamento {
	@Getter
	@Setter
	private Integer id;
	
	@Getter
	@Setter
	private String nombre;
	
	@Getter
	private Empleado jefe;
	
	// Esto se llama en el show
	public Departamento(Integer id, String nombre, Integer jefe) {
		this(id);
		setNombre(nombre);
		setJefe(new Empleado(jefe));
	}
	
	// Esto al añadir empleado
	public Departamento(Integer id) {
		this.setId(id);
	}
	
	// Esto es al añadir departamento
	public Departamento(String nombre, Empleado jefe) {
		setNombre(nombre);
		setJefe(jefe);
	}
	
	// Se llama al modificar departamento
	public Departamento(Integer id, Empleado jefe) {
		setId(id);
		setJefe(jefe);
	}
	
	private void setJefe(Empleado jefe) {
		this.jefe = jefe;
	}
	
	
	@Override
	public String toString() {
		return "\n\tID: " + getId() + ", NOMBRE: " + getNombre() + ", JEFE: " + getJefe().getId();
	}
	
}
