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

package com.snsoft.teamreading.controller.teacherController;

import com.snsoft.teamreading.dao.UserDao;
import com.snsoft.teamreading.enums.Code;
import com.snsoft.teamreading.pojo.Message;
import com.snsoft.teamreading.pojo.Student;
import com.snsoft.teamreading.service.MessageService;
import com.snsoft.teamreading.utils.ModelAndViewUtil;
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

@Controller("TeacherMessageController")
@RequestMapping("teacher")
public class MessageController {

    @Resource
    MessageService messageService;
    @Resource
    UserDao userDao;
    @Resource
    ModelAndView mv;

    /**
     * 导师向学生批量发送消息通知
     *
     * @param content  消息内容
     * @param receiver 接受者id列表
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ModelAndView sendMessage(@RequestParam("content") String content,
                                    @RequestBody List<String> receiver) {
        ArrayList<Message> messages = new ArrayList<>();
        for (String s : receiver
        ) {
            Student student = userDao.getStudentByIdNotRemoved(s);
            if (student == null) {
                return ModelAndViewUtil.addObject(mv, Code.FAIL, "学生id：" + s + " 不存在");
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
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
