package kr.co.metlife.pseudomgtchannelapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ParameterDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.TableDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.ApiResponse;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.Metadata;
import kr.co.metlife.pseudomgtchannelapi.dto.RuleDTO;
import kr.co.metlife.pseudomgtchannelapi.feature.MetaFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meta")
@RequiredArgsConstructor
public class MetaController {

    private final MetaFeatureService metaFeatureService;

    /**
     * @description 가명화 규칙을 조회합니다.
     * @example http GET :7999/v1/tenants/KOREA/KUDP/channel/meta/rules
     */
    @GetMapping("/rules")
    public ResponseEntity<ApiResponse<List<RuleDTO>>> getRules() throws JsonProcessingException {

        List<RuleDTO> item = metaFeatureService.getRules();
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", item.size());

        ApiResponse<List<RuleDTO>> apiResponse = new ApiResponse<>(item, metadata);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * @description 가명화 규칙 파라미터를 조회합니다.
     * @example http GET :7999/v1/tenants/KOREA/KUDP/channel/meta/parameters
     */
    @GetMapping("/parameters")
    public ResponseEntity<ApiResponse<List<ParameterDTO>>> getParameters() throws JsonProcessingException {

        List<ParameterDTO> item = metaFeatureService.getParameters();
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", item.size());

        ApiResponse<List<ParameterDTO>> apiResponse = new ApiResponse<>(item, metadata);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * @description 가명화 대상 원본 테이블 메타 데이터를 조회합니다.
     * @example http GET :7999/v1/tenants/KOREA/KUDP/channel/meta/tables
     */
    @GetMapping("/tables")
    public ResponseEntity<ApiResponse<List<TableDTO>>> getTables() throws JsonProcessingException {

        List<TableDTO> item = metaFeatureService.getTables();
        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", item.size());

        ApiResponse<List<TableDTO>> apiResponse = new ApiResponse<>(item, metadata);

        return ResponseEntity.ok(apiResponse);
    }
}
