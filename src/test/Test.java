/*
 * @copyright ：神农大学生软件创新中心 版权所有 © 2019
 *
 * @author 16级信息与计算科学潘鹏程
 *
 * @version
 *
 * @date 2019.05.21
 *
 * @Description
 */

import java.io.File;

public class Test {

    public static void main(String[] args) {
        new Test().test();
    }

    private void test() {
        String s = "9b64675a-e406-4eb5-a277-72981acb5054";
        File file = new File("target/readingsystem/WEB-INF/attachment/" + s.charAt(0) + File.separator + s);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
    }
}
