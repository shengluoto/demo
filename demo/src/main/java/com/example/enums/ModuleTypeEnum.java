package com.example.enums;


/**
 * 模块名称(数据字典+0用户、5人员、3部门、2角色、1讨论组、6公司、4职务)
 * @author
 *	2018-03-08 athena 创建
 */
public enum ModuleTypeEnum {
    USER("0", "用户"),
    TEAM("1", "讨论组"),
    ROLE("2", "角色"),
    DEPARTMENT("3", "部门"),
    POSITION("4", "职务"),
    PERSON("5", "人员"),
    COMPANY("6", "公司"),

    ;

    private String code;

    private String message;

    ModuleTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
	public static ModuleTypeEnum getByValue(String value) {
        for (ModuleTypeEnum typeEnum : values()) {  
            if (typeEnum.getCode().equals(value)) {  
                return typeEnum;
            }  
        }  
        return null;  
    } 
}