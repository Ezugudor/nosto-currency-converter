
package com.nosto.nosto_currency_converter.Common;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ControllerHelpers {

    public <T> BaseResponse<T> getResourceByIdResponseHandler(ServiceResult<T> serviceResult) {
        if (serviceResult.isOk() && serviceResult.getResultObject() != null) {
            return new BaseResponse<T>(HttpStatus.OK.value(), serviceResult.getResultObject());
        } else if (serviceResult.getKey() == ResultKey.NOT_FOUND) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, serviceResult.getMessage());
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, serviceResult.getMessage());
        }
    }

    public <T> BaseResponse<T> getResourcesResponseHandler(ServiceResult<T> serviceResult) {
        if (serviceResult.isOk() && serviceResult.getResultObject() != null) {
            return new BaseResponse<T>(HttpStatus.OK.value(), serviceResult.getResultObject());
        } else if (serviceResult.getKey() == ResultKey.NOT_FOUND) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, serviceResult.getMessage());
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, serviceResult.getMessage());
        }
    }

    public <T> BaseResponse<T> putResourceByIdResponseHandler(ServiceResult<T> serviceResult) {
        switch (serviceResult.getKey()) {
            case OK:
                return new BaseResponse<T>(HttpStatus.OK.value(), serviceResult.getResultObject());
            case UNCHANGED:
                throw new ResponseStatusException(HttpStatus.GONE, serviceResult.getMessage());
            case NOT_FOUND:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, serviceResult.getMessage());
            default:
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, serviceResult.getMessage());
        }
    }

    public <T> BaseResponse<T> postNewResourceResponseHandler(ServiceResult<T> serviceResult) {
        if (serviceResult.isOk()) {
            return new BaseResponse<T>(HttpStatus.OK.value(), serviceResult.getResultObject());
        } else if (serviceResult.getKey() == ResultKey.NOT_FOUND) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, serviceResult.getMessage());
        } else if (serviceResult.getKey() == ResultKey.CONFLICT) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, serviceResult.getMessage());
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, serviceResult.getMessage());
        }
    }

    public <T> BaseResponse<T> deleteResourceResponseHandler(ServiceResult<T> serviceResult) {
        if (serviceResult.isOk()) {
            return new BaseResponse<T>(HttpStatus.NO_CONTENT.value(), serviceResult.getResultObject());
        } else if (serviceResult.getKey() == ResultKey.NOT_FOUND) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, serviceResult.getMessage());
        } else if (serviceResult.getKey() == ResultKey.FORBIDDEN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, serviceResult.getMessage());
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, serviceResult.getMessage());
        }
    }
}