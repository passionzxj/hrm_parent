package com.zhang.hrm.service;

import com.zhang.hrm.doc.Escourse;
import com.zhang.hrm.query.EscourseQuery;
import com.zhang.hrm.util.PageList;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-05
 */
public interface IEscourseService {

    void updateById(Escourse escourse);

    void insert(Escourse escourse);

    void deleteById(Long id);

    Escourse selectById(Long id);

    List<Escourse> selectList(Object o);

    PageList<Escourse> selectListPage(EscourseQuery query);

    void batchOnline(List<Escourse> esList);

    void batchOffline(List<Escourse> esList);
}
