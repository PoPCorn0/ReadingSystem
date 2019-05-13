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

import com.alibaba.fastjson.JSON;
import com.snsoft.readingsystem.pojo.Message;

import java.util.ArrayList;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setTargetId("student1");
            message.setContent("content");
            message.setId(UUID.randomUUID().toString());
            messages.add(message);
        }

        String s = JSON.toJSONString(messages);
        System.out.println(s);
    }

}
