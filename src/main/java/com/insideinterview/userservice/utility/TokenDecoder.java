package com.insideinterview.userservice.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import static com.insideinterview.userservice.utility.CustomEncoderAlgorithm.algorithm;

public class TokenDecoder {
    public static DecodedJWT getDecodedJWT(String token) {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
    }
}