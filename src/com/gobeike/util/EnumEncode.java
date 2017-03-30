package com.gobeike.util;

/**
 * Created by xuff on 17-3-29.
 * 设置网络访问的编码格式
 */
public enum EnumEncode {

    gb2312("gb2312"),utf_8("utf-8");

    private String encoding;
    EnumEncode(String s) {
        this.encoding=s;
    }

    public String getEncoding() {
        return encoding;
    }
}
