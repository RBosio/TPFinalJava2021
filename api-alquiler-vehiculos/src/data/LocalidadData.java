package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Localidad;
import entities.Pais;
import entities.Provincia;


public class LocalidadData {
	
	public LinkedList<Localidad> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Localidad> localidades = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM localidad l INNER JOIN provincia p ON l.idProv = p.idProv INNER JOIN pais ON p.idPais = pais.idPais");
			
				while(rs.next()){
					Localidad l = new Localidad();
					Provincia pro = new Provincia();
					Pais pais = new Pais();
					l.setCodPostal(rs.getString("codPostal"));
					l.setNombre(rs.getString("nombre"));
					l.setEstado(rs.getBoolean("l.estado"));
					l.setIdProv(rs.getInt("l.idProv"));
					
					pro.setDenominacion(rs.getString("p.denominacion"));
					l.setProvincia(pro);
					
					pais.setDenominacion(rs.getString("pais.denominacion"));
					pro.setPais(pais);
					localidades.add(l);
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
	
	public Localidad findByCodPostal(Localidad l) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM localidad l INNER JOIN provincia p ON l.idProv = p.idProv INNER JOIN pais ON p.idPais = pais.idPais WHERE codPostal=?");
			stmt.setString(1, l.getCodPostal());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				l = new Localidad();
				Provincia pro = new Provincia();
				Pais pais = new Pais();
				l.setCodPostal(rs.getString("codPostal"));
				l.setNombre(rs.getString("nombre"));
				l.setEstado(rs.getBoolean("l.estado"));
				l.setIdProv(rs.getInt("l.idProv"));
				
				pro.setDenominacion(rs.getString("p.denominacion"));
				l.setProvincia(pro);
				
				pais.setDenominacion(rs.getString("pais.denominacion"));
				pro.setPais(pais);
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
		
		return l;
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
