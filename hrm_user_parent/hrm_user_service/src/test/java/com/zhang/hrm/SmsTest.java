package com.zhang.hrm;



import com.zhang.hrm.util.SmsHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = User9008Application.class)
public class SmsTest {

    @Autowired
    private SmsHelper smsHelper;
    @Test
    public void test() throws Exception{
        smsHelper.sendSms("15882346334","1",new String[]{"9999","3"});
    }
}
