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

package com.snsoft.readingsystem.controller.teacherController;

import com.snsoft.readingsystem.dao.UserDao;
import com.snsoft.readingsystem.pojo.Message;
import com.snsoft.readingsystem.pojo.Student;
import com.snsoft.readingsystem.service.MessageService;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class MessageController {

    @Resource
    MessageService messageService;
    @Resource
    UserDao userDao;

    /**
     * 导师向学生批量发送消息通知
     *
     * @param content  消息内容
     * @param receiver 接受者id列表
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/teacher/sendMessage", method = RequestMethod.POST)
    public ModelAndView sendMessage(@RequestParam("content") String content,
                                    @RequestBody List<String> receiver) {
        ArrayList<Message> messages = new ArrayList<>();
        for (String s : receiver
        ) {
            Student student = userDao.getStudentByIdNotRemoved(s);
            if (student == null) {
                return ModelAndViewUtil.getModelAndView(AllConstant.CODE_FAILED, "学生id：" + s + " 不存在");
            }

            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setContent(content);
            message.setTargetId(s);
            messages.add(message);
        }

        try {
            return messageService.sendMessage(messages);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.getModelAndView(AllConstant.CODE_ERROR);
        }
    }
}
