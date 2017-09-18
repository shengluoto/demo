package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Table(name = "tb_wx_accessToken")
@Entity
public class AccessDDToken {
    @Id
//    @GenericGenerator(name="uuid",strategy="uuid")
//    @GeneratedValue(generator="uuid")
//    @Column(name = "id", unique = true, nullable = false)
    private String id;
    private String accessToken;
    private Date createTime;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Override
    public String toString() {
        return "AccessToken [id=" + id + ", accessToken=" + accessToken + ", createTime="
                + createTime + "]";
    }
    
}
