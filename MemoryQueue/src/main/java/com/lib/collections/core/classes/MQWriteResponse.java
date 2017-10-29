package com.lib.collections.core.classes;

import com.lib.collections.core.enums.MqReturnCode;

/**
 * Created by nikzz on 27/10/17.
 */
public class MQWriteResponse {

    private boolean isSuccessful;
    private String errorMessage;

    private MqReturnCode returnCode;

    /**
     *
     */
    public MQWriteResponse() {
        isSuccessful = false;
        errorMessage = null;
        returnCode = MqReturnCode.FAILURE;
    }

    /**
     * @param isSuccessful
     * @param errorMessage
     * @param code
     */
    public MQWriteResponse(boolean isSuccessful, String errorMessage, MqReturnCode code) {
        this.isSuccessful = isSuccessful;
        this.errorMessage = errorMessage;
        this.returnCode = code;
    }

    /**
     * @return
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * @param successful
     */
    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    /**
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return
     */
    public MqReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * @param returnCode
     */
    public void setReturnCode(MqReturnCode returnCode) {
        this.returnCode = returnCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MQWriteResponse response = (MQWriteResponse) o;

        if (isSuccessful() != response.isSuccessful()) return false;
        if (getErrorMessage() != null ? !getErrorMessage().equals(response.getErrorMessage()) : response.getErrorMessage() != null)
            return false;
        return getReturnCode() == response.getReturnCode();
    }

    @Override
    public int hashCode() {
        int result = (isSuccessful() ? 1 : 0);
        result = 31 * result + (getErrorMessage() != null ? getErrorMessage().hashCode() : 0);
        result = 31 * result + getReturnCode().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MQWriteResponse{" +
                "isSuccessful=" + isSuccessful +
                ", errorMessage='" + errorMessage + '\'' +
                ", returnCode=" + returnCode +
                '}';
    }
}
