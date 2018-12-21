package com.qpp.admin.entity.system;


public class SchdulerJob {
    private String id;

    private String fJobname;

    private String fJobgroup;

    private String fJobdes;

    private String fRunparam;

    private String fCronexpression;

    private String fBeanname;

    private String fMethodname;

    private Long fRuntime;

    private Integer fStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getfJobname() {
        return fJobname;
    }

    public void setfJobname(String fJobname) {
        this.fJobname = fJobname == null ? null : fJobname.trim();
    }

    public String getfJobgroup() {
        return fJobgroup;
    }

    public void setfJobgroup(String fJobgroup) {
        this.fJobgroup = fJobgroup == null ? null : fJobgroup.trim();
    }

    public String getfJobdes() {
        return fJobdes;
    }

    public void setfJobdes(String fJobdes) {
        this.fJobdes = fJobdes == null ? null : fJobdes.trim();
    }

    public String getfRunparam() {
        return fRunparam;
    }

    public void setfRunparam(String fRunparam) {
        this.fRunparam = fRunparam == null ? null : fRunparam.trim();
    }

    public String getfCronexpression() {
        return fCronexpression;
    }

    public void setfCronexpression(String fCronexpression) {
        this.fCronexpression = fCronexpression == null ? null : fCronexpression.trim();
    }

    public String getfBeanname() {
        return fBeanname;
    }

    public void setfBeanname(String fBeanname) {
        this.fBeanname = fBeanname == null ? null : fBeanname.trim();
    }

    public String getfMethodname() {
        return fMethodname;
    }

    public void setfMethodname(String fMethodname) {
        this.fMethodname = fMethodname == null ? null : fMethodname.trim();
    }

    public Long getfRuntime() {
        return fRuntime;
    }

    public void setfRuntime(Long fRuntime) {
        this.fRuntime = fRuntime;
    }

    public Integer getfStatus() {
        return fStatus;
    }

    public void setfStatus(Integer fStatus) {
        this.fStatus = fStatus;
    }

}