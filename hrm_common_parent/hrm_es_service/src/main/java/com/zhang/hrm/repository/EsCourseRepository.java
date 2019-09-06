package com.zhang.hrm.repository;

import com.zhang.hrm.doc.Escourse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsCourseRepository extends ElasticsearchRepository<Escourse,Long> {
}
