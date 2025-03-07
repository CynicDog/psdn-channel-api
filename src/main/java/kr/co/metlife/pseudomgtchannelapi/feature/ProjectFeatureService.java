package kr.co.metlife.pseudomgtchannelapi.feature;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ProjectDTO;

import java.util.List;

public interface ProjectFeatureService {

    List<ProjectDTO> getProjectsByUsername(String username) throws JsonProcessingException;
    ProjectDTO saveProject(ProjectDTO projectDTO) throws JsonProcessingException;

}
