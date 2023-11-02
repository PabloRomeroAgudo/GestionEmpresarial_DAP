package view;

import java.util.List;

import dao.Gestion;
import io.IO;
import model.Departamento;
import model.Empleado;

public class View {

	public static void main(String[] args) {
		
		Gestion gestion = new Gestion();
		
		List<String> opciones = List.of(
				"0. Salir",
				"\n1. Añadir empleado",
				"\n2. Añadir departamento",
				"\n3. Borrar empleado",
				"\n4. Borrar departamento",
				"\n5. Modificar empleado",
				"\n6. Modificar departamento",
				"\n7. Buscar empleado por nombre",
				"\n8. Buscar departamento por nombre",
				"\n9. Buscar empleado por id",
				"\n10. Buscar departamento por id",
				"\n11. Mostrar"
				);
		
		while (true) {
			System.out.println("\n" + opciones.toString().replace(",", "") + "\n");
			switch(IO.readInt()) {
				case 0:
					gestion.close();
				return;
				case 1:
					addEmpleado(gestion);
					break;
				case 2:
					addDepartamento(gestion);
					break;
				case 3:
					delEmpleado(gestion);
					break;
				case 4:
					delDepartamento(gestion);
					break;
				case 5:
					updateEmp(gestion);
					break;
				case 6:
					updateDep(gestion);
					break;
				case 7:
					findEmpByName(gestion);
					break;
				case 8:
					findDepByName(gestion);
					break;
				case 9:
					findEmpByID(gestion);
					break;
				case 10:
					findDepByID(gestion);
					break;
				case 11:
					show(gestion);
					break;
				default:
					System.err.println("opcion incorrecta");
			}
		}
	}
	
	public static void addEmpleado(Gestion gestion) {
		IO.print("Introduce el nombre");
		String nombre = IO.readString();
		IO.print("Introduce el salario");
		Double salario = IO.readDouble();
		IO.print("Introduce el id del departamento");
		Integer idDepartamento = IO.readInt();
		
		boolean anadido = gestion.addEmpleado(new Empleado(nombre, salario, new Departamento(idDepartamento)));
		IO.print(anadido ? "Añadido correctamente" : "No se ha podido añadir");
	}
	
	public static void addDepartamento(Gestion gestion) {
		IO.print("Introduce el nombre");
		String nombre = IO.readString();
		IO.print("Introduce el id del jefe");
		Integer idJefe = IO.readInt();
		
		boolean anadido = gestion.addDepartamento(new Departamento(nombre, new Empleado(idJefe)));
		IO.print(anadido ? "Añadido correctamente" : "No se ha podido añadir");
	}
	
	public static void delEmpleado(Gestion gestion) {
		IO.print("Introduce el id del empleado");
		String id = IO.readString();
		
		boolean borrado = gestion.delEmpleado(id);
		IO.print(borrado ? "Borrado correctamente" : "No se ha podido borrar");
	}
	
	public static void delDepartamento(Gestion gestion) {
		IO.print("Introduce el id del departamento");
		String id = IO.readString();
		
		boolean borrado = gestion.delDepartamento(id);
		IO.print(borrado ? "Borrado correctamente" : "No se ha podido borrar");
	}
	
	public static void updateEmp(Gestion gestion) {
		IO.print("Introduce el id del empleado a modificar");
		Integer id = IO.readInt();
		IO.print("Introduce el salario nuevo");
		Double salario = IO.readDouble();
		IO.print("Introduce el id del departamento nuevo");
		Integer idDepartamento = IO.readInt();
		
		boolean anadido = gestion.updateEmp(new Empleado(id, salario, new Departamento(idDepartamento)));
		IO.print(anadido ? "Modificado correctamente" : "No se ha podido modificar");
	}
	
	public static void updateDep(Gestion gestion) {
		IO.print("Introduce el id del departamento a modificar");
		Integer id = IO.readInt();
		IO.print("Introduce el id del jefe nuevo");
		Integer jefe = IO.readInt();
		
		boolean anadido = gestion.updateDep(new Departamento(id, new Empleado(jefe)));
		IO.print(anadido ? "Modificado correctamente" : "No se ha podido modificar");
	}
	
	public static void findEmpByName(Gestion gestion) {
		IO.print("Introduce las iniciales");
		String initName = IO.readString();
		
		IO.print(gestion.findEmpByName(initName));
	}
	
	public static void findDepByName(Gestion gestion) {
		IO.print("Introduce las iniciales");
		String initName = IO.readString();
		
		IO.print(gestion.findDepByName(initName));
	}
	
	public static void findEmpByID(Gestion gestion) {
		IO.print("Introduce el id del empleado");
		Integer id = IO.readInt();
		
		IO.print(gestion.findEmpByID(id));
	}
	
	public static void findDepByID(Gestion gestion) {
		IO.print("Introduce el id del departamento");
		Integer id = IO.readInt();
		
		IO.print(gestion.findDepByID(id));
	}
	
	public static void show(Gestion gestion) {
		System.out.println(gestion.show());
	}

}
