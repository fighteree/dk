package com.yzdbx.dkpan.entity.dto;

import lombok.Data;

/**
 * @Author: 一只大笨象
 * @Date: 2023/06/05
 * @description:
 */
@Data
public class FileDto {
    private Long fileId;
    private String fileName;
    private String fileSize;
    private String fileCover;

}
