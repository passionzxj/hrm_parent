package com.zhang.hrm.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-03
 */
@TableName("t_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String users;
    @TableField("course_type_id")
    private Long courseTypeId;
    @TableField(exist = false)
    private CourseType courseType;
    private Long grade;
    private Integer status;
    @TableField("tenant_id")
    private Long tenantId;
    private String tenantName;
    @TableField("user_id")
    private Long userId;
    private String userName;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;

    @TableField(exist = false)
    private CourseDetail detail;

    public CourseDetail getDetail() {
        return detail;
    }

    public void setDetail(CourseDetail detail) {
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public Long getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Long courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Course{" +
        ", id=" + id +
        ", name=" + name +
        ", users=" + users +
        ", courseTypeId=" + courseTypeId +
        ", grade=" + grade +
        ", status=" + status +
        ", tenantId=" + tenantId +
        ", tenantName=" + tenantName +
        ", userId=" + userId +
        ", userName=" + userName +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        "}";
    }
}
