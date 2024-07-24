package com.nosto.nosto_currency_converter.Common;

public class ServiceResultBuilder<T> {
    private String id;
    private T resultObject;
    private ResultKey key = ResultKey.UNKNOWN_STATE;
    private String message = "";

    public ServiceResultBuilder() {
    }

    public ServiceResultBuilder(String id) {
        this.id = id;
    }

    public ServiceResultBuilder<T> withId(String id) {
        this.id = id;
        return this;
    }

    public ServiceResultBuilder<T> withKey(ResultKey key) {
        this.key = key;
        return this;
    }

    public ServiceResultBuilder<T> ok(T result) {
        this.key = ResultKey.OK;
        this.resultObject = result;
        return this;
    }

    public ServiceResultBuilder<T> unchanged(String error) {
        this.key = ResultKey.UNCHANGED;
        this.message = error;
        return this;
    }

    public ServiceResultBuilder<T> notFound(String error) {
        this.key = ResultKey.NOT_FOUND;
        this.message = error;
        return this;
    }

    public ServiceResultBuilder<T> repoError(String error) {
        this.key = ResultKey.REPOSITORY_ERROR;
        this.message = error;
        return this;
    }

    public ServiceResultBuilder<T> failed(String error) {
        this.key = ResultKey.FAILED;
        this.message = error;
        return this;
    }

    public ServiceResultBuilder<T> forbidden(String error) {
        this.key = ResultKey.FORBIDDEN;
        this.message = error;
        return this;
    }

    public ServiceResultBuilder<T> conflict(String error) {
        this.key = ResultKey.CONFLICT;
        this.message = error;
        return this;
    }

    public ServiceResultBuilder<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceResult<T> build() {
        return new ServiceResult<>(this);
    }

    public String getId() {
        return id;
    }

    public T getResultObject() {
        return resultObject;
    }

    public ResultKey getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
