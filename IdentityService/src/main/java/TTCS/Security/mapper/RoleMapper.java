package TTCS.Security.mapper;


import TTCS.Security.dto.request.RoleRequest;
import TTCS.Security.dto.response.RoleResponse;
import TTCS.Security.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
