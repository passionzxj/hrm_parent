package com.zhang.hrm;

import com.zhang.hrm.domain.Sso;
import com.zhang.hrm.util.StrUtils;
import com.zhang.hrm.util.encrypt.MD5;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = User9008Application.class)
public class Md5Test {

    //注册时加密保存
    @Test
    public void testRegMd5En() throws Exception {

        String salt = StrUtils.getComplexRandomString(16);
        String secretyPwd = MD5.getMD5("zhang" + salt);
        //加密的秘密和盐值都要存放到数据库
//        87bvuKUlagC1PesR
//        9847e0a7592956ef334a4c65295aef38

        System.out.println(salt);
        System.out.println(secretyPwd);
    }

    //登录时比对,也就是把输入的秘密加密和数据库查询出来的进行比对
    @Test
    public void testLonginEqual() throws Exception {

        String username = "xxx";
        String pwd = "zhang";
        //通过用户名从数据库查询sso
        Sso sso = new Sso();
        sso.setSalt("87bvuKUlagC1PesR");
        sso.setPassword("9847e0a7592956ef334a4c65295aef38");

        String inputSecrutyPwd = MD5.getMD5(pwd + sso.getSalt());

        if (inputSecrutyPwd.equals(sso.getPassword())) {
            System.out.println("登录成功!!!!!!!!!!!!!!!!!");
        }
    }
}