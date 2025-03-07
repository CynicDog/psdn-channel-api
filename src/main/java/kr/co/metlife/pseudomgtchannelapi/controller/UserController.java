package kr.co.metlife.pseudomgtchannelapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ProjectDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.ApiResponse;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.Metadata;
import kr.co.metlife.pseudomgtchannelapi.feature.ProjectFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ProjectFeatureService projectFeatureService;

    /**
     * @description 사용자의 가명화 프로젝트를 조회합니다.
     * @example http GET :7999/v1/tenants/KOREA/KUDP/channel/users/JohnDoe/projects
     */
    @GetMapping("/{username}/projects")
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> getUserProjects(@PathVariable String username) throws JsonProcessingException {

        List<ProjectDTO> items = projectFeatureService.getProjectsByUsername(username);
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", items.size());

        ApiResponse<List<ProjectDTO>> apiResponse = new ApiResponse<>(items, metadata);

        return ResponseEntity.ok(apiResponse);
    }
}
