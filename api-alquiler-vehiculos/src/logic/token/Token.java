package logic.token;

import javax.ws.rs.NotAuthorizedException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Token {

	public static void getToken(String token){
    	try {
    		Algorithm algorithm = Algorithm.HMAC256("secret");
    		JWTVerifier verifier = JWT.require(algorithm)
    				.withIssuer("auth0")
    				.build();
    		DecodedJWT jwt = verifier.verify(token);
		} catch (TokenExpiredException e) {
			throw new NotAuthorizedException("Unauthorized");
		} catch (SignatureVerificationException e){
			throw new NotAuthorizedException("Unauthorized");
		} catch (JWTDecodeException e){
			throw new NotAuthorizedException("Unauthorized");
		}
	}
	
}

