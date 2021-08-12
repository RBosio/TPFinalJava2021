package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Alquiler;
import entities.Cobertura;
import entities.Marca;
import entities.Persona;
import entities.Vehiculo;

public class AlquilerData {
	
	public LinkedList<Alquiler> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Alquiler> alquileres = new LinkedList<Alquiler>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			 
			rs = stmt.executeQuery("SELECT * FROM alquiler a INNER JOIN persona p ON a.dni = p.dni INNER JOIN vehiculo v ON a.idVeh = v.idVeh INNER JOIN cobertura c ON a.idCob = c.idCob INNER JOIN marca m ON v.idMarca = m.idMarca");
			
			while(rs.next()){
				Alquiler a = new Alquiler();
				Cobertura cob = new Cobertura();
				Persona per = new Persona();
				Vehiculo veh = new Vehiculo();
				
				a.setDni(rs.getString("p.dni"));
				a.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
				a.setIdVeh(rs.getInt("v.idVeh"));
				a.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
				a.setEstado(rs.getString("estado"));
				a.setCostoTotal(rs.getDouble("costoTotal"));
				a.setIdCob(rs.getInt("c.idCob"));
				
				cob.setDescripcion(rs.getString("c.descripcion"));
				cob.setPrecioDia(rs.getDouble("c.precioDia"));
				cob.setEstado(rs.getBoolean("c.estado"));
				a.setCobertura(cob);
				
				per.setApellido(rs.getString("apellido"));
				per.setNombre(rs.getString("nombre"));
				per.setEmail(rs.getString("email"));
				per.setTelefono(rs.getString("telefono"));
				a.setPersona(per);
				
				veh.setDenominacion(rs.getString("v.denominacion"));
				veh.setPrecioDia(rs.getDouble("v.precioDia"));
				veh.setImagen(rs.getString("imagen"));
				Marca m = new Marca();
				m.setDenominacion(rs.getString("m.denominacion"));
				veh.setMarca(m);
				a.setVehiculo(veh);
				
				alquileres.add(a);
			}
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(stmt!= null ) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return alquileres;
	}
	
	public Alquiler findByDni(Alquiler alq) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Alquiler a = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM alquiler a INNER JOIN persona p ON a.dni = p.dni INNER JOIN vehiculo v ON a.idVeh = v.idVeh INNER JOIN cobertura c ON a.idCob = c.idCob INNER JOIN marca m ON v.idMarca = m.idMarca WHERE p.dni=? AND fechaHoraInicio=?");
			stmt.setString(1, alq.getDni());
			stmt.setTimestamp(2, Timestamp.valueOf(alq.getFechaHoraInicio()));
			rs = stmt.executeQuery();
			
			if(rs.next()){
				a = new Alquiler();
				Cobertura cob = new Cobertura();
				Persona per = new Persona();
				Vehiculo veh = new Vehiculo();
				
				a.setDni(rs.getString("p.dni"));
				a.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
				a.setIdVeh(rs.getInt("v.idVeh"));
				a.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
				a.setEstado(rs.getString("estado"));
				a.setCostoTotal(rs.getDouble("costoTotal"));
				a.setIdCob(rs.getInt("c.idCob"));
				
				cob.setDescripcion(rs.getString("c.descripcion"));
				cob.setPrecioDia(rs.getDouble("c.precioDia"));
				cob.setEstado(rs.getBoolean("c.estado"));
				a.setCobertura(cob);
				
				per.setApellido(rs.getString("apellido"));
				per.setNombre(rs.getString("nombre"));
				per.setEmail(rs.getString("email"));
				per.setTelefono(rs.getString("telefono"));
				a.setPersona(per);
				
				veh.setDenominacion(rs.getString("v.denominacion"));
				veh.setPrecioDia(rs.getDouble("v.precioDia"));
				veh.setImagen(rs.getString("imagen"));
				Marca m = new Marca();
				m.setDenominacion(rs.getString("m.denominacion"));
				veh.setMarca(m);
				a.setVehiculo(veh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(stmt!= null ) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		return a;
	}
	
	public Alquiler newRent(Alquiler nuevoA) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO alquiler(dni, fechaHoraInicio, idVeh, fechaHoraFin, costoTotal, idCob) VALUES(?, ?, ?, ?, ?, ?)");
			stmt.setString(1, nuevoA.getDni());
			stmt.setTimestamp(2, Timestamp.valueOf(nuevoA.getFechaHoraInicio()));
			stmt.setInt(3, nuevoA.getIdVeh());
			stmt.setTimestamp(4, Timestamp.valueOf(nuevoA.getFechaHoraFin()));
			stmt.setDouble(5, nuevoA.getCostoTotal());
			stmt.setInt(6, nuevoA.getIdCob());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (key != null) key.close();
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return nuevoA;
	}
	
	public Alquiler confirmRent(Alquiler confA) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE alquiler SET estado=? WHERE dni=? AND fechaHoraInicio=?");
			stmt.setString(1, "En Curso");
			stmt.setString(2, confA.getDni());
			stmt.setTimestamp(3, Timestamp.valueOf(confA.getFechaHoraInicio()));
			stmt.executeUpdate();
			
			confA.setEstado("En Curso");
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return confA;
	}
	
	public Alquiler finishRent(Alquiler finA) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE alquiler SET estado=? WHERE dni=? AND fechaHoraInicio=?");
			stmt.setString(1, "Terminado");
			stmt.setString(2, finA.getDni());
			stmt.setTimestamp(3, Timestamp.valueOf(finA.getFechaHoraInicio()));
			stmt.executeUpdate();
			
			finA.setEstado("Terminado");
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return finA;
	}
	
	public Alquiler cancelRent(Alquiler delA) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE alquiler SET estado=? WHERE dni=? AND fechaHoraInicio=?");
			stmt.setString(1, "Cancelado");
			stmt.setString(2, delA.getDni());
			stmt.setTimestamp(3, Timestamp.valueOf(delA.getFechaHoraInicio()));
			stmt.executeUpdate();
			
			delA.setEstado("Cancelado");
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return delA;
	}
	
	public int getIdFactura() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		int id = 0;
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			 
			rs = stmt.executeQuery("SELECT COUNT(*) id FROM alquiler");
			
			if(rs.next()){
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(stmt!= null ) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return id;
	}
}
