package com.zhang.hrm.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-02
 */
@TableName("t_tenant")
public class Tenant extends Model<Tenant> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String companyName;
    private String companyNum;
    private Date registerTime = new Date();
    //审核状态 0:审核中,1:成功,-1:失败
    private Integer state = 0;
    private String address;
    private String logo;
    //租户管理员
    @TableField(exist = false)
    private Employee adminUser;
    //租户与套餐多对多关系
    @TableField(exist = false)
    private List<Meal> meals = new ArrayList<>();
    //机构类型
    @TableField("tenantType_id")
    private Long tenantTypeId;
    @TableField(exist = false)
    private TenantType tenantType;

    //维护租户和套餐间的中间表
    public List<Map<String, Long>> getTenantMeal() {
        List<Map<String, Long>> list = new ArrayList<>();
        if (meals.size() > 0) {
            for (Meal meal : meals) {
                Map<String, Long> map = new HashMap<>();
                map.put("mealId", meal.getId());
                map.put("tenantId", this.getId());
                list.add(map);
            }
            System.out.println(list);
        }
        return list;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Employee getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(Employee adminUser) {
        this.adminUser = adminUser;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Long getTenantTypeId() {
        return tenantTypeId;
    }

    public void setTenantTypeId(Long tenantTypeId) {
        this.tenantTypeId = tenantTypeId;
    }

    public TenantType getTenantType() {
        return tenantType;
    }

    public void setTenantType(TenantType tenantType) {
        this.tenantType = tenantType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                ", id=" + id +
                ", companyName=" + companyName +
                ", companyNum=" + companyNum +
                ", registerTime=" + registerTime +
                ", state=" + state +
                ", address=" + address +
                ", logo=" + logo +
                "}";
    }
}
