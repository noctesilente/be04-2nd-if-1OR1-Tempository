package org.teamone.tempository.project.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamone.tempository.project.query.client.ProjectIssueClient;
import org.teamone.tempository.project.query.client.ProjectServiceClient;
import org.teamone.tempository.project.query.client.ProjectWbsClient;
import org.teamone.tempository.project.query.dao.ProjectMapper;
import org.teamone.tempository.project.query.dto.ProjectDTO;
import org.teamone.tempository.project.query.dto.ProjectMemberDTO;
import org.teamone.tempository.project.query.entity.Project;
import org.teamone.tempository.project.query.entity.ProjectMember;
import org.teamone.tempository.project.query.type.ProjectStatus;
import org.teamone.tempository.project.query.vo.ResponseIssue;
import org.teamone.tempository.project.query.vo.ResponseUser;
import org.teamone.tempository.project.query.vo.ResponseWbs;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {


    private final ProjectMapper projectMapper;
    private final ProjectServiceClient projectServiceClient;

    private final ProjectIssueClient projectIssueClient;

    private final ProjectWbsClient projectWbsClient;

    @Autowired
    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectServiceClient projectServiceClient,
                              ProjectIssueClient projectIssueClient, ProjectWbsClient projectWbsClient) {
        this.projectMapper = projectMapper;
        this.projectServiceClient = projectServiceClient;
        this.projectIssueClient = projectIssueClient;
        this.projectWbsClient = projectWbsClient;

    }

    /* 설명. ID를 이용하여 프로젝트 정보 조회 */
    @Override
    public List<Project> findProjectInfoById(String id) {

        List<Project> findProjectInfoById = projectMapper.findProjectInfoById(id);

        if (findProjectInfoById != null && findProjectInfoById.size() > 0) {

            findProjectInfoById.forEach(System.out::println);

            return findProjectInfoById;

        }

        System.out.println("프로젝트가 존재하지 않습니다.");
        throw new IllegalArgumentException("프로젝트가 존재하지않습니다.");


    }


    /* 설명. Status를 이용하여 프로젝트 완료나 미완료 상태인 프로젝트를 조회 */
    public List<Project> findProjectInfoByStatus(ProjectStatus Status) {

        List<Project> findProjectInfoByStatus = projectMapper.findProjectInfoByStatus(Status);

        findProjectInfoByStatus.forEach(System.out::println);

        return findProjectInfoByStatus;
    }

    /* 설명. 좋아요 순으로 프로젝트 조회 기능 */
    public List<Project> findProjectOrderByLike() {

        List<Project> findProjectInfoOrderByLike = projectMapper.findProjectOrderByLike();
        findProjectInfoOrderByLike.forEach(System.out::println);

        return findProjectInfoOrderByLike;
    }

    /* 설명. 공개 유무에 따른 프로젝트 조회 기능 */

    public List<Project> findProjectInfoByIsPublic(boolean isPublic) {

        if (!isPublic) {
            System.out.println("조회할 수 없는 프로젝트입니다.");
            throw new IllegalArgumentException("조회할 수 없는 프로젝트입니다.");
        }

        List<Project> findProjectInfoByIsPublic = projectMapper.findProjectInfoByIsPublic(isPublic);
        findProjectInfoByIsPublic.forEach(System.out::println);

        return findProjectInfoByIsPublic;
    }


    /* 설명. 프로젝트 참여 회원 조회 기능 */
    @Override
    public List<ProjectDTO> findProjectJoinMemberByMemberId(String id, String token) {

        List<Project> project = projectMapper.findProjectJoinMemberByMemberId(id);


        List<ProjectDTO> projectDTOJoinMember = projectToProjectDTOMember(project, token, id);


        return projectDTOJoinMember;
    }

    private List<ProjectDTO> projectToProjectDTOMember(List<Project> projectList, String token, String id) {

        List<ProjectDTO> projectDTOMemberList = new ArrayList<>();

        List<ResponseUser> userList = projectServiceClient.findProjectMembers(id, token);


        for (Project project : projectList) {
            ProjectDTO projectDTO = new ProjectDTO();

            projectDTO.setId(project.getId());
            projectDTO.setStatus(project.getStatus());
            projectDTO.setName(project.getName());

            List<ProjectMember> projectMemberList = project.getProjectMemberList();

            List<ProjectMemberDTO> projectMemberDTOList = new ArrayList<>();

            for (ProjectMember projectMember : projectMemberList) {

                ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO();

                projectMemberDTO.setProjectId(projectMember.getProjectId());
                projectMemberDTO.setMemberId(projectMember.getMemberId());
                projectMemberDTO.setMemberStatus(projectMember.getMemberStatus());
                projectMemberDTO.setPosition(projectMember.getPosition());

                projectMemberDTO.setUsers(userList);


                projectMemberDTOList.add(projectMemberDTO);
            }

            projectDTO.setProjectMemberList(projectMemberDTOList);
            projectDTOMemberList.add(projectDTO);
        }

        return projectDTOMemberList;
    }

    /* 설명. 프로젝트 내용 검색을 통한 프로젝트 조회 기능 */
    @Override
    public List<Project> findProjectInfoByContent(String content) {

        List<Project> findProjectByContent = projectMapper.findProjectInfoByContent(content);
        findProjectByContent.forEach(System.out::println);

        return findProjectByContent;
    }

    /* 설명. 프로젝트 이름 검색을 통한 프로젝트 조회 기능 */

    @Override
    public List<Project> findProjectInfoByName(String name) {
        List<Project> findProjectByName = projectMapper.findProjectInfoByName(name);
        findProjectByName.forEach(System.out::println);

        return findProjectByName;
    }

    @Override
    public List<ProjectDTO> findProjectNameById(String id) {
        List<Project> findProjectInfoById = projectMapper.findProjectNameById(id);

        List<ProjectDTO> projectDTOProject = projectToProjectDTOProject(findProjectInfoById);

        if (findProjectInfoById != null && findProjectInfoById.size() > 0) {

            findProjectInfoById.forEach(System.out::println);

        } else {

            System.out.println("프로젝트가 존재하지 않습니다.");
            throw new IllegalArgumentException("프로젝트가 존재하지 않습니다.");

        }

        return projectDTOProject;
    }

    private List<ProjectDTO> projectToProjectDTOProject(List<Project> findProjectInfoById) {
        List<ProjectDTO> projectDTOProjectList = new ArrayList<>();

        for (Project project : findProjectInfoById) {
            ProjectDTO projectDTO = new ProjectDTO();

            projectDTO.setId(project.getId());
            projectDTO.setStatus(project.getStatus());
            projectDTO.setLikeCnt(project.getLikeCnt());
            projectDTO.setPublic(project.isPublic());
            projectDTO.setName(project.getName());
            projectDTO.setContent(project.getContent());

            projectDTOProjectList.add(projectDTO);
        }

        return projectDTOProjectList;
    }


    // 변환 파일 생성 이어서 하기
    @Override
    public List<ProjectDTO> findProjectIssueById(String id, String token) {

        List<Project> findProjectIssueById = projectMapper.findProjectIssueById(id);
        List<ProjectDTO> projectDTOIssueList = projectToProjectDTOIssueList(findProjectIssueById, token, id);
        return projectDTOIssueList;
    }

    private List<ProjectDTO> projectToProjectDTOIssueList(List<Project> findProjectIssueById, String token, String id) {
        List<ProjectDTO> projectIssueList = new ArrayList<>();

        for (Project project : findProjectIssueById) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(project.getId());
            List<ResponseIssue> issueList = projectIssueClient.findProjectIssue(id, token);

            projectDTO.setIssueList(issueList);
            projectIssueList.add(projectDTO);
        }


        return projectIssueList;
    }

    @Override
    public List<ProjectDTO> findProjectWbsById(String id, String token) {

        List<Project> findProjectWbsById = projectMapper.findProjectWbsById(id);

        List<ProjectDTO> projectDTOWbsList = projectToProjectDTOWbs(findProjectWbsById, token, id);

        return projectDTOWbsList;
    }

    private List<ProjectDTO> projectToProjectDTOWbs(List<Project> findProjectWbsById, String token, String id) {
        List<ProjectDTO> projectWbsList = new ArrayList<>();


        for (Project project : findProjectWbsById) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(project.getId());

            List<ResponseWbs> wbsList = projectWbsClient.findWbsList(id, token);

            projectDTO.setWbsList(wbsList);
            projectWbsList.add(projectDTO);
        }

        return projectWbsList;
    }
}