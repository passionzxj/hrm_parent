package com.zhang.hrm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.query.CourseTypeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程目录 Mapper 接口
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-01
 */
public interface CourseTypeMapper extends BaseMapper<CourseType> {

    List<CourseType> loadPageList(Pagination page, @Param("query") CourseTypeQuery query);
}
