package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Alquiler;
import entities.Extra;
import entities.Rol;

public class ExtraData {
	
	public LinkedList<Extra> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Extra> extras = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM extra");
			
				while(rs.next()){
					Extra c = new Extra();
					c.setIdExtra(rs.getInt("idExtra"));
					c.setDescripcion(rs.getString("descripcion"));
					c.setPrecioDia(rs.getDouble("precioDia"));
					c.setEstado(rs.getBoolean("estado"));
					extras.add(c);
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
		
		return extras;
	}
	
	public Extra findById(Extra c) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM extra WHERE idExtra=?");
			stmt.setInt(1, c.getIdExtra());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				c = new Extra();
				c.setIdExtra(rs.getInt("idExtra"));
				c.setDescripcion(rs.getString("descripcion"));
				c.setPrecioDia(rs.getDouble("precioDia"));
				c.setEstado(rs.getBoolean("estado"));
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
		
		return c;
	}
	
	public Extra newExtra(Extra nuevoE) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO extra(descripcion, precioDia) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nuevoE.getDescripcion());
			stmt.setDouble(2, nuevoE.getPrecioDia());
			stmt.executeUpdate();
			key = stmt.getGeneratedKeys();
			if(key != null && key.next()) {
				nuevoE.setIdExtra(key.getInt(1));
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
		
		return nuevoE;
	}
	
	public Extra updateExtra(Extra actE) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE extra SET descripcion=?, precioDia=? WHERE idExtra=?");
			stmt.setString(1, actE.getDescripcion());
			stmt.setDouble(2, actE.getPrecioDia());
			stmt.setInt(3, actE.getIdExtra());
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
		
		return actE;
	}
	
	public Extra deleteExtra(Extra delE) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE extra SET estado=? WHERE idExtra=?");
			stmt.setBoolean(1, false);
			stmt.setInt(2, delE.getIdExtra());
			stmt.executeUpdate();
			
			delE.setEstado(false);
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
		
		return delE;
	}
	
	public void newAlquilerExtra(Alquiler alquiler) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO alquiler_extra(dni, fechaHoraInicio, idExtra) VALUES(?, ?, ?)");
			stmt.setString(1, alquiler.getDni());
			stmt.setTimestamp(2, Timestamp.valueOf(alquiler.getFechaHoraInicio()));
			for(Extra extra: alquiler.getExtras()){
				stmt.setInt(3, extra.getIdExtra());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
