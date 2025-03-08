package com.iconpln.ticket.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel implements Serializable{

	private static final long serialVersionUID = 3247696593017334807L;
	
	private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_time", nullable = true, insertable = true, updatable = false)
    @JsonInclude(Include.NON_NULL)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    private String createdBy;

    @Column(name = "created_by", nullable = true, insertable = true, updatable = false, length = 25, precision = 0)
    @JsonInclude(Include.NON_NULL)
    @CreatedBy
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private Date updatedTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time", nullable = true, insertable = true, updatable = true)
    @JsonInclude(Include.NON_NULL)
    @LastModifiedDate
    @CreatedDate
    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    private String updatedBy;

    @Column(name = "updated_by", nullable = true, insertable = true, updatable = true, length = 25, precision = 0)
    @JsonInclude(Include.NON_NULL)
    @LastModifiedBy
    @CreatedBy
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }




}
