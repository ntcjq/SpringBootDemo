package com.cjq.SpringBootDemo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT
 * token由三部分组成：header.payload.signature
 * header：
 *  {
 *   'typ': 'JWT', //声明类型
 *   'alg': 'HS256' //声明加密的算法 通常直接使用 HMAC SHA256
 *  }
 *
 *
 * payload：
 *      iss: jwt签发者
 *
 *      sub: jwt所面向的用户
 *
 *      aud: 接收jwt的一方
 *
 *      exp: jwt的过期时间，这个过期时间必须要大于签发时间
 *
 *      nbf: 定义在什么时间之前，该jwt都是不可用的.
 *
 *      iat: jwt的签发时间
 *
 *      jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
 * signature:base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分
 */
public class JwtUtil
{
    // 过期时间5分钟
//    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    //过期时间3s
    private static final long EXPIRE_TIME = 3000;

    //这个是放在服务端的  盐
    private static final String secret = "WEWNEFIOSN7W7EB";

    /**
     * 生成签名
     * @param username 用户名
     * @param password 用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String password)
    {
        //查库校验用户名密码
        //TODO

        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).withIssuedAt(new Date()).sign(algorithm);
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String username)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            verifier.verify(token);
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名，过期时间
     */
    public static String getUsername(String token)
    {
        try
        {
            DecodedJWT jwt = JWT.decode(token);
            //获取签发时间
            System.out.println(jwt.getIssuedAt());
            //获取过期时间
            System.out.println(jwt.getExpiresAt());
            return jwt.getClaim("username").asString();
        }
        catch (JWTDecodeException e)
        {
            return null;
        }
    }




    public static void main(String[] args){
        String username = "cjq";
        String password = "000000";
        //使用说明 先组装用户信息 去获取token
        JSONObject userinfo = new JSONObject();
        userinfo.put("username",username);
        userinfo.put("password",password);
        String sha1 = ShaUtils.encode(JSON.toJSONString(userinfo));
        Map<String,String> header = new HashMap();
        header.put("Authorization",sha1);
        //使用上边的信息去获取token，服务端解密 验证用户名密码，成功则返回token，客户端以后请求都带上token，过期返回401，重新再获取

        String token = sign(username, password);

        String u = getUsername(token);

        System.out.println(verify(token,u));

    }
}