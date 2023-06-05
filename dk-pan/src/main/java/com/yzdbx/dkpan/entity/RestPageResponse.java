package com.yzdbx.dkpan.entity;

import com.yzdbx.dkpan.entity.data.PageData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RestPageResponse<T> {
    private long code;
    private PageData<T> data;

    public RestPageResponse(long code, String message, int recordCount, int totalCount, int pageNo, int pageSize, int totalPage, List<T> list) {
        this.code = code;
        this.data = new PageData<T>(message, recordCount, totalCount, pageNo, pageSize, totalPage, list);
    }


}
