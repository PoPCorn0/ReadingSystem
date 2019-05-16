/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.15
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.Attachment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentDao {

    // 添加一条附件记录
    @Insert("insert into attachment (id, rely_on_id, author_id, save_path, size, file_name) " +
            "values (#{id}, #{relyOnId}, #{authorId}, #{savePath}, #{size}, #{fileName})")
    public void addAttachment(Attachment attachment);

    // 根据id查询附件记录
    @Select("select id, rely_on_id, author_id, save_path, size, file_name from attachment where id = #{id}")
    public Attachment getAttachment(@Param("id") String id);

    // 删除附件记录
    @Delete("delete from attachment where id = #{id}")
    public void deleteAttachment(String id);
}
