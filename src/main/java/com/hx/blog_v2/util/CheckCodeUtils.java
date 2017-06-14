package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.dto.CheckCode;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;

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
@org.springframework.stereotype.Component
public final class CheckCodeUtils {

    @Autowired
    private ConstantsContext constantsContext;

    /**
     * Ĭ�ϵĿ��
     */
    public static int DEFAULT_WIDTH = 80;
    /**
     * Ĭ�ϵĸ߶�
     */
    public static int DEFAULT_HEIGHT = 30;
    /**
     * Ĭ�ϵ���ɫ
     */
    public static Color DEFAULT_COLOR = Color.BLACK;
    /**
     * Ĭ�ϵ�����
     */
    public static Font DEFAULT_FONT = new Font(null, Font.BOLD, 20);
    /**
     * Ĭ�ϵ���֤����ַ�����
     */
    public static int DEFAULT_CHECK_CODE_LENGTH = 4;

    /**
     * ��ȡ�򵥵���֤��
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public CheckCode getCheckCode() {
        BufferedImage checkCodeImg = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = checkCodeImg.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        g.setColor(DEFAULT_COLOR);
        g.setFont(DEFAULT_FONT);

        String checkCodeStr = makeCheckCode(DEFAULT_CHECK_CODE_LENGTH);
        g.drawString(checkCodeStr, 0, 20);

        CheckCode checkCode = new CheckCode(checkCodeStr, checkCodeImg);
        return checkCode;
    }


    /**
     * ������֤��
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public String makeCheckCode(int length) {
        StringBuffer sb = new StringBuffer();
        int candidateNum = constantsContext.checkCodeCandidatesStr.length();
        for (int i = 0; i < length; i++) {
            sb.append(constantsContext.checkCodeCandidates.get(Tools.ran.nextInt(candidateNum)));
        }

        return sb.toString();
    }


    /**
     * ������֤��ͼƬ
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public CheckCode getCheckCode(int width, int height, Color bgColor, Font font, int validateCodeLength,
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
     * ��ȡ������Χ�ڵ������
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public int random(int range) {
        return Tools.ran.nextInt(range);
    }

    /**
     * ��ȡһ���������ɫ
     *
     * @return com.hx.blog.bean.CheckCode
     * @author Jerry.X.He
     * @date 5/7/2017 2:26 PM
     * @since 1.0
     */
    public Color randomColor() {
        return new Color(random(255) + 1, random(255) + 1, random(255) + 1);
    }

}
