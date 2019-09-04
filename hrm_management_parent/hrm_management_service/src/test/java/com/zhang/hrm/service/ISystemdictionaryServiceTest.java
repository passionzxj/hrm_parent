package com.zhang.hrm.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhang.hrm.ManagementService9001Application;
import com.zhang.hrm.domain.Systemdictionary;
import com.zhang.hrm.query.SystemdictionaryQuery;
import com.zhang.hrm.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementService9001Application.class)
public class ISystemdictionaryServiceTest {

    @Autowired
    private ISystemdictionaryService systemdictionaryService;

    @Test
    public void test() throws Exception{
        for (Systemdictionary systemdictionary : systemdictionaryService.selectList(null)) {
            System.out.println(systemdictionary);
        }
    }
    @Test
    public void testGetById() throws Exception{
        System.out.println(systemdictionaryService.selectById(2L));
    }
    @Test
    public void testUpdate() throws Exception{
        Systemdictionary systemdictionary = new Systemdictionary();
        systemdictionary.setId(1L);
        systemdictionary.setName("sssss");
        systemdictionaryService.updateById(systemdictionary);
    }
    @Test
    public void testDel() throws Exception{
        systemdictionaryService.deleteById(1L);
    }

    @Test
    public void testJson() throws Exception{
        SystemdictionaryQuery query = new SystemdictionaryQuery();
        query.setPage(1);
        query.setPageSize(3);
        Page<Systemdictionary> page = new Page<Systemdictionary>(query.getPage(), query.getPageSize());
        page = systemdictionaryService.selectPage(page);
        PageList<Systemdictionary> pageList = new PageList<>(page.getTotal(),page.getRecords());
        System.out.println(pageList.getTotal());
        for (Systemdictionary row : pageList.getRows()) {
            System.out.println(row);
        }

    }
}