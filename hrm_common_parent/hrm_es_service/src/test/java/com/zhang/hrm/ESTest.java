package com.zhang.hrm;

import com.zhang.hrm.doc.Escourse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ES9004Application.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void test() throws Exception{
        template.createIndex(Escourse.class);
        template.putMapping(Escourse.class);

    }
}
