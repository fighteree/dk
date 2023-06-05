package com.yzdbx.dkpan.controller.api;

import com.yzdbx.dkpan.entity.RestPageResponse;
import com.yzdbx.dkpan.entity.dto.FileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * @Author: 一只大笨象
 * @Date: 2023/06/05
 * @description:
 */
@Tag(name = "文件管理", description = "用于处理文件的上传下载和删除")
public interface FileControllerApi {

    /**
     * 查询用户文件连表文件
     *
     * @param fileType     文件类型
     * @param fileParentId 文件父目录
     * @return
     */
    @Operation(
            method = "GET",
            summary = "用户文件列表",
            parameters = {
                    @Parameter(name = "fileType", description = "文件分类id"),
                    @Parameter(name = "file_parent_id", description = "文件父目录")
            }
    )
    RestPageResponse<FileDto> fileList(Integer fileType, Integer fileParentId);


}
