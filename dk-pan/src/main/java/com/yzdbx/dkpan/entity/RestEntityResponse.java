package com.yzdbx.dkpan.entity;

import com.yzdbx.dkpan.entity.data.EntityData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEntityResponse<T> {

    private static final long success_code = 10000L;
    private static final String success_message = "success";

    private long code;
    private EntityData<T> data;

    public RestEntityResponse(Long code, String message, T data) {
        this.code = code;
        this.data = new EntityData<>(message, data);
    }

    public static <T> RestEntityResponse<T> success(T data) {
        return new RestEntityResponse<>(success_code, success_message, data);
    }

    public static <T> RestEntityResponse<T> success(String message, T data) {
        return new RestEntityResponse<>(success_code, message, data);
    }

    public static <T> RestEntityResponse<T> failure(long code, String message) {
        return new RestEntityResponse<>(code, message, null);
    }

}
