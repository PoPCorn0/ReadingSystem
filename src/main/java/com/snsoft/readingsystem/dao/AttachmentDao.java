/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.21
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import com.snsoft.readingsystem.pojo.Attachment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentDao {

    // 添加一条附件记录
    @Insert("insert into attachment (id, rely_on_id, author_id, save_path, size, file_name) " +
            "values (#{id}, #{relyOnId}, #{authorId}, #{savePath}, #{size}, #{fileName})")
    void addAttachment(Attachment attachment);

    // 根据id查询附件记录
    @Select("select id, rely_on_id, author_id, save_path, size, file_name from attachment where id = #{id}")
    Attachment getAttachmentById(String id);

    // 删除附件记录
    @Delete("delete from attachment where id = #{id}")
    void deleteAttachmentByRelyOnId(String id);

    // 根据附件依赖的任务或解读id查询附件记录
    @Select("select id, rely_on_id, author_id, save_path, size, file_name from attachment where rely_on_id = #{id}")
    Attachment getAttachmentByRelyOnId(String id);
}
