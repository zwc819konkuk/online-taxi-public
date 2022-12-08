package com.zwc.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zwc.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class JwtUtils {

    //盐
    private static final String SIGN = "zwc991";

    private static final String JWT_KEY_PHONE = "passengerPhone";
    //乘客是1 司机是2
    private static final String JWT_KEY_IDENTITY = "identity";

    //token类型
    private static final String JWT_TOKEN_TYPE = "token_type";

    private static final String JWT_TOKEN_TIME = "tokenTime";

    //生成token
    public static String generatorToken(String passengerPhone,String identity,String tokenType){
        HashMap<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        //token过期时间
        map.put(JWT_TOKEN_TIME,Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();

        map.forEach
                ((k,v)->{
                     builder.withClaim(k,v);
                }
        );

        //整合过期时间
//        builder.withExpiresAt(date);

        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    //解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setIdentity(identity);
        tokenResult.setPhone(phone);
        return tokenResult;
    }

    //检查token，判断token是否异常
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try {
            tokenResult= JwtUtils.parseToken(token);
        }catch (Exception e){

        }
        return tokenResult;
    }
    public static void main(String[] args) {

//        String s = generatorToken("13910733521","1");
//        System.out.println("生成的token："+s);
//        System.out.println("解析后的token"+parseToken(s));
    }
}
