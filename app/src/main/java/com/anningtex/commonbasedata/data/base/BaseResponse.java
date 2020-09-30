package com.anningtex.commonbasedata.data.base;

import lombok.Data;

/**
 * @author Song
 */
@Data
public class BaseResponse<T> {
    private String code, msg;
    private T data;
}
