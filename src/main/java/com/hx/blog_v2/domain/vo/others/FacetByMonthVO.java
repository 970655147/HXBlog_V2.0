package com.hx.blog_v2.domain.vo.others;

/**
 * FacetByMonthVO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/27/2017 9:55 PM
 */
public class FacetByMonthVO {

    private String month;
    private Integer cnt;

    public FacetByMonthVO() {
    }

    public FacetByMonthVO(String month, Integer cnt) {
        this.month = month;
        this.cnt = cnt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
