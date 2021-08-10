package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import javax.naming.AuthenticationException;

import org.mindrot.jbcrypt.BCrypt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import data.PersonaData;
import entities.Persona;

public class PersonaLogic {

	PersonaData pd;
	public PersonaLogic(){
		pd = new PersonaData();
	}
	
	public LinkedList<Persona> getAll() throws SQLException, IOException{
		LinkedList<Persona> personas = pd.getAll();
		
		return personas;
	}
	
	public Persona getOne(Persona p) throws SQLException, IOException{
		Persona persona = pd.findByDni(p);
		
		return persona;
	}
	
	public Persona login(Persona p) throws SQLException, IOException, AuthenticationException{
		Persona persona = pd.findByEmail(p);
		if(BCrypt.checkpw(p.getPassword(), persona.getPassword())){
			try {
			    Algorithm algorithm = Algorithm.HMAC256("secret");
			    String token = JWT.create()
			        .withIssuer("auth0")
			        .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
			        .sign(algorithm);
			    persona.setToken(token);
			    persona.setPassword(null);
			} catch (JWTCreationException e){
				throw new IOException();
			}
		}else{
			throw new AuthenticationException();
		};
		
		return persona;
	}
	
	public Persona newUser(Persona p) throws SQLException, IOException{
		String passHash = BCrypt.hashpw(p.getPassword(), BCrypt.gensalt(10));
		p.setPassword(passHash);
		Persona persona = pd.newUser(p);
		
		return persona;
	}
	
		
	public Persona updateUser(Persona p) throws SQLException, IOException{
		String passHash = BCrypt.hashpw(p.getPassword(), BCrypt.gensalt(10));
		p.setPassword(passHash);
		Persona persona = pd.updateUser(p);
		
		return persona;
	}
	
	public Persona deleteUser(Persona p) throws SQLException, IOException{
		Persona persona = pd.deleteUser(pd.findByDni(p));
		
		return persona;
	}
}
