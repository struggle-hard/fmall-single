package com.marc.fmall.common;

public enum AppletTypeEnum {

    WECHAT_APPLET(100,"微信"),
    ALIPAY_APPLET(200,"支付宝"),
    BAIDU_APPLET(300,"百度"),
    BYTE_APPLET(400,"字节跳动");

    private int type;
    private String name;

    private AppletTypeEnum(int type, String name){
        this.type=type;
        this.name=name;
    }

    public int getType(){
        return type;
    }
    public void setType(){
        this.type=type;
    }
    public String getName(){
        return name;
    }
    public void setName(){
        this.name=name;
    }
}
