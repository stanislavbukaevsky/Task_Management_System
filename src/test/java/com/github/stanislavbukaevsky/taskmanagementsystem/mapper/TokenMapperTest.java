package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ParametersForEntityTest.*;

@ExtendWith(MockitoExtension.class)
public class TokenMapperTest {
    @InjectMocks
    private TokenMapperImpl tokenMapper;

    @Test
    public void toAuthenticationResponseTest() {
        AuthenticationResponseDto response = tokenMapper.toAuthenticationResponse(
                ID_USER,
                FIRST_NAME_USER,
                LAST_NAME_USER,
                EMAIL_USER,
                PASSWORD_USER,
                Role.USER.name(),
                ACCESS_TOKEN,
                REFRESH_TOKEN,
                EXPIRES_ACCESS,
                EXPIRES_REFRESH);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(ID_USER);
        Assertions.assertThat(response.getFirstName()).isEqualTo(FIRST_NAME_USER);
        Assertions.assertThat(response.getLastName()).isEqualTo(LAST_NAME_USER);
        Assertions.assertThat(response.getEmail()).isEqualTo(EMAIL_USER);
        Assertions.assertThat(response.getPassword()).isEqualTo(PASSWORD_USER);
        Assertions.assertThat(response.getRole()).isEqualTo(Role.USER.name());
        Assertions.assertThat(response.getAccessToken()).isEqualTo(ACCESS_TOKEN);
        Assertions.assertThat(response.getRefreshToken()).isEqualTo(REFRESH_TOKEN);
        Assertions.assertThat(response.getExpiresAtAccess()).isEqualTo(EXPIRES_ACCESS);
        Assertions.assertThat(response.getExpiresAtRefresh()).isEqualTo(EXPIRES_REFRESH);
    }

    @Test
    public void toAccessTokenResponseTest() {
        AccessTokenResponseDto response = tokenMapper.toAccessTokenResponse(EMAIL_USER, ACCESS_TOKEN, EXPIRES_ACCESS);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getEmail()).isEqualTo(EMAIL_USER);
        Assertions.assertThat(response.getAccessToken()).isEqualTo(ACCESS_TOKEN);
        Assertions.assertThat(response.getExpiresAtAccess()).isEqualTo(EXPIRES_ACCESS);
    }
}
