package com.dj.boot.common.util.sign.signUtil;

import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.common.util.sign.signUtil.domain.AppConfig;
import com.dj.boot.common.util.sign.signUtil.domain.CertInfoAreConsistentResVo;
import com.dj.boot.common.util.sign.signUtil.domain.CertOpTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SignTest {

    private static final Logger logger = LoggerFactory.getLogger(SignTest.class);

    public static HashMap<String, AppConfig> signatureMap = new HashMap<String, AppConfig>();


    static {
        try {
            logger.info("--------------读取配置文件开始--------------");
            ResourceBundle signatureConfig = ResourceBundle.getBundle("prop/signature");

            Enumeration<String> keyEnum = signatureConfig.getKeys();

            while (keyEnum.hasMoreElements()) {
                String key = (String) keyEnum.nextElement();
                logger.info("--------------初始化配置信息, appNo_bizNo:{}--------------", key);
                signatureMap.put(key, JsonUtil.fromJson(signatureConfig.getString(key), AppConfig.class));
            }
            logger.info("--------------读取配置文件结束--------------");
        } catch (IOException e) {
            logger.info("--------------读取配置失败--------------");
        }
    }


    public static void main(String[] args) {
        String appNo = "adi";
        String bizNo = "1000";
        String priviteKey = "12456789qwertyuio";
        String token = Md5Utils.encode(appNo + "_" + bizNo + "_" + priviteKey);

        CertInfoAreConsistentResVo certInfoAreConsistentResVo = new CertInfoAreConsistentResVo();
        certInfoAreConsistentResVo.setAppNo(appNo);
        certInfoAreConsistentResVo.setBizNo(bizNo);
        certInfoAreConsistentResVo.setToken(token);
        try {
            testSign(certInfoAreConsistentResVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testSign(CertInfoAreConsistentResVo reqVo) throws Exception {

        CertInfoAreConsistentResVo resVo = validateRequest(new CertInfoAreConsistentResVo(), reqVo, CertOpTypeEnum.IS_CON);

    }



    private static CertInfoAreConsistentResVo validateRequest(CertInfoAreConsistentResVo certInfoAreConsistentResVo, CertInfoAreConsistentResVo reqVo, CertOpTypeEnum opType) throws Exception {
        if(!StringUtils.hasText(reqVo.getAppNo())){
            throw new Exception("appNo为空");
        }

        if(!StringUtils.hasText(reqVo.getBizNo())){
            throw new Exception("appNo为空");
        }

        if(!StringUtils.hasText(reqVo.getToken())){
            throw new Exception("appNo为空");
        }

        if(!isAuthenticated(reqVo.getAppNo(), reqVo.getBizNo(), reqVo.getToken(), opType)){
            throw new Exception("签名失败");
        }

        return certInfoAreConsistentResVo;
    }

    public static boolean isAuthenticated(String appNo, String bizNo, String token, CertOpTypeEnum opType) {
        String _token = "";

        AppConfig ac = signatureMap.get(appNo + "_" + bizNo);

        if(ac != null){
            try {
                _token = Md5Utils.encode(String.format("%s_%s_%s", ac.getAppNo(), ac.getBizNo(), ac.getPrivateKey()));
            } catch (Exception e) {
                _token = "";
                logger.info("[isAuthenticated] get token error "+e.getMessage(),e);
            }

            logger.info("[isAuthenticated]appNo:{}, bizNo:{}, inputToken:{}, token:{}", appNo, bizNo, token, _token);
            if(_token.equalsIgnoreCase(token)){
               // boolean hasContains = ac.getMethodSet().contains(opType.getCode());
                boolean hasContains = ac.methodSet().contains(opType.getCode());
                if(!hasContains){
                    logger.info("[isAuthenticated]当前请求无此操作权限 appNo:{}, bizNo:{}, opType:{}", appNo, bizNo, opType);
                }
                return hasContains;
            }
        }

        return false;
    }
}
