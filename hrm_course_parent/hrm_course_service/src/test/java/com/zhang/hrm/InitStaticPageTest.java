package com.zhang.hrm;

import com.zhang.hrm.service.ICourseTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseService9002Application.class)
public class InitStaticPageTest {

    @Autowired
    private ICourseTypeService courseTypeService;
    @Test
    public void testSendMsg() throws Exception{
        courseTypeService.initCourseSiteIndex();
    }
}
