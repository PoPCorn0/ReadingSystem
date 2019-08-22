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

package com.snsoft.teamreading.controller.commonController;

import com.snsoft.teamreading.dao.*;
import com.snsoft.teamreading.enums.Code;
import com.snsoft.teamreading.pojo.Attachment;
import com.snsoft.teamreading.pojo.User;
import com.snsoft.teamreading.utils.ModelAndViewUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.UUID;

@Controller("CommonFileController")
@RequestMapping("/common")
public class FileController {
    @Resource
    AttachmentDao attachmentDao;
    @Resource
    TaskDao taskDao;
    @Resource
    PendingTaskDao pendingTaskDao;
    @Resource
    AnswerDao answerDao;
    @Resource
    PendingAnswerDao pendingAnswerDao;
    @Resource
    HashSet<String> availableFileSuffix;
    @Resource
    ModelAndView mv;

    /**
     * 文件上传
     * <p>
     * //     * @param user       session中用户信息
     *
     * @param id         任务或解读id
     * @param mark       附件标记，属于任务则为'1'，属于待审核任务则为'2'，属于解读&追加解读则为'3'，属于待审核解读&追加解读则为'4'
     * @param uploadFile 上传的文件
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView Upload(
            @SessionAttribute(value = "user") User user,
            @RequestParam("id") String id,
            @RequestParam("mark") char mark,
            @RequestParam("file") MultipartFile uploadFile) {

        Attachment attachment = attachmentDao.getAttachmentByRelyOnId(id);
        if (attachment != null) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "重复上传");
        }

        // 根据传入的id和mark查询相应的任务或待审核任务或解读或待审核解读是否存在
        switch (mark) {
            case '1':
                if (taskDao.getTaskById(id) == null) {
                    return ModelAndViewUtil.addObject(mv, Code.FAIL);
                }
                break;
            case '2':
                if (pendingTaskDao.getPendingTaskById(id) == null) {
                    return ModelAndViewUtil.addObject(mv, Code.FAIL);
                }
                break;
            case '3':
                if (answerDao.getAnswerById(id) == null) {
                    return ModelAndViewUtil.addObject(mv, Code.FAIL);
                }
                break;
            case '4':
                if (pendingAnswerDao.getPendingAnswerById(id) == null) {
                    return ModelAndViewUtil.addObject(mv, Code.FAIL);
                }
        }

        //检测是否成功获取ApplicationContext
        WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        if (currentWebApplicationContext == null) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }

        // 检测是否成功获取ServletContext
        ServletContext servletContext = currentWebApplicationContext.getServletContext();
        if (servletContext == null) {
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }

        String originalFilename = uploadFile.getOriginalFilename();
        //判断上传文件是否为空或文件名不符
        if (originalFilename == null || originalFilename.equals("")) {
            return ModelAndViewUtil.addObject(mv, Code.SUCCESS, "未选择文件");
        }

        //获取文件后缀名
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

        // 判断文件后缀名是否符合要求
        if (!availableFileSuffix.contains(fileSuffix)) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL, "不支持的文件类型");
        }

        UUID uuid = UUID.randomUUID();
        //为上传的文件增加一个父目录，文件夹名为uuid第一位
        char parentDir = uuid.toString().charAt(0);
        String realPath = servletContext.getRealPath("/WEB-INF/attachment");
        //拼接文件存放路径
        String filePath = realPath + File.separator + parentDir + File.separator + uuid.toString() + fileSuffix;
        File file = new File(filePath);

        //如果文件父目录不存在且创建失败
        if (!file.getParentFile().exists() && !file.mkdirs()) {
            return ModelAndViewUtil.addObject(mv, Code.FAIL);
        }

        try {
            //上传文件
            uploadFile.transferTo(file);

            // 向attachment表添加一条记录
            attachment = new Attachment();
            attachment.setId(uuid.toString());
            attachment.setRelyOnId(id);
            attachment.setAuthorId(user.getId());
            attachment.setSavePath(filePath);
            attachment.setSize((int) uploadFile.getSize());
            attachment.setFileName(originalFilename);
            attachmentDao.addAttachment(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return ModelAndViewUtil.addObject(mv, Code.ERROR);
        }
        return ModelAndViewUtil.addObject(mv, Code.SUCCESS);
    }

    /**
     * 文件下载
     *
     * @param id 附件id
     * @return ModelAndView视图
     */
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> Download(@RequestParam("id") String id) {
        Attachment attachment = attachmentDao.getAttachmentById(id);
        String filePath = attachment.getSavePath();
        File file = new File(filePath);

        //设置http头信息
        HttpHeaders headers = new HttpHeaders();
        //文件名编码
        headers.setContentDispositionFormData("attachment",
                new String(attachment.getFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            return null;
        }
    }
}
