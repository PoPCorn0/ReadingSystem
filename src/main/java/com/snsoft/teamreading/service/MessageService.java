/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.08.22
 *
 * @Description
 */

package com.snsoft.teamreading.service;

import com.snsoft.teamreading.dao.MessageDao;
import com.snsoft.teamreading.enums.Code;
import com.snsoft.teamreading.pojo.Message;
import com.snsoft.teamreading.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class MessageService {
    @Resource
    MessageDao messageDao;
    @Resource
    ModelAndView mv;

    /**
     * 向一个或多个学生发送消息通知
     *
     * @param messages 消息通知对象
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView sendMessage(ArrayList<Message> messages) {
        return messageDao.sendMessages(messages) == messages.size() ?
                ModelAndViewUtil.addObject(mv, Code.SUCCESS) :
                ModelAndViewUtil.addObject(mv, Code.FAIL);
    }
}
