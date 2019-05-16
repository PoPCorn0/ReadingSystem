/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.12
 *
 * @Description
 */

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.MessageDao;
import com.snsoft.readingsystem.pojo.Message;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class MessageService {
    @Resource
    MessageDao messageDao;

    /**
     * 向一个或多个学生发送消息通知
     *
     * @param messages 消息通知对象
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView sendMessage(ArrayList<Message> messages) {
        return messageDao.sendMessages(messages) == messages.size() ?
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_SUCCESS) :
                ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED);
    }
}
