package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_user")
public class User extends CommonFields {
	@ApiModelProperty(value="登陆名")
	@Column(name="loginName_")
	private String loginName;			//登陆名
	
	@ApiModelProperty(value="姓名")
	@Column(name="userName_")
	private String userName;			//姓名
	
	@ApiModelProperty(value="密码")
	@Column(name="password_")
	private String password;            //密码
	
	@ApiModelProperty(value="手机号")
	@Column(name="phone_")
	private String phone;				//手机号
	
	@ApiModelProperty(value="邮箱")
	@Column(name="email_")
	private String email;				//邮箱
}