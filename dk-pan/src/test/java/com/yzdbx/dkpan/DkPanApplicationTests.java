package com.yzdbx.dkpan;

import com.yzdbx.dkpan.controller.UserInfoController;
import com.yzdbx.dkpan.controller.api.ApiUserInfo;
import com.yzdbx.dkpan.util.EmailUtil;
import com.yzdbx.dkpan.util.VerifyCodeUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
@SpringBootTest
class DkPanApplicationTests {
    @Autowired
    private UserInfoController userInfoController;

    @Test
    void contextLoads() {
        VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil();
        verifyCodeUtil.generateCode();
        String text = verifyCodeUtil.getText();
        System.out.println(text);
    }
    @Test
    void testMail() throws NoSuchMethodException {
        Tag annotation = userInfoController.getClass().getAnnotation(Tag.class);
        System.out.println(annotation);
    }

}
