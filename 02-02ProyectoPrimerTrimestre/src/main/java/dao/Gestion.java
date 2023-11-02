package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Departamento;
import model.Empleado;

public class Gestion {
	private Connection conn = null;

	/**
	 * Constructor
	 */
	public Gestion() {
		conn = BD.getConnection();
		createTables();
	}

	public void close() {
		BD.close();
	}

	public String show() {
		String sql =	"""
							SELECT * FROM empleado
						""";
		String sql2 = 	"""
							SELECT * FROM departamento
						""";
		StringBuffer sb = new StringBuffer();
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();
		ArrayList<Departamento> departamentos = new ArrayList<Departamento>();
		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				Double salario = rs.getDouble("salario");
				Integer departamento = rs.getInt("departamento");
				Empleado e = new Empleado(id, nombre, salario, departamento);
				empleados.add(e);
			}

			rs = stmt.executeQuery(sql2);

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				Integer jefe = rs.getInt("jefe");
				Departamento d = new Departamento(id, nombre, jefe);
				departamentos.add(d);
			}

			sb.append("EMPLEADOS:\n");
			sb.append(empleados.toString());
			sb.append("\n\nDEPARTAMENTOS:\n");
			sb.append(departamentos.toString() + "\n");

			return sb.toString();
		} catch (SQLException e) {
			return "";
		}
	}

	public boolean addEmpleado(Empleado emp) {
		String sql =	"""
							SELECT id FROM departamento WHERE id = ?
						""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, emp.getDepartamento().getId());
			ResultSet rs = ps.executeQuery();

			if (rs.next() || emp.getDepartamento().getId() == 0) {
				sql =	"""
							INSERT INTO empleado(nombre, salario, departamento) VALUES (?, ?, ?)
						""";
				ps = conn.prepareStatement(sql);
				ps.setString(1, emp.getNombre());
				ps.setDouble(2, emp.getSalario());
				if (emp.getDepartamento().getId() == 0) {
					ps.setObject(3, null);
				} else {
					ps.setInt(3, emp.getDepartamento().getId());
				}

				return ps.executeUpdate() > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addDepartamento(Departamento dep) {
		String sql =	"""
							SELECT id FROM empleado WHERE id = ?
						""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dep.getJefe().getId());
			ResultSet rs = ps.executeQuery();

			if (rs.next() || dep.getJefe().getId() == 0) {
				sql = 	"""
							INSERT INTO departamento(nombre, jefe) VALUES (?, ?)
						""";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dep.getNombre());
				if (dep.getJefe().getId() == 0) {
					ps.setObject(2, null);
				} else {
					ps.setInt(2, dep.getJefe().getId());
				}

				return ps.executeUpdate() > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delEmpleado(String id) {
		String sql =	"""
							DELETE FROM empleado WHERE id = ?;
						""";
		String sql2 = """
					UPDATE departamento SET jefe = null WHERE jefe = ?;
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			conn.setAutoCommit(false);
			ps.setString(1, id);
			ps.executeUpdate();

			ps = conn.prepareStatement(sql2);
			ps.setString(1, id);
			ps.executeUpdate();

			conn.commit();
			return true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delDepartamento(String id) {
		String sql = 	"""
							DELETE FROM departamento WHERE id = ?;
						""";
		String sql2 = 	"""
							UPDATE empleado SET departamento = null WHERE departamento = ?;
						""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			conn.setAutoCommit(false);
			ps.setString(1, id);
			ps.executeUpdate();

			ps = conn.prepareStatement(sql2);
			ps.setString(1, id);
			ps.executeUpdate();

			conn.commit();
			return true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateEmp(Empleado emp) {
		String sql = 	"""
							SELECT id FROM departamento WHERE id = ?
						""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, emp.getDepartamento().getId());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next() || emp.getDepartamento().getId() == 0) {
				sql = 	"""
							UPDATE empleado SET salario = ?, departamento = ? WHERE id = ?
						""";
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, emp.getSalario());
				if (emp.getDepartamento().getId() == 0) {
					ps.setObject(2, null);
				} else {					
					ps.setInt(2, emp.getDepartamento().getId());
				}
				ps.setInt(3, emp.getId());
				return ps.executeUpdate() > 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateDep(Departamento dep) {
		String sql =	"""
							SELECT id FROM empleado WHERE id = ?
						""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dep.getJefe().getId());
			ResultSet rs = ps.executeQuery();

			if (rs.next() || dep.getJefe().getId() == 0) {
				sql = 	"""
							UPDATE departamento SET jefe = ? WHERE id = ?
						""";
				ps = conn.prepareStatement(sql);
				if (dep.getJefe().getId() == 0) {
					ps.setObject(1, null);
				} else {
					ps.setInt(1, dep.getJefe().getId());
				}
				ps.setInt(2, dep.getId());

				return ps.executeUpdate() > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String findEmpByName(String initName) {
		String sql = 	"""
							SELECT * FROM empleado WHERE nombre LIKE ?
						""";
		
		StringBuffer sb = new StringBuffer();
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, initName + "%");
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				Double salario = rs.getDouble("salario");
				Integer departamento = rs.getInt("departamento");
				empleados.add(new Empleado(id, nombre, salario, departamento));
			}
			sb.append(empleados.toString());
			return sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "No se ha encontrado ningun empleado";
	}
	
	public String findDepByName(String initName) {
		String sql = 	"""
							SELECT * FROM departamento WHERE nombre LIKE ?
						""";		
		StringBuffer sb = new StringBuffer();
		ArrayList<Departamento> departamentos = new ArrayList<Departamento>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, initName + "%");
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				Integer jefe = rs.getInt("jefe");
				departamentos.add(new Departamento(id, nombre, jefe));
			}
			sb.append(departamentos.toString());
			return sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "No se ha encontrado ningun departamento";
	}
	
	public String findEmpByID(Integer idToFind) {
		String sql = 	"""
							SELECT * FROM empleado WHERE id = ?
						""";
		Empleado emp = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idToFind);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Integer id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				Double salario = rs.getDouble("salario");
				Integer departamento = rs.getInt("departamento");
				emp = new Empleado(id, nombre, salario, departamento);
				return emp.toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "No se encuentra el empleado con ese ID";
	}
	
	public String findDepByID(Integer idToFind) {
		String sql = 	"""
							SELECT * FROM departamento WHERE id = ?
						""";		
		Departamento dep = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idToFind);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Integer id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				Integer jefe = rs.getInt("jefe");
				dep = new Departamento(id, nombre, jefe);
				return dep.toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "No se encuentra el departamento con ese ID";
	}
	
	/**
	 * Crea el esquema de la base de datos
	 * 
	 * @throws SQLException
	 */
	private void createTables() {
		String tablaEmpleado = null;
		String tableDepartamento = null;
		if (BD.typeDB.equals("sqlite")) {
			tablaEmpleado =	"""
								CREATE TABLE IF NOT EXISTS empleado (
									id INTEGER PRIMARY KEY AUTOINCREMENT,
									nombre TEXT NOT NULL,
									salario REAL DEFAULT 0.0,
									departamento INTEGER
								);
							""";
			tableDepartamento =	"""
									CREATE TABLE IF NOT EXISTS departamento (
										id INTEGER PRIMARY KEY AUTOINCREMENT,
										nombre TEXT NOT NULL,
										jefe INTEGER
									)
								""";
		}
		if (BD.typeDB.equals("mariadb")) {
			tablaEmpleado =	"""
								CREATE TABLE IF NOT EXISTS empleado (
								id INT PRIMARY KEY AUTO_INCREMENT,
								nombre VARCHAR(255),
								salario DECIMAL(10,2) DEFAULT 0.0,
								departamento INT
								);
							""";
			tableDepartamento =	"""
									CREATE TABLE IF NOT EXISTS departamento (
									  id INT PRIMARY KEY AUTO_INCREMENT,
									  nombre VARCHAR(255) NOT NULL,
									  jefe INT
									)
								""";
		}
		try {
			conn.createStatement().executeUpdate(tablaEmpleado);
			conn.createStatement().executeUpdate(tableDepartamento);
		} catch (SQLException e) {
		}
	}
}
