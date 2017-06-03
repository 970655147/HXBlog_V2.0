package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.dto.CheckCode;
import com.hx.log.util.Tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * CheckCodeUtils
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/6/2017 4:07 PM
 */
public final class CheckCodeUtils {

    /**
     * 常量配置
     */
    private static BlogConstants constants = BlogConstants.getInstance();

    // disable constructor
    private CheckCodeUtils() {
        Tools.assert0("can't instantiate !");
    }

    /**
     * 获取简单的验证码
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public static CheckCode getCheckCode() {
        BufferedImage checkCodeImg = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
        Graphics g = checkCodeImg.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 80, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, Font.BOLD, 20));

//        String checkCodeStr = makeCheckCode(BlogConstants.checkCodeLength);
        String checkCodeStr = makeCheckCode(4);
        g.drawString(checkCodeStr, 0, 20);

        CheckCode checkCode = new CheckCode(checkCodeStr, checkCodeImg);
        return checkCode;
    }


    /**
     * 生成验证码
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public static String makeCheckCode(int length) {
        StringBuffer sb = new StringBuffer();
        int candidateNum = constants.checkCodeCandidatesStr.length();
        for (int i = 0; i < length; i++) {
            sb.append(constants.checkCodeCandidates.get(Tools.ran.nextInt(candidateNum)));
        }

        return sb.toString();
    }


    /**
     * 生成验证码图片
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public static CheckCode getCheckCode(int width, int height, Color bgColor, Font font, int validateCodeLength,
                                         List<Character> checkCodes, int minInterference, int interferenceOff) {
        int checkCodeY = height - (height >> 2);
        int checkCodeXOff = width / (validateCodeLength + 2);

        BufferedImage checkCodeImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = checkCodeImg.getGraphics();
        g.setColor(bgColor);
        g.fillRect(0, 0, width, height);
        g.setFont(font);
        StringBuilder checkCodeStr = new StringBuilder();
        while (checkCodeStr.length() < validateCodeLength) {
            g.setColor(randomColor());
            Character curChar = checkCodes.get(random(checkCodes.size()));
            g.drawString(curChar.toString(), (checkCodeStr.length() + 1) * checkCodeXOff, checkCodeY);
            checkCodeStr.append(curChar);
        }

        //draw the interference line
        int interferenceSize = (Tools.ran.nextInt(interferenceOff) + minInterference);
        for (int i = 0; i < interferenceSize; i++) {
            g.setColor(randomColor());
            g.drawLine(random(width), random(height), random(width), random(height));
        }

        CheckCode checkCode = new CheckCode(checkCodeStr.toString(), checkCodeImg);
        return checkCode;
    }

    /**
     * 获取给定范围内的随机数
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public static int random(int range) {
        return Tools.ran.nextInt(range);
    }

    /**
     * 获取一个随机的颜色
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public static Color randomColor() {
        return new Color(random(255) + 1, random(255) + 1, random(255) + 1);
    }

}
