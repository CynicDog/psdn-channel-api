package kr.co.metlife.pseudomgtchannelapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ProjectDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.ApiResponse;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.Metadata;
import kr.co.metlife.pseudomgtchannelapi.feature.ProjectFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
