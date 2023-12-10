package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskFullResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Comment;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Task;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Priority;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ParametersForEntityTest.*;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {
    @InjectMocks
    private TaskMapperImpl taskMapper;

    @Test
    public void toEntityTaskTest() {
        TaskRequestDto request = new TaskRequestDto();
        request.setHeading(HEADING_TASK);
        request.setDescription(DESCRIPTION_TASK);

        Task task = taskMapper.toEntityTask(request);
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getHeading()).isEqualTo(HEADING_TASK);
        Assertions.assertThat(task.getDescription()).isEqualTo(DESCRIPTION_TASK);
    }

    @Test
    public void toTaskResponseTest() {
        Task task = new Task();
        task.setId(ID_TASK);
        task.setHeading(HEADING_TASK);
        task.setDescription(DESCRIPTION_TASK);
        task.setDateTime(DATE_TIME);
        task.setStatus(Status.IN_WAITING);
        task.setPriority(Priority.HIGH);

        TaskResponseDto response = taskMapper.toTaskResponse(task);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(ID_TASK);
        Assertions.assertThat(response.getHeading()).isEqualTo(HEADING_TASK);
        Assertions.assertThat(response.getDescription()).isEqualTo(DESCRIPTION_TASK);
        Assertions.assertThat(response.getDateTime()).isEqualTo(DATE_TIME);
        Assertions.assertThat(response.getStatus()).isEqualTo(Status.IN_WAITING.getDescription());
        Assertions.assertThat(response.getPriority()).isEqualTo(Priority.HIGH.getDescription());
    }

    @Test
    public void toTaskFullResponseTest() {
        Comment commentOne = new Comment();
        commentOne.setId(ID_COMMENT);
        commentOne.setText(TEXT_COMMENT);
        commentOne.setDateTime(DATE_TIME);

        Comment commentTwo = new Comment();
        commentTwo.setId(ID_COMMENT_2);
        commentTwo.setText(TEXT_COMMENT_2);
        commentTwo.setDateTime(DATE_TIME_2);

        Task task = new Task();
        task.setId(ID_TASK);
        task.setHeading(HEADING_TASK);
        task.setDescription(DESCRIPTION_TASK);
        task.setDateTime(DATE_TIME);
        task.setStatus(Status.IN_WAITING);
        task.setPriority(Priority.HIGH);
        List<Comment> comments = new ArrayList<>();
        comments.add(commentOne);
        comments.add(commentTwo);

        TaskFullResponseDto response = taskMapper.toTaskFullResponse(task);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(ID_TASK);
        Assertions.assertThat(response.getHeading()).isEqualTo(HEADING_TASK);
        Assertions.assertThat(response.getDescription()).isEqualTo(DESCRIPTION_TASK);
        Assertions.assertThat(response.getDateTime()).isEqualTo(DATE_TIME);
        Assertions.assertThat(response.getStatus()).isEqualTo(Status.IN_WAITING.getDescription());
        Assertions.assertThat(response.getPriority()).isEqualTo(Priority.HIGH.getDescription());
        Assertions.assertThat(comments.get(0).getId()).isEqualTo(ID_COMMENT);
        Assertions.assertThat(comments.get(0).getText()).isEqualTo(TEXT_COMMENT);
        Assertions.assertThat(comments.get(0).getDateTime()).isEqualTo(DATE_TIME);
        Assertions.assertThat(comments.get(1).getId()).isEqualTo(ID_COMMENT_2);
        Assertions.assertThat(comments.get(1).getText()).isEqualTo(TEXT_COMMENT_2);
        Assertions.assertThat(comments.get(1).getDateTime()).isEqualTo(DATE_TIME_2);
    }
}
