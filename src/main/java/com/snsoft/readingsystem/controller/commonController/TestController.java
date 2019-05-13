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

package com.snsoft.readingsystem.controller.commonController;

import com.snsoft.readingsystem.dao.TaskDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TestController {
    @Resource
    TaskDao taskDao;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void logout(@RequestBody List<String> receiver) {
        System.out.println(receiver.size());
//        ArrayList<StuTask> list = new ArrayList<>();
//        StuTask stuTask1 = new StuTask();
//        stuTask1.setStudentId("student1");
//        stuTask1.setTaskId("task1");
//
//        StuTask stuTask2 = new StuTask();
//        stuTask2.setStudentId("student2");
//        stuTask2.setTaskId("task1");
//
//        StuTask stuTask3 = new StuTask();
//        stuTask3.setStudentId("student3");
//        stuTask3.setTaskId("task1");
//
//        list.add(stuTask1);
//        list.add(stuTask2);
//        list.add(stuTask3);
//
//        int task1 = taskDao.addReceiver(list);
//        System.out.println(task1);
    }
}
