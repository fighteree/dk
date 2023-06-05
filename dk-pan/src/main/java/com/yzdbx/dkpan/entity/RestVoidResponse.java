package com.yzdbx.dkpan.entity;

import com.yzdbx.dkpan.entity.data.VoidData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestVoidResponse {

    private static final long success_code = 10000L;
    private static final String success_message = "success";

    private long code;
    private VoidData data;

    public RestVoidResponse(Long code, String message) {
        this.code = code;
        this.data = new VoidData(message);
    }

    public static RestVoidResponse success() {
        return new RestVoidResponse(success_code, success_message);
    }

    public static RestVoidResponse success(String message) {
        return new RestVoidResponse(success_code, message);
    }

    public static RestVoidResponse failure(long code, String message) {
        return new RestVoidResponse(code, message);
    }

}
