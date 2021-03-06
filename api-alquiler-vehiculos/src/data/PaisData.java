package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Pais;

public class PaisData {
	
	public LinkedList<Pais> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Pais> paises = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM pais");
			
				while(rs.next()){
					Pais p = new Pais();
					p.setIdPais(rs.getInt("idPais"));
					p.setDenominacion(rs.getString("denominacion"));
					p.setEstado(rs.getBoolean("estado"));
					paises.add(p);
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
		
		return paises;
	}
	
	public Pais findById(Pais p) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM pais WHERE idPais=?");
			stmt.setInt(1, p.getIdPais());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				p = new Pais();
				p.setIdPais(rs.getInt("idPais"));
				p.setDenominacion(rs.getString("denominacion"));
				p.setEstado(rs.getBoolean("estado"));
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
	
	public Pais newCountry(Pais nuevoP) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO pais(denominacion) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nuevoP.getDenominacion());
			stmt.executeUpdate();
			key = stmt.getGeneratedKeys();
			if(key != null && key.next()) {
				nuevoP.setIdPais(key.getInt(1));
			}
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
		
		return nuevoP;
	}
	
	public Pais updateCountry(Pais actP) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE pais SET denominacion=? WHERE idPais=?");
			stmt.setString(1, actP.getDenominacion());
			stmt.setInt(2, actP.getIdPais());
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
		
		return actP;
	}
	
	public Pais deleteCountry(Pais delP) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE pais SET estado=? WHERE idPais=?");
			stmt.setBoolean(1, false);
			stmt.setInt(2, delP.getIdPais());
			stmt.executeUpdate();
			
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
