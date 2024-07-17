package TTCS.Security.mapper;


import TTCS.Security.dto.request.PermissionRequest;
import TTCS.Security.dto.response.PermissionResponse;
import TTCS.Security.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
