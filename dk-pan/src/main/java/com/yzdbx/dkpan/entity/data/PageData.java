package com.yzdbx.dkpan.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> {
    private String message;
    private int recordCount;
    private int totalCount;
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private List<T> list;
}
