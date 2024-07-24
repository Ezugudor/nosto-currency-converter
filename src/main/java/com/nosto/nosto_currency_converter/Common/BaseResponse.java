package com.nosto.nosto_currency_converter.Common;

import java.time.LocalDateTime;

// import org.springframework.http.HttpStatus;

public class BaseResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private T data;

    public BaseResponse(int status, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.data = data;

    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}