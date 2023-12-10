package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ParametersForEntityTest.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    public void toRegistrationResponseTest() {
        User user = new User();
        user.setId(ID_USER);
        user.setFirstName(FIRST_NAME_USER);
        user.setLastName(LAST_NAME_USER);
        user.setEmail(EMAIL_USER);
        user.setPassword(PASSWORD_USER);
        user.setRole(Role.USER);

        RegistrationResponseDto response = userMapper.toRegistrationResponseDto(user);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(ID_USER);
        Assertions.assertThat(response.getFirstName()).isEqualTo(FIRST_NAME_USER);
        Assertions.assertThat(response.getLastName()).isEqualTo(LAST_NAME_USER);
        Assertions.assertThat(response.getEmail()).isEqualTo(EMAIL_USER);
        Assertions.assertThat(response.getPassword()).isEqualTo(PASSWORD_USER);
        Assertions.assertThat(response.getRole()).isEqualTo(Role.USER.name());
    }

    @Test
    public void toEntityUserTest() {
        RegistrationRequestDto request = new RegistrationRequestDto();
        request.setFirstName(FIRST_NAME_USER);
        request.setLastName(LAST_NAME_USER);
        request.setEmail(EMAIL_USER);

        User user = userMapper.toEntityUser(request);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isEqualTo(FIRST_NAME_USER);
        Assertions.assertThat(user.getLastName()).isEqualTo(LAST_NAME_USER);
        Assertions.assertThat(user.getEmail()).isEqualTo(EMAIL_USER);
    }
}
