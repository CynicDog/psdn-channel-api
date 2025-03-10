package kr.co.metlife.pseudomgtchannelapi.feature.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.metlife.pseudomgtchannelapi.dto.ConfigTableDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.ProjectDTO;
import kr.co.metlife.pseudomgtchannelapi.feature.ProjectFeatureService;
import kr.co.metlife.pseudomgtchannelapi.proxy.logic.SystemProxyLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectFeatureLogic implements ProjectFeatureService {

    private final SystemProxyLogic systemProxyLogic;
    private final ObjectMapper objectMapper;

    @Override
    public List<ProjectDTO> getProjectsByUsername(String username) throws JsonProcessingException {

        ResponseEntity<String> response = systemProxyLogic.get("/projects?username=" + username);
        List<ProjectDTO> projects = objectMapper.readValue(response.getBody(), new TypeReference<List<ProjectDTO>>() {});

        return projects;
    }

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) throws JsonProcessingException {

        ResponseEntity<String> response = systemProxyLogic.post("/projects/save", objectMapper.writeValueAsString(projectDTO));
        ProjectDTO project = objectMapper.readValue(response.getBody(), ProjectDTO.class);

        return project;
    }

    @Override
    public List<ProjectDTO> saveAllProjects(List<ProjectDTO> projectDTOList) throws JsonProcessingException {

        ResponseEntity<String> response = systemProxyLogic.post("/projects/saveAll", objectMapper.writeValueAsString(projectDTOList));
        List<ProjectDTO> projects = objectMapper.readValue(response.getBody(), new TypeReference<List<ProjectDTO>>() {});

        return projects;
    }

    @Override
    public ConfigTableDTO saveConfigTable(String projectId, ConfigTableDTO configTableDTO) throws JsonProcessingException {

        ResponseEntity<String> response = systemProxyLogic.post(String.format("/projects/%s/configTable/save", projectId), objectMapper.writeValueAsString(configTableDTO));
        ConfigTableDTO configTable = objectMapper.readValue(response.getBody(), ConfigTableDTO.class);

        return configTable;
    }

    @Override
    public void deleteConfigTable(String projectId, String configTableId) throws JsonProcessingException {
        systemProxyLogic.delete(String.format("/projects/%s/configTables/%s/delete", projectId, configTableId));
    }

    @Override
    public void deleteProject(String projectId) throws JsonProcessingException {
        systemProxyLogic.delete(String.format("/projects/%s/delete", projectId));
    }
}
