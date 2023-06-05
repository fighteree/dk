package com.yzdbx.dkpan.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component //spring管理对象
@Slf4j
public class JwtUtil {
    //定义密钥
    private final String SECRET_KEY = "dwa68d61dwa3dsd1waaa66ggjhqdsguyyutiihiuyiugigityr6eduyg";
    //定义保存用户账号使用key名称，密码代码冗余
    private final String ACCOUNT_KEY = "account";

    /**
     * 根据账号信息生成token字符串
     *
     * @param account 登录的账户名
     * @return
     */
    public String createToken(String account) {
        try {
            //定义头信息
            JWSHeader header = new JWSHeader
                    .Builder(JWSAlgorithm.HS256)  //指定加密算法
                    .type(JOSEObjectType.JWT)  //实现方式
                    .build(); //jwt生成方案
            log.info("token的header信息如下：" + header.toString());//看看head生成结果

            //1-2 定义荷载
            Map<String, Object> map = new HashMap<>();
            map.put(ACCOUNT_KEY, account);//账户信息
            //增强荷载信息的复杂度，好处：让同一个账户每次登录拿到的token都不一样
            map.put("now", System.currentTimeMillis());
            Payload payLoad = new Payload(map);
            log.info("token的payLoad信息如下：" + payLoad.toString());

            //1-3 生成签名，颁发token
            //1-3-1 定义密钥
            JWSSigner jwsSigner = new MACSigner(SECRET_KEY);  //MACSigner()中要指定一个密钥
            //1-3-2 生成token
            JWSObject jwsObject = new JWSObject(header, payLoad);//组合头和荷载
            //生成签名
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();//序列化：
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证用户的携带的token是否合法
     *
     * @param token 请求协议携带token
     * @return
     */
    public boolean verify(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY);//提供密钥
            return jwsObject.verify(verifier);
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 基于验证通过token，获取荷载里面保存用户信息
     *
     * @return
     */
    public String getInfoFromToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();//获取荷载，荷载实际存入数据格式：map
            Map<String, Object> map = payload.toJSONObject();
            return (String) map.get(ACCOUNT_KEY);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
