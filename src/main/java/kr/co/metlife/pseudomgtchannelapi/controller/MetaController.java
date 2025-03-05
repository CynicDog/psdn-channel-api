package kr.co.metlife.pseudomgtchannelapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.metlife.pseudomgtchannelapi.dto.ApiResponse;
import kr.co.metlife.pseudomgtchannelapi.dto.Metadata;
import kr.co.metlife.pseudomgtchannelapi.dto.RuleDTO;
import kr.co.metlife.pseudomgtchannelapi.feature.logic.MetaFeatureLogic;
import kr.co.metlife.pseudomgtchannelapi.proxy.logic.SystemProxyLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meta")
@RequiredArgsConstructor
public class MetaController {

    @Value("${info.application.name}")
    private String baseUrl;

    private final MetaFeatureLogic metaFeatureLogic;

    /**
     * @description 가명화 규칙을 조회합니다.
     * @example http :7999/v1/tenants/KOREA/KUDP/channel/meta/rules
     */
    @GetMapping("/rules")
    public ResponseEntity<ApiResponse<List<RuleDTO>>> getRules() throws JsonProcessingException {

        List<RuleDTO> rules = metaFeatureLogic.getRules();

        Metadata metadata = new Metadata(System.currentTimeMillis(), "JohnDoe", baseUrl, rules.size());
        ApiResponse<List<RuleDTO>> apiResponse = new ApiResponse<>(rules, metadata);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * @description 가명화 규칙 파라미터를 조회합니다.
     * @example http :7999/v1/tenants/KOREA/KUDP/channel/meta/rules
     */
    @GetMapping("/parameters")
    public ResponseEntity<String> getParameters() {

        return ResponseEntity.ok().body("");
    }
}
