package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Comment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ParametersForEntityTest.*;

@ExtendWith(MockitoExtension.class)
public class CommentMapperTest {
    @InjectMocks
    private CommentMapperImpl commentMapper;

    @Test
    public void toEntityCommentTest() {
        CommentRequestDto request = new CommentRequestDto();
        request.setText(TEXT_COMMENT);

        Comment comment = commentMapper.toEntityComment(request);
        Assertions.assertThat(comment).isNotNull();
        Assertions.assertThat(comment.getText()).isEqualTo(TEXT_COMMENT);
    }

    @Test
    public void toCommentResponseTest() {
        Comment comment = new Comment();
        comment.setId(ID_COMMENT);
        comment.setText(TEXT_COMMENT);
        comment.setDateTime(DATE_TIME);

        CommentResponseDto response = commentMapper.toCommentResponse(comment);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(ID_COMMENT);
        Assertions.assertThat(response.getText()).isEqualTo(TEXT_COMMENT);
        Assertions.assertThat(response.getDateTime()).isEqualTo(DATE_TIME);
    }
}
