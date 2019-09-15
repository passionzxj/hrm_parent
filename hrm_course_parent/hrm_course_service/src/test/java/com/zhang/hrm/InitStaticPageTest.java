package com.zhang.hrm;

import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.service.ICourseTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseService9002Application.class)
public class InitStaticPageTest {

    @Autowired
    private ICourseTypeService courseTypeService;
    @Test
    public void testSendMsg() throws Exception{
        courseTypeService.initCourseSiteIndex();
    }

    @Test
    public void testGetCrumbs() throws Exception{
        List<Map<String, Object>> crumbs = courseTypeService.getCrumbs(1040L);
        for (Map<String, Object> crumb : crumbs) {
            CourseType ownCourseType = (CourseType) crumb.get("ownCourseType");
            System.out.println("ownCourseType:"+ownCourseType);
            List<CourseType> otherCourseTypes = (List<CourseType>) crumb.get("otherCourseTypes");
            for (CourseType otherCourseType : otherCourseTypes) {
                System.out.println("otherCourseType:"+otherCourseType);
            }
            System.out.println("===========================");
        }
    }
}
