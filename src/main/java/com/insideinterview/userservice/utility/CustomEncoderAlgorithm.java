package com.insideinterview.userservice.utility;

import com.auth0.jwt.algorithms.Algorithm;

public class CustomEncoderAlgorithm {
    protected final static Algorithm algorithm = Algorithm.HMAC256("username".getBytes());
}
