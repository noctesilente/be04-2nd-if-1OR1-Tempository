package org.teamone.tempository.project.query.dto;

import lombok.*;
import org.teamone.tempository.project.query.type.ProjectStatus;
import org.teamone.tempository.project.query.vo.ResponseIssue;
import org.teamone.tempository.project.query.vo.ResponseWbs;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class ProjectDTO {
    private int id;
    private String name;

    private boolean isPublic;

    private int likeCnt;

    private ProjectStatus status;

    private String content;

    private List<ProjectMemberDTO> projectMemberList;

    private List<ResponseIssue> issueList;

    private List<ResponseWbs> wbsList;


}
