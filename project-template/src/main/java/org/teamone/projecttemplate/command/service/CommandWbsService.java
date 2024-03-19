package org.teamone.projecttemplate.command.service;

import org.teamone.projecttemplate.command.dto.CommandWbsDTO;
import org.teamone.projecttemplate.command.entity.CommandWbs;
import org.teamone.projecttemplate.command.vo.CommandWbsRequest;

import java.util.List;

public interface CommandWbsService {

    void addWbs(CommandWbsDTO wbsDTO);

    CommandWbsDTO addWbsByProjectId(CommandWbsDTO wbsDTO);

    void addManyWbsByProjectId(int projectId, List<CommandWbsRequest> wbs);

    void modifyWbs(CommandWbsDTO wbsDTO);

    List<CommandWbs> modifyAllWbsStatusToCompleted(int projectId);

    void modifyWbsContentByProjectIdAndWbsNo(int projectId, int wbsNo, String content);

    CommandWbsDTO removeWbs(int projectId, int wbsNo);

    void removeAllWbsByProjectId(int projectId);

}
