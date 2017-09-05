package com.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.entity.AccessToken;

public interface AccessTokenMapper {
    @Insert("INSERT INTO tb_wx_accessToken(accessToken, createTime) values(#{accessToken},now())")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int insert(@Param("accessToken") String accessToken);
  
    @Select("SELECT at.* FROM tb_wx_accessToken at where createTime=(SELECT max(createTime) from tb_wx_accessToken)")
    public AccessToken getLatestAccessToken();
}

