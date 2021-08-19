package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Provincia;


public class ProvinciaData {
	
	public LinkedList<Provincia> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Provincia> provincias = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM provincia");
			
				while(rs.next()){
					Provincia p = new Provincia();
					p.setIdProvincia(rs.getInt("idProv"));
					p.setDenominacion(rs.getString("denominacion"));
					p.setEstado(rs.getBoolean("estado"));
					p.setIdPais(rs.getInt("idPais"));
					provincias.add(p);
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
		
		return provincias;
	}
	
	public Provincia findById(Provincia p) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM provincia WHERE idProv=?");
			stmt.setInt(1, p.getIdProvincia());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				p = new Provincia();
				p.setIdProvincia(rs.getInt("idProv"));
				p.setDenominacion(rs.getString("denominacion"));
				p.setEstado(rs.getBoolean("estado"));
				p.setIdPais(rs.getInt("idPais"));
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
	
	public Provincia newProvince(Provincia nuevaP) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO provincia(denominacion, idPais) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nuevaP.getDenominacion());
			stmt.setInt(2, nuevaP.getIdPais());
			stmt.executeUpdate();
			
			key = stmt.getGeneratedKeys();
			if(key != null && key.next()) {
				nuevaP.setIdProvincia(key.getInt(1));
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
		
		return nuevaP;
	}
	
	public Provincia updateProvince(Provincia actP) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE provincia SET denominacion=?, idPais=? WHERE idProv=?");
			stmt.setString(1, actP.getDenominacion());
			stmt.setInt(2, actP.getIdPais());
			stmt.setInt(3, actP.getIdProvincia());
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
	
	public Provincia deleteProvince(Provincia delP) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE provincia SET estado=? WHERE idProv=?");
			stmt.setBoolean(1, false);
			stmt.setInt(2, delP.getIdProvincia());
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
