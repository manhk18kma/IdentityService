package TTCS.Security.mapper;


import TTCS.Security.dto.response.UserResponse;
import TTCS.Security.entity.User;
import TTCS.Security.dto.request.UserCreationRequest;
import TTCS.Security.dto.request.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
    @Mapping(target = "roles", ignore = true)

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
