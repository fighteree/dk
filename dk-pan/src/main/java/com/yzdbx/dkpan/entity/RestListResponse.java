package com.yzdbx.dkpan.entity;

import com.yzdbx.dkpan.entity.data.ListData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestListResponse<T> {

    private static final long success_code = 10000L;
    private static final String success_message = "success";

    private long code;
    private ListData<T> data;

    public RestListResponse(long code, String message, List<T> list) {
        this.code = code;
        this.data = new ListData<>(message, list);
    }

    public static <T> RestListResponse<T> success(List<T> data) {
        return new RestListResponse<>(success_code, success_message, data);
    }

    public static <T> RestListResponse<T> success(String message, List<T> data) {
        return new RestListResponse<>(success_code, message, data);
    }

    public static <T> RestListResponse<T> failure(long code, String message) {
        return new RestListResponse<>(code, message, null);
    }
}
