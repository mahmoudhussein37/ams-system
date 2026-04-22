package koreatech.cse.domain.dto;

public class GraduationResearchPlanRequest {
    private String title;
    private String type;
    private String startDate;
    private String endDate;
    private String purpose;
    private String scope;
    private String method;
    private String impl;
    private String schedule;
    private String ref;
    private String etc;
    private String submitDate;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getImpl() { return impl; }
    public void setImpl(String impl) { this.impl = impl; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }

    public String getEtc() { return etc; }
    public void setEtc(String etc) { this.etc = etc; }

    public String getSubmitDate() { return submitDate; }
    public void setSubmitDate(String submitDate) { this.submitDate = submitDate; }
}
