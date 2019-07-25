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

package com.snsoft.readingsystem.service;

import com.snsoft.readingsystem.dao.AnswerDao;
import com.snsoft.readingsystem.dao.RecordDao;
import com.snsoft.readingsystem.dao.TaskDao;
import com.snsoft.readingsystem.dao.UserDao;
import com.snsoft.readingsystem.enums.Code;
import com.snsoft.readingsystem.pojo.Answer;
import com.snsoft.readingsystem.pojo.PraiseRecord;
import com.snsoft.readingsystem.pojo.Task;
import com.snsoft.readingsystem.utils.AllConstant;
import com.snsoft.readingsystem.utils.ModelAndViewUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class PraiseService {
    @Resource
    AnswerDao answerDao;
    @Resource
    RecordDao recordDao;
    @Resource
    UserDao userDao;
    @Resource
    TaskDao taskDao;
    @Resource
    ModelAndView mv;

    /**
     * 点赞
     *
     * @param studentId 学生id
     * @param answerId  被点赞解读id
     * @return ModelAndView视图
     */
    @Transactional
    public ModelAndView praise(String studentId, String answerId) {
        Answer answer = answerDao.getAnswerById(answerId);
        // 判断解读是否存在
        if (answer == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "解读不存在");
        }

        // 判断解读是否属于点赞者
        if (answer.getAuthorId().equals(studentId)) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "无法给自己点赞");
        }

        // 查询当天的点赞记录
        List<PraiseRecord> praiseRecords = recordDao.getPraiseRecordsByStudentIdInADay(studentId);
        if (praiseRecords == null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL);
        }

        // 判断是否重复点赞
        if (recordDao.getPraiseRecordByStudentIdAndAnswerId(studentId, answerId) != null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "无法重复点赞");
        }

        // 向praise_record插入一条记录
        PraiseRecord praiseRecord = new PraiseRecord();
        praiseRecord.setAnswerId(answerId);
        praiseRecord.setId(UUID.randomUUID().toString());
        praiseRecord.setPraiseId(studentId);
        if (recordDao.addPraiseRecord(praiseRecord) != 1) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL);
        }

        Task task = taskDao.getTaskById(answer.getTaskId());
        if (praiseRecords.size() < 3) {
            //添加积分
            if (userDao.updateScore(studentId, task.getTeamId(), AllConstant.PRAISE) != 1 ||
                    userDao.updateScore(answer.getAuthorId(), task.getTeamId(), AllConstant.PRAISE) != 1)
                return ModelAndViewUtil.addObject(mv, Code.FAIL);
        }

        return ModelAndViewUtil.addObject(mv, Code.SUCCESS);
    }
}
