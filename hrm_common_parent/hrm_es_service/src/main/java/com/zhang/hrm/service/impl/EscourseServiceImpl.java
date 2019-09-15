package com.zhang.hrm.service.impl;

import com.zhang.hrm.doc.Escourse;
import com.zhang.hrm.query.EscourseQuery;
import com.zhang.hrm.repository.EsCourseRepository;
import com.zhang.hrm.service.IEscourseService;
import com.zhang.hrm.util.PageList;
import org.apache.commons.lang.StringUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
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
     *
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
        builder.withPageable(PageRequest.of(query.getPage() - 1, query.getPageSize()));
        //字段截取
//        builder.withSourceFilter(new FetchSourceFilter())
        //构造查询条件
        NativeSearchQuery esQuery = builder.build();
        //查询
        Page<Escourse> escoursePage = repository.search(esQuery);
        return new PageList<>(escoursePage.getTotalElements(), escoursePage.getContent());
    }

    @Override
    public void batchOnline(List<Escourse> esList) {
        repository.saveAll(esList);
    }

    @Override
    public void batchOffline(List<Escourse> esList) {
        repository.deleteAll(esList);
    }

    //页面分页+高级
    @Override
    public PageList<Map<String, Object>> esQuery(Map<String, Object> params) {
        // keyword CourseyType priceMin priceMax sortField sortType page rows
        String keyword = (String) params.get("keyword"); //查询
        String sortField = (String) params.get("sortField"); //排序
        String sortType = (String) params.get("sortType");//排序

        Long courseType = params.get("CourseType") != null ? Long.valueOf(params.get("CourseType").toString()) : null;//过滤
        Long priceMin = params.get("priceMin") != null ? Long.valueOf(params.get("priceMin").toString()) * 100 : null;//过滤
        Long priceMax = params.get("priceMax") != null ? Long.valueOf(params.get("priceMax").toString()) * 100 : null;//过滤
        Long page = params.get("page") != null ? Long.valueOf(params.get("page").toString()) : null; //分页
        Long rows = params.get("rows") != null ? Long.valueOf(params.get("rows").toString()) : null;//分页

        //构建器
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //设置查询条件=查询+过滤
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(keyword)) {
            boolQuery.must(QueryBuilders.matchQuery("all", keyword));
        }
        List<QueryBuilder> filter = boolQuery.filter();
        if (courseType != null) { //类型
            System.out.println(courseType + "jjjjjjjjjjjjjjjjjjj");
            filter.add(QueryBuilders.termQuery("courseTypeId", courseType));
        }
        //最大价格 最小价格
        //minPrice <= priceMax && maxPrice>=priceMin
        if (priceMax != null && priceMin != null) {
            filter.add(QueryBuilders.rangeQuery("price").gte(priceMin).lte(priceMax));
        }
        builder.withQuery(boolQuery);
        //排序
        SortOrder defaultSortOrder = SortOrder.DESC;
        if (StringUtils.isNotBlank(sortField)) {//销量 新品 价格 人气 评论
            //如果传入的不是降序改为升序
            if (StringUtils.isNotBlank(sortType) && !sortType.equals(SortOrder.DESC)) {
                defaultSortOrder = SortOrder.ASC;
            }
            // 价格  索引库有两个字段 最大,最小
            //如果用户按照升序就像买便宜的,就用最小价格,如果用户按照降序想买贵的,用最大价格
            if (sortField.equals("jg")) {
                builder.withSort(SortBuilders.fieldSort("price").order(defaultSortOrder));
            }
        }
        //分页
        Long pageTmp = page - 1; //从0开始
        builder.withPageable(PageRequest.of(pageTmp.intValue(), rows.intValue()));
        //截取字段 @TODO
        //封装数据
        Page<Escourse> CourseDocs = repository.search(builder.build());
        List<Map<String, Object>> datas = esCourses2ListMap(CourseDocs.getContent());
        return new PageList<>(CourseDocs.getTotalElements(), datas);
    }

    private List<Map<String, Object>> esCourses2ListMap(List<Escourse> content) {
        List<Map<String,Object>> result = new ArrayList<>();
        for (Escourse esCourse : content){
            result.add(esCourse2Map(esCourse));
        }
        return result;
    }

    private Map<String, Object> esCourse2Map(Escourse esCourse) {
        Map<String,Object> result = new HashMap<>();
        result.put("id", esCourse.getId());
        result.put("name", esCourse.getName());
        result.put("users", esCourse.getUsers());
        result.put("courseTypeId", esCourse.getCourseTypeId());
        result.put("courseTypeName", esCourse.getCourseTypeName());
        result.put("gradeId", esCourse.getGradeId());
        result.put("gradeName", esCourse.getGradeName());
        result.put("status", esCourse.getStatus());
        result.put("tenantId", esCourse.getTenantId());
        result.put("tenantName", esCourse.getTenantName());
        result.put("userId", esCourse.getUserId());
        result.put("userName", esCourse.getUserName());
        result.put("startTime", esCourse.getStartTime());
        result.put("endTime", esCourse.getEndTime());
        result.put("expires", esCourse.getExpires());
        result.put("priceOld", esCourse.getPriceOld());
        result.put("price", esCourse.getPrice());
        result.put("intro", esCourse.getIntro());
        result.put("qq", esCourse.getQq());
        result.put("resources", esCourse.getResources());
        return result;
    }

}
