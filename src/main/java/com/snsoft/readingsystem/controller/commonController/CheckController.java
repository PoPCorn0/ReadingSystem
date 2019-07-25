/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.07.25
 *
 * @Description
 */

package com.snsoft.readingsystem.controller.commonController;

import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.User;
import com.snsoft.readingsystem.service.CheckService;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller("CommonCheckController")
@RequestMapping("/common")
public class CheckController {
    @Resource
    CheckService checkService;
    @Resource
    ModelAndView mv;

    /**
     * 审核学生提交任务
     *
     * @param user      session中用户信息
     * @param id        待审核任务id
     * @param checkMark 审核标记
     * @param endTime   任务结束时间
     * @param reward    任务悬赏分
     * @param receiver  可接受者
     * @param reason    不通过理由
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/checkTask", method = RequestMethod.POST)
    public ModelAndView checkTask(@SessionAttribute("user") User user,
                                  @RequestParam("id") String id,
                                  @RequestParam("checkMark") Character checkMark,
                                  @RequestParam(value = "endTime", required = false) String endTime,
                                  @RequestParam(value = "reward", required = false) Integer reward,
                                  @RequestParam(value = "receiver", required = false) List<String> receiver,
                                  @RequestParam(value = "reason", required = false) String reason) {
        // 当审核通过时endTime, reward, receiver参数必需，不通过时reason参数必需
        if ((checkMark == '1' && (endTime == null || reward == null || receiver == null))
                || (checkMark == '2' && reason == null)) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "缺少参数");
        } else if (checkMark != '1' && checkMark != '2') {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "审核标记异常");
        }

        try {
            if (checkMark == '1')
                return checkService.checkTask(user, id, endTime, reward, receiver);
            else
                return checkService.checkTask(user, id, reason);
        } catch (RuntimeException e) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }

    /**
     * 审核学生提交的待审核解读&追加解读
     *
     * @param user      session中用户信息
     * @param id        待审核解读&追加解读id
     * @param checkMark 审核标记
     * @param reason    不通过理由
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/checkAnswer", method = RequestMethod.POST)
    public ModelAndView checkAnswer(@SessionAttribute("user") User user,
                                    @RequestParam("id") String id,
                                    @RequestParam("checkMark") Character checkMark,
                                    @RequestParam(value = "reason", required = false) String reason) {
        if (checkMark == '2' && reason == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "参数异常");
        } else if (checkMark != '1' && checkMark != '2') {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "审核标记异常");
        }

        try {
            if (checkMark == '1')
                return checkService.checkAnswer(user, id);
            else
                return checkService.checkAnswer(user, id, reason);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
    }
}
