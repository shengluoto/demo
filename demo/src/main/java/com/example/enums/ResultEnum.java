package com.example.enums;

public enum ResultEnum {
	UNKONW_ERROR(-1,"未知错误！请联系系统管理员！"),
	SUCCESS(1,"操作成功！"),
	SAVE_SUCCESS(2,"保存成功！"),
	SELECT_SUCCESS(3,"查询成功！"),
	SELECT_NULL(4,"不存在查询数据！"),
	REDIS_NULL(5,"redis不存在查询数据！登录信息失效，请重新登录！"),
	TOKEN_NULL(6,"token为空！登录信息失效，请重新登录！"),
	USERID_NULL(7,"用户id为空！"),
	USER_ERROR(8,"用户信息参数不正确！"),
	PASSWORD_ERROR(9,"密码不正确！"),
	TOKEN_ERROR(10,"token验证失败！请重新登录！"),
	FORMAT_ERROR(11,"传值格式错误，请检查输入信息！"),
	SAVEDATA_NULL(12,"保存数据不能为空！"),
	DATA_ERROR(13,"数据异常，请联系管理员！"),
	NUMBER_SAMEERROR(14,"已存在相同编码的数据，请勿重复添加！"),
	NAME_SAMEERROR(15,"已存在相同名称的数据，请勿重复添加！"),
	ID_NULL(16,"数据id为空！"),
	DATA_NULL(17,"该数据不存在！"),
	DATA_DELETED(18,"该数据已被删除！"),
	DATA_DISABLED(19,"该数据已被禁用！"),
	DATA_ENABLED(19,"该数据已被启用！"),
	UPDATEDATA_NULL(20,"修改数据不能为空！"),
	UPDATEDATA_REFERED(21,"该数据已被引用，不能修改！"),
	DELETEDATA_REFERED(22,"该数据已被引用，不能删除！"),
	JSONSTR_NULL(33,"传值为空！"),
    ;
	//信息对应码
	private Integer msgCode;
	
	//提示信息
	private String message;
	
	private ResultEnum(Integer msgCode, String message) {
		this.msgCode = msgCode;
		this.message = message;
	}

	public Integer getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
