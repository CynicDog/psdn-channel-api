package kr.co.metlife.pseudomgtchannelapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ParameterDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.ApiResponse;
import kr.co.metlife.pseudomgtchannelapi.dto.meta.Metadata;
import kr.co.metlife.pseudomgtchannelapi.dto.RuleDTO;
import kr.co.metlife.pseudomgtchannelapi.feature.logic.MetaFeatureLogic;
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

    private final MetaFeatureLogic metaFeatureLogic;

    /**
     * @description 가명화 규칙을 조회합니다.
     * @example http :7999/v1/tenants/KOREA/KUDP/channel/meta/rules
     */
    @GetMapping("/rules")
    public ResponseEntity<ApiResponse<List<RuleDTO>>> getRules() throws JsonProcessingException {

        List<RuleDTO> rules = metaFeatureLogic.getRules();

        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", rules.size());
        ApiResponse<List<RuleDTO>> apiResponse = new ApiResponse<>(rules, metadata);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * @description 가명화 규칙 파라미터를 조회합니다.
     * @example http :7999/v1/tenants/KOREA/KUDP/channel/meta/parameters
     */
    @GetMapping("/parameters")
    public ResponseEntity<ApiResponse<List<ParameterDTO>>> getParameters() throws JsonProcessingException {

        List<ParameterDTO> parameters = metaFeatureLogic.getParameters();

        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", parameters.size());
        ApiResponse<List<ParameterDTO>> apiResponse = new ApiResponse<>(parameters, metadata);

        return ResponseEntity.ok(apiResponse);
    }
}
