package com.philimonov.converter;

import com.philimonov.dao.UserModel;
import com.philimonov.service.UserDTO;

public class UserModelToUserDtoConverter implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setEmail(source.getEmail());

        return userDTO;
    }
}
