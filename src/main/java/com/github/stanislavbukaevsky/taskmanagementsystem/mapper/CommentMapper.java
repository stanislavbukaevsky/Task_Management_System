package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Comment;
import org.mapstruct.Mapper;

/**
 * Маппер-интерфейс, который преобразует информацию о комментариях в DTO
 */
@Mapper
public interface CommentMapper {
    /**
     * Этот метод преобразует полученные поля из DTO в модель, для получения информации о комментарии
     *
     * @param request DTO запроса с информацией
     * @return Возвращает сформированную модель с информацией о комментарии
     */
    Comment toEntityComment(CommentRequestDto request);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о комментарии
     *
     * @param comment сущность комментария
     * @return Возвращает сформированную DTO с ответом пользователю о комментарии
     */
    CommentResponseDto toCommentResponse(Comment comment);
}
