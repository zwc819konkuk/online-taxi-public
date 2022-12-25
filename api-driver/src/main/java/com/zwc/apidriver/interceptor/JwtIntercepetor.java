package com.zwc.apidriver.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.zwc.internalcommon.constant.TokenConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.dto.TokenResult;
import com.zwc.internalcommon.utils.JwtUtils;
import com.zwc.internalcommon.utils.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtIntercepetor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");
        //解析token
        TokenResult tokenResult= JwtUtils.checkToken(token);

        if (tokenResult==null){
            resultString = "token invalid";
            result = false;
        }else {
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();

            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
            //从redis中取出token
            String tokenRedis = redisTemplate.opsForValue().get(tokenKey);
            if ((StringUtils.isBlank(tokenRedis))||(!token.trim().equals(tokenRedis.trim()))){
                resultString = "access token invalid";
                result = false;
            }
        }


        if (!result){
            PrintWriter out = response.getWriter();
//            out.print();
            out.write(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }
        return true;
    }
}
