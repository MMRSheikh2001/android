package com.MMRSheikh2001.workbridgeandroid.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class AIInterviewSessionResponseDTO {



    private Long applicationId;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private Integer totalScore;

    private Boolean completed;

    private List<InterviewQuestion> questions;

}
