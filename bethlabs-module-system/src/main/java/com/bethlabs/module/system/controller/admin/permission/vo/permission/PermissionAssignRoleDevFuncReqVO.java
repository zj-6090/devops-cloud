package com.bethlabs.module.system.controller.admin.permission.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Schema(description = "管理后台 - 赋予角色设备功能权限 Request VO")
@Data
public class PermissionAssignRoleDevFuncReqVO {

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    @Schema(description = "设备功能编号列表", example = "1,3,5")
    private Set<Long> devFuncIds = Collections.emptySet(); // 兜底

}
