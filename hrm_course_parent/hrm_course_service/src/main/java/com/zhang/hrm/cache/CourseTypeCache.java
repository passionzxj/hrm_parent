package com.zhang.hrm.cache;

import com.alibaba.fastjson.JSONArray;
import com.zhang.hrm.client.RedisClient;
import com.zhang.hrm.domain.CourseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseTypeCache {

    private static final String TYPE_TREE = "type_tree";

    @Autowired
    private RedisClient redisClient;

    /**
     * 如果redis缓存中有相应的数据
     * 通过key获得json字符串
     * 再转化为对象集合返回
     * @return
     */
    public List<CourseType> getCourseTypes(){
        String treeData = redisClient.get(TYPE_TREE);
        return JSONArray.parseArray(treeData,CourseType.class);
    }

    /**
     * 如过redis中没有相应的数据
     * 把从数据库中查询出来的数据放入redis缓存中
     * @param courseTypesFromDb
     */
    public void setCourseTypes(List<CourseType> courseTypesFromDb){
        String jsonString = JSONArray.toJSONString(courseTypesFromDb);
        redisClient.set(TYPE_TREE,jsonString);
    }
}
