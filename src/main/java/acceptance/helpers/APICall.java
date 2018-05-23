package acceptance.helpers;

import com.jayway.restassured.response.Response;

public class APICall {
    private String requestParams;
    private Response response;
    private String requestStartDate;
    private String requestEndDate;
    private String requestForename;
    private String requestSecondForename;
    private String requestSurname;
    private String requestInitials;
    private String requestDob;
    private String requestNino;
    private String requestSex;
    private String requestHeaderAccept;
    private String requestHeaderAcceptEncoding;
    private String requestMethod;

    public String getRequestParams() {
        return requestParams;
    }

    public String getRequestHeaderAccept() {
        return requestHeaderAccept;
    }

    public String getRequestHeaderAcceptEncoding() {
        return requestHeaderAcceptEncoding;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestStartDate() {
        return requestStartDate;
    }

    public String getRequestEndDate() {
        return requestEndDate;
    }

    public String getRequestNino() {
        return requestNino;
    }

    public String getRequestForename() {
        return requestForename;
    }

    public String getRequestSecondForename() {
        return requestSecondForename;
    }

    public String getRequestSurname() {
        return requestSurname;
    }

    public String getRequestInitials() {
        return requestInitials;
    }

    public String getRequestDob() {
        return requestDob;
    }

    public String getRequestSex() {
        return requestSex;
    }

    public Response getResponse() {
        return response;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setRequestStartDate(String requestParams) {
        this.requestStartDate = requestParams;
    }

    public void setRequestEndDate(String requestParams) {
        this.requestEndDate = requestParams;
    }

    public void setRequestNino(String requestParams) {
        this.requestNino = requestParams;
    }

    public void setRequestForename(String requestParams) {
        this.requestForename = requestParams;
    }

    public void setRequestSecondForename(String requestParams) {
        this.requestSecondForename = requestParams;
    }

    public void setRequestSurname(String requestParams) {
        this.requestSurname = requestParams;
    }

    public void setRequestInitials(String requestParams) {
        this.requestInitials = requestParams;
    }

    public void setRequestSex(String requestParams) {
        this.requestSex = requestParams;
    }

    public void setRequestDob(String requestParams) {
        this.requestDob = requestParams;
    }

    public void setRequestHeaderAccept(String requestHeaderParams) {
        this.requestHeaderAccept = requestHeaderParams;
    }

    public void setRequestHeaderAcceptEncoding(String requestHeaderParams) {
        this.requestHeaderAcceptEncoding = requestHeaderParams;
    }

    public void setRequestMethod(String requestMethodParams) {
        this.requestMethod = requestMethodParams;
    }

}
