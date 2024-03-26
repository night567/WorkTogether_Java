package user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.junit.jupiter.api.Test;

class CaptchaTest {
    @Test
    void testCreateCaptcha() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //输出图片
        lineCaptcha.write("d:/line.png");
        //输出code
        String code = lineCaptcha.getCode();
        System.out.println(code);
        //验证code(无视大小写)
        System.out.println(lineCaptcha.verify(code.toLowerCase()));
    }
}
