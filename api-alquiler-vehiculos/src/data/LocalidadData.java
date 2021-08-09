package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Localidad;


public class LocalidadData {
	
	public LinkedList<Localidad> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Localidad> localidades = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM localidad");
			
				while(rs.next()){
					Localidad p = new Localidad();
					p.setCodPostal(rs.getString("codPostal"));
					p.setNombre(rs.getString("nombre"));
					p.setEstado(rs.getBoolean("estado"));
					p.setIdProv(rs.getInt("idProv"));
					localidades.add(p);
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
		
		return localidades;
	}
	
	public Localidad findById(Localidad p) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM localidad WHERE codPostal=?");
			stmt.setString(1, p.getCodPostal());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				p = new Localidad();
				p.setCodPostal(rs.getString("codPostal"));
				p.setNombre(rs.getString("nombre"));
				p.setEstado(rs.getBoolean("estado"));
				p.setIdProv(rs.getInt("idProv"));
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
	
	public Localidad newCity(Localidad nuevaL) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO localidad(codPostal, nombre, idProv) VALUES(?, ?, ?)");
			stmt.setString(1, nuevaL.getCodPostal());
			stmt.setString(2, nuevaL.getNombre());
			stmt.setInt(3, nuevaL.getIdProv());
			stmt.executeUpdate();
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
		
		return nuevaL;
	}
	
	public Localidad updateCity(Localidad actL) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE localidad SET nombre=?, idProv=? WHERE codPostal=?");
			stmt.setString(1, actL.getNombre());
			stmt.setInt(2, actL.getIdProv());
			stmt.setString(3, actL.getCodPostal());
			stmt.executeUpdate();
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
		
		return actL;
	}
	
	public Localidad deleteCity(Localidad delL) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE localidad SET estado=? WHERE codPostal=?");
			stmt.setBoolean(1, false);
			stmt.setString(2, delL.getCodPostal());
			stmt.executeUpdate();
			
			delL.setEstado(false);
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
		
		return delL;
	}
}
