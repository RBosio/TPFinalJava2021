package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Persona;
import entities.Rol;

public class RolData {
	
	public LinkedList<Rol> findByUser(Persona p) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Rol> roles = new LinkedList<Rol>();

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM rol INNER JOIN persona_rol pr ON rol.idRol = pr.idRol WHERE pr.dni=?");
			stmt.setString(1, p.getDni());
			rs = stmt.executeQuery();
			while(rs.next()){
				Rol rol = new Rol();
				rol.setIdRol(rs.getInt("rol.idRol"));
				rol.setNombre(rs.getString("nombre"));
				roles.add(rol);
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
		
		return roles;
	}
	
	public void newPersonaRol(Persona persona) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO persona_rol(dni, idRol) VALUES(?, ?)");
			stmt.setString(1, persona.getDni());
			if(persona.getRoles().isEmpty()){
				stmt.setInt(2, 1);	
				stmt.executeUpdate();
			}else{
				for(Rol rol: persona.getRoles()){
					stmt.setInt(2, rol.getIdRol());
					stmt.executeUpdate();
				}
			}
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
		
	}
}
