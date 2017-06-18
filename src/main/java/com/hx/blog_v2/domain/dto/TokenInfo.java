package com.hx.blog_v2.domain.dto;

/**
 * TokenInfo
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 12:05 PM
 */
public class TokenInfo {

    /**
     * 生成的token
     */
    private String originalToken;
    /**
     * 当前token[originalToken 处理之后的结果]
     */
    private String tokenNow;
    /**
     * 当前token之前的token [可能需要在 token切换的时候做容错]
     */
    private String tokenLast;
    /**
     * token 上一次更新的时间
     */
    private long lastUpdated;

    public TokenInfo(String originalToken, String tokenNow) {
        this.originalToken = originalToken;
        this.tokenNow = tokenNow;
        lastUpdated = System.currentTimeMillis();
    }

    public String getOriginalToken() {
        return originalToken;
    }

    public void setOriginalToken(String originalToken) {
        this.originalToken = originalToken;
    }

    public String getTokenNow() {
        return tokenNow;
    }

    public void setTokenNow(String tokenNow) {
        this.tokenNow = tokenNow;
    }

    public String getTokenLast() {
        return tokenLast;
    }

    public void setTokenLast(String tokenLast) {
        this.tokenLast = tokenLast;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
