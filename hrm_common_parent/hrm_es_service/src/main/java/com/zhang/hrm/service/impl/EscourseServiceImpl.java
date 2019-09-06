package com.zhang.hrm.service.impl;

import com.zhang.hrm.doc.Escourse;
import com.zhang.hrm.query.EscourseQuery;
import com.zhang.hrm.repository.EsCourseRepository;
import com.zhang.hrm.service.IEscourseService;
import com.zhang.hrm.util.PageList;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-05
 */
@Service
public class EscourseServiceImpl implements IEscourseService {

    @Autowired
    private EsCourseRepository repository;

    @Override
    public void updateById(Escourse escourse) {
        repository.save(escourse);
    }

    @Override
    public void insert(Escourse escourse) {
        repository.save(escourse);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Escourse selectById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Escourse> selectList(Object o) {
        return null;
    }

    /**
     * DSL的分页查询
     * @param query
     * @return
     */
    @Override
    public PageList<Escourse> selectListPage(EscourseQuery query) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        //模糊查询
        bool.must(QueryBuilders.matchAllQuery());
        //过滤查询
        List<QueryBuilder> filter = bool.filter();
        filter.add(QueryBuilders.rangeQuery("age").gte(20).lte(40));
        //query结构
        builder.withQuery(bool);
        //排序
        builder.withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC));
        //分页
        builder.withPageable(PageRequest.of(query.getPage()-1,query.getPageSize()));
        //字段截取
//        builder.withSourceFilter(new FetchSourceFilter())
        //构造查询条件
        NativeSearchQuery esQuery = builder.build();
        //查询
        Page<Escourse> escoursePage = repository.search(esQuery);
        return new PageList<>(escoursePage.getTotalElements(),escoursePage.getContent());
    }

    @Override
    public void batchOnline(List<Escourse> esList) {
        repository.saveAll(esList);
    }

    @Override
    public void batchOffline(List<Escourse> esList) {
        repository.deleteAll(esList);
    }
}
