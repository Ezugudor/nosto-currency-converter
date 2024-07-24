package com.nosto.nosto_currency_converter.Common;

public class ServiceResult<T> {
    private String id;
    private ResultKey key;
    private String message;
    private T resultObject;

    public ServiceResult() {
    }

    public ServiceResult(ServiceResultBuilder<T> builder) {
        this.id = builder.getId();
        this.resultObject = builder.getResultObject();
        this.key = builder.getKey();
        this.message = builder.getMessage();
    }

    public boolean isOk() {
        return this.key == ResultKey.OK;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultKey getKey() {
        return key;
    }

    public void setKey(ResultKey key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResultObject() {
        return resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }
}
