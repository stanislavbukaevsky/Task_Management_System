package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер-интерфейс, который преобразует информацию о пользователе в DTO
 */
@Mapper
public interface UserMapper {
    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о пользователе
     *
     * @param user сущность пользователя
     * @return Возвращает сформированную DTO с ответом о пользователе
     */
    RegistrationResponseDto toRegistrationResponseDto(User user);

    /**
     * Этот метод преобразует полученные поля из DTO в модель, для получения информации о пользователе. <br>
     * Используется аннотация {@link Mapping} для игнорирования поля
     *
     * @param registrationRequestDto DTO запроса с информацией о пользователе
     * @return Возвращает сформированную модель с информацией о пользователе
     */
    @Mapping(ignore = true, target = "password")
    User toEntityUser(RegistrationRequestDto registrationRequestDto);
}
