package kr.co.metlife.pseudomgtchannelapi.feature;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ConfigTableDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.ProjectDTO;

import java.util.List;

public interface ProjectFeatureService {

    List<ProjectDTO> getProjectsByUsername(String username) throws JsonProcessingException;

    ProjectDTO saveProject(ProjectDTO projectDTO) throws JsonProcessingException;

    ConfigTableDTO saveConfigTable(String projectId, ConfigTableDTO configTableDTO) throws JsonProcessingException;

    void deleteConfigTable(String projectId, String configTableId) throws JsonProcessingException;

    void deleteProject(String projectId) throws JsonProcessingException;
}
