package com.springboot.study.netty;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class DeviceInfo {

    private String unifiedId;//统一ID
    private String csid;//厂商ID
    private String type;
    private String name;
    private String status;
    private String ip;
    private Date addTime;
    private Date activeTime;
    private Date lastOnlineTime;
    private JSONObject values;

    public DeviceInfo(String csid, JSONObject values) {
        this.csid = csid;
        this.values = values;
    }

    public String getCsid() {
        return csid;
    }

    public void setCsid(String csid) {
        this.csid = csid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getUnifiedId() {
        return unifiedId;
    }

    public void setUnifiedId(String unifiedId) {
        this.unifiedId = unifiedId;
    }

    public JSONObject getValues() {
        return values;
    }

    public void setValues(JSONObject values) {
        this.values = values;
    }
}
