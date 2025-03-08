package kr.co.metlife.pseudomgtchannelapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ConfigTableDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.ProjectDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.ApiResponse;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.Metadata;
import kr.co.metlife.pseudomgtchannelapi.feature.ProjectFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectFeatureService projectFeatureService;

    // TODO: more resilient and cleaner Exception handling
    // TODO: RBAC on APIs (PreAuthorize with Role parsing filter in the Spring Security flow)
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<ProjectDTO>> saveProject(@RequestBody ProjectDTO payload) throws JsonProcessingException {

        ProjectDTO item = projectFeatureService.saveProject(payload);
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", 1); // TODO: replace "JohnDoe" with such of `var username =  SecurityContext.getUsername()`

        ApiResponse<ProjectDTO> apiResponse = new ApiResponse<>(item, metadata);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{projectId}/configTable/save")
    public ResponseEntity<ApiResponse<ConfigTableDTO>> saveConfigTable(@PathVariable String projectId, @RequestBody ConfigTableDTO payload) throws JsonProcessingException {

        ConfigTableDTO item = projectFeatureService.saveConfigTable(projectId, payload);
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", 1);

        ApiResponse<ConfigTableDTO> apiResponse = new ApiResponse<>(item, metadata);

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{projectId}/configTables/{configTableId}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteConfigTable(@PathVariable String projectId, @PathVariable String configTableId) throws JsonProcessingException {

        projectFeatureService.deleteConfigTable(projectId, configTableId);
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", 1);

        ApiResponse<Void> apiResponse = new ApiResponse<>(null, metadata);

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{projectId}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable String projectId) throws JsonProcessingException {

        projectFeatureService.deleteProject(projectId);
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", 1);

        ApiResponse<Void> apiResponse = new ApiResponse<>(null, metadata);

        return ResponseEntity.ok(apiResponse);
    }
}
