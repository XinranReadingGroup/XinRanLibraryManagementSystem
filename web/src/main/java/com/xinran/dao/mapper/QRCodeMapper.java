package com.xinran.dao.mapper;

import com.xinran.pojo.QRCode;
import com.xinran.pojo.User;
import org.apache.ibatis.annotations.*;

public interface QRCodeMapper {


	@Select("SELECT * FROM qrcode WHERE id = #{id}")
     QRCode findQRCodeById(@Param("id") Long id);

    @Select("SELECT * FROM qrcode WHERE content = #{content}")
     QRCode findQRCodeByContent(@Param("content") String content);


    @Insert("insert into qrcode (created_At, updated_At,type,content,status) values(now(),now(),#{type},#{content}, #{status})")
    @Options(useGeneratedKeys = true)
    void addQRCode(QRCode qrCode);
    
    @Update("update  qrcode set  updated_At = #{updatedAt},status = "+QRCode.USERD+" where id = #{id} and status = "+QRCode.NOT_USERD )
    int associate(QRCode qrCode);

}
