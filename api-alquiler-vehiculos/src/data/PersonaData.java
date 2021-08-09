package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Persona;


public class PersonaData {
	
	public LinkedList<Persona> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Persona> personas = new LinkedList<>();
		RolData rolData = new RolData();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT dni, nombre, apellido, email, telefono, estado, codPostal FROM persona");
			
				while(rs.next()){
					Persona p = new Persona();
					p.setDni(rs.getString("dni"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					p.setEmail(rs.getString("email"));
					p.setTelefono(rs.getString("telefono"));
					p.setEstado(rs.getBoolean("estado"));
					p.setCodPostal(rs.getString("codPostal"));
					
					p.setRoles(rolData.findByUser(p));
					
					personas.add(p);
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
		
		return personas;
	}
	
	public Persona findByDni(Persona p) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		RolData rolData = new RolData();

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT dni, p.nombre, apellido, email, telefono, p.estado, l.codPostal, l.nombre, pro.denominacion, pais.denominacion FROM persona p INNER JOIN localidad l ON p.codPostal = l.codPostal INNER JOIN provincia pro ON l.idProv = pro.idProv INNER JOIN pais ON pro.idPais = pais.idPais WHERE dni=?");
			stmt.setString(1, p.getDni());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				p = new Persona();
				p.setDni(rs.getString("dni"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.setEmail(rs.getString("email"));
				p.setTelefono(rs.getString("telefono"));
				p.setEstado(rs.getBoolean("estado"));
				p.setCodPostal(rs.getString("l.codPostal"));
				p.setNomLoc(rs.getString("l.nombre"));
				p.setDenomProv(rs.getString("pro.denominacion"));
				p.setApellido(rs.getString("pais.denominacion"));
				
				p.setRoles(rolData.findByUser(p));
			} else {
				throw new NullPointerException();
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
		
		return p;
	}
	
	public Persona findByEmail(Persona p) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		RolData rolData = new RolData();

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT dni, p.nombre, apellido, email, password, telefono, p.estado, l.codPostal, l.nombre, pro.denominacion, pais.denominacion FROM persona p INNER JOIN localidad l ON p.codPostal = l.codPostal INNER JOIN provincia pro ON l.idProv = pro.idProv INNER JOIN pais ON pro.idPais = pais.idPais WHERE email=?");
			stmt.setString(1, p.getEmail());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				p = new Persona();
				p.setDni(rs.getString("dni"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.setEmail(rs.getString("email"));
				p.setPassword(rs.getString("password"));
				p.setTelefono(rs.getString("telefono"));
				p.setEstado(rs.getBoolean("estado"));
				p.setCodPostal(rs.getString("l.codPostal"));
				p.setNomLoc(rs.getString("l.nombre"));
				p.setDenomProv(rs.getString("pro.denominacion"));
				p.setApellido(rs.getString("pais.denominacion"));
				
				p.setRoles(rolData.findByUser(p));
			} else {
				throw new NullPointerException();
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
		
		return p;
	}
	
	public Persona newUser(Persona nuevaP) throws SQLException, IOException{
		PreparedStatement stmt = null;
		RolData rolData = new RolData();
				

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO persona(dni, nombre, apellido, email, password, telefono, codPostal) VALUES(?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, nuevaP.getDni());
			stmt.setString(2, nuevaP.getNombre());
			stmt.setString(3, nuevaP.getApellido());
			stmt.setString(4, nuevaP.getEmail());
			stmt.setString(5, nuevaP.getPassword());
			stmt.setString(6, nuevaP.getTelefono());
			stmt.setString(7, nuevaP.getCodPostal());
			stmt.executeUpdate();
			
			rolData.newPersonaRol(nuevaP);
			
			nuevaP.setPassword(null);
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
		
		return nuevaP;
	}

	public Persona updateUser(Persona actP) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE persona SET nombre=?, apellido=?, email=?, password=?, telefono=?, codPostal=? WHERE dni=?");
			stmt.setString(1, actP.getNombre());
			stmt.setString(2, actP.getApellido());
			stmt.setString(3, actP.getEmail());
			stmt.setString(4, actP.getPassword());
			stmt.setString(5, actP.getTelefono());
			stmt.setString(6, actP.getCodPostal());
			stmt.setString(7, actP.getDni());
			stmt.executeUpdate();
			
			actP.setPassword(null);
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
		
		return actP;
	}

	public Persona deleteUser(Persona delP) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE persona SET estado=? WHERE dni=?");
			stmt.setBoolean(1, false);
			stmt.setString(2, delP.getDni());
			stmt.executeUpdate();
			
			delP.setPassword(null);
			delP.setEstado(false);
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
		
		return delP;
	}
}
