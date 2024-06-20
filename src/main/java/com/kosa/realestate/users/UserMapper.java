package com.kosa.realestate.users;

public class UserMapper {
  public static Users toEntity(UserDTO userDTO) {
    if(userDTO == null) {
      return null;
    }
    
    return Users.builder()
        .userId(userDTO.getUserId())
        .email(userDTO.getEmail())
        .password(userDTO.getPassword())
        .nickname(userDTO.getNickname())
        .isDeleted(userDTO.getIsDeleted())
        .createdAt(userDTO.getCreatedAt())
        .updatedAt(userDTO.getUpdatedAt())
        .build();
  }
  
  public static UserDTO toDTO(Users user) {
    if (user == null) {
      return null;
    }
    
    return UserDTO.builder()
        .userId(user.getUserId())
        .email(user.getEmail())
        .password(user.getPassword())
        .nickname(user.getNickname())
        .isDeleted(user.getIsDeleted())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
