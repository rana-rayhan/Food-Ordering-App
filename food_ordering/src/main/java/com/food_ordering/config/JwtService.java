package com.food_ordering.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    public static final String SECRECT_KEY = "testSecrectKeysjhakjhsgfkafkbsfkabsfjgbagaginxakakdoifhnxhlkadadlmasdmsanah";

    // extract user email
    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    // extract single claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // generate token with single parameter
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // generate token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        String gToken = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();

        return gToken;
    }

    // is token valid
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);

        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpaired(token));
    }

    private boolean isTokenExpaired(String token) {
        return extractExparation(token).before(new Date());
    }

    private Date extractExparation(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // all claims extract methdo
    private Claims extractAllClaims(String token) {

        Claims myClaims = Jwts.parserBuilder()
                .setSigningKey(getSiginKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return myClaims;
    }

    // creating secrect key
    private Key getSiginKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRECT_KEY);

        return Keys.hmacShaKeyFor(keyByte);
    }

}
