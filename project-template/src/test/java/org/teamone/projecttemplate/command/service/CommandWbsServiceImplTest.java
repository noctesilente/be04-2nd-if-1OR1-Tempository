package org.teamone.projecttemplate.command.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.teamone.projecttemplate.command.dto.CommandWbsDTO;
import org.teamone.projecttemplate.command.entity.CommandWbs;
import org.teamone.projecttemplate.query.dto.WbsDTO;
import org.teamone.projecttemplate.query.entity.Wbs;
import org.teamone.projecttemplate.query.service.WbsService;

import java.util.List;


@SpringBootTest
public class CommandWbsServiceImplTest {

    @Autowired
    private CommandWbsServiceImpl commandWbsServiceImpl;

    @Autowired
    private WbsService wbsService;

    @DisplayName("WBS 추가")
    @Test
    void addWbs() {

        // Given
        CommandWbsDTO commandWbsDTO = new CommandWbsDTO();
        commandWbsDTO.setTaskStatus("IN_PROGRESS");
        commandWbsDTO.setContent("WBS 추가 테스트");
        commandWbsDTO.setStartDate(null);
        commandWbsDTO.setEndDate(null);
        commandWbsDTO.setProjectId(2);
        commandWbsDTO.setManagerId(1);

        // When
        commandWbsServiceImpl.addWbs(commandWbsDTO);

        // Then
        WbsDTO wbsDTO = wbsService.findWbsByProjectIdAndWbsNo(2, 1);

        Assertions.assertNotNull(wbsDTO);
    }

    @DisplayName("WBS 수정")
    @Test
    void modifyWbs() {

        // Given
        CommandWbsDTO commandWbsDTO = new CommandWbsDTO();

        commandWbsDTO.setWbsNo(1);
        commandWbsDTO.setProjectId(1);
        commandWbsDTO.setTaskStatus("COMPLETED");
        commandWbsDTO.setContent("WBS 수정 테스트");
        commandWbsDTO.setStartDate(null);
        commandWbsDTO.setEndDate(null);
        commandWbsDTO.setManagerId(5);

        // When
        commandWbsServiceImpl.modifyWbs(commandWbsDTO);

        // Then
        WbsDTO modifiedWbsDTO = wbsService.findWbsByProjectIdAndWbsNo(1, 1);

        Assertions.assertEquals(commandWbsDTO.getWbsNo(), modifiedWbsDTO.getWbsNo());
        Assertions.assertEquals(commandWbsDTO.getContent(), modifiedWbsDTO.getContent());
        Assertions.assertEquals(commandWbsDTO.getTaskStatus(), modifiedWbsDTO.getTaskStatus());
        Assertions.assertEquals(commandWbsDTO.getStartDate(), modifiedWbsDTO.getStartDate());
        Assertions.assertEquals(commandWbsDTO.getEndDate(), modifiedWbsDTO.getEndDate());
        Assertions.assertEquals(commandWbsDTO.getProjectId(), modifiedWbsDTO.getProjectId());
        Assertions.assertEquals(commandWbsDTO.getManagerId(), modifiedWbsDTO.getManagerId());
    }

    @DisplayName("WBS 전부 COMPLETED 상태로 바꾸기")
    @Test
    void modifyAllWbsStatusToCompleted() {

        // Given
        int projectId = 1;

        // When
        List<CommandWbs> modifiedCommandWbsList = commandWbsServiceImpl.modifyAllWbsStatusToCompleted(projectId);

        // Then
        for (CommandWbs commandWbs : modifiedCommandWbsList) {
            Assertions.assertEquals("COMPLETED", commandWbs.getTaskStatus());
        }
    }

    @DisplayName("WBS 삭제")
    @Test
    void removeWbs() {

        // Given
        int projectId = 1;
        int wbsNo = 1;

        // When
        commandWbsServiceImpl.removeWbs(projectId, wbsNo);

        // Then
        WbsDTO deletedWbsDTO = wbsService.findWbsByProjectIdAndWbsNo(1, 1);

        // WBS NO가 자동 업데이트 되므로 NO가 빌 수 없음
        Assertions.assertNotEquals(null, deletedWbsDTO);
    }

    @DisplayName("프로젝트 ID에 해당하는 WBS 전체 삭제")
    @Test
    void removeAllWbsByProjectId() {

        // Given
        int projectId = 1;

        // When
        commandWbsServiceImpl.removeAllWbsByProjectId(projectId);

        // Then
        Assertions.assertEquals(wbsService.findAllWbsByProjectId(1).size(), 0);
    }
}