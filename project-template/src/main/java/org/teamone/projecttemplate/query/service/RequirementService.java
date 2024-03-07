package org.teamone.projecttemplate.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamone.projecttemplate.query.dto.RequirementDTO;
import org.teamone.projecttemplate.query.entity.Requirement;
import org.teamone.projecttemplate.query.repository.RequirementMapper;

import java.util.List;

@Service
public class RequirementService {

    private final RequirementMapper requirementMapper;

    @Autowired
    public RequirementService(RequirementMapper requirementMapper) {
        this.requirementMapper = requirementMapper;
    }

    /* 설명. projectId로 요구사항 명세서 내용 조회 */
    public List<Requirement> findRequirementByProjectId(int projectId) {
        List<Requirement> requirementList = requirementMapper.selectRequirementByProjectId(projectId);

        requirementList.forEach(System.out::println);

        return requirementList;
    }

    /* 설명. separate와 projectId로 요구사항 명세서 내용 조회 */
    public List<Requirement> findRequirementBySeparateAndProjectId(RequirementDTO requirementDTO) {
        Requirement requirement = new Requirement(requirementDTO.getSeparate(), requirementDTO.getProjectId());

        List<Requirement> requirementList = requirementMapper.selectRequirementBySeparateAndProjectId(requirement);

        requirementList.forEach(System.out::println);

        return requirementList;
    }

    /* 설명. 요구사항 id로 요구사항 명세서 조회 */
    public Requirement findRequirementById(int id) {
        Requirement requirement = requirementMapper.selectRequirementById(id);
        System.out.println("requirement = " + requirement);

        return requirement;
    }
}
