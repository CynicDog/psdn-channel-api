package kr.co.metlife.pseudomgtchannelapi.feature.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
