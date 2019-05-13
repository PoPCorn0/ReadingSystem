/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.10
 *
 * @Description
 */

package com.snsoft.readingsystem.dao;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentDao {

    @Delete("delete from attachment where id = #{id}")
    public void deleteAttachment(String id);
}
