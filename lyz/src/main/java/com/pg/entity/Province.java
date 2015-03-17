package com.pg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.entity.AbstractModel;

/**
 * 城市
 * @author 李喻哲
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "MC_PROVINCE")
public class Province extends AbstractModel{
	
	@Id
	private Long id;
	
	@Column
	private String proName;
	@Column
	private Integer proSort;
	@Column
	private String proRemark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Integer getProSort() {
		return proSort;
	}
	public void setProSort(Integer proSort) {
		this.proSort = proSort;
	}
	public String getProRemark() {
		return proRemark;
	}
	public void setProRemark(String proRemark) {
		this.proRemark = proRemark;
	}
}