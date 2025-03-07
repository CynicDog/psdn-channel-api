package kr.co.metlife.pseudomgtchannelapi.feature.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.metlife.pseudomgtchannelapi.dto.ParameterDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.RuleDTO;
import kr.co.metlife.pseudomgtchannelapi.dto.TableDTO;
import kr.co.metlife.pseudomgtchannelapi.feature.MetaFeatureService;
import kr.co.metlife.pseudomgtchannelapi.proxy.logic.SystemProxyLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MetaFeatureLogic implements MetaFeatureService {

    private final SystemProxyLogic systemProxyLogic;
    private final ObjectMapper objectMapper;

    @Override
    public List<RuleDTO> getRules() throws JsonProcessingException {

        ResponseEntity<String> response = systemProxyLogic.get("/meta/rules");
        List<RuleDTO> rules = objectMapper.readValue(response.getBody(), new TypeReference<List<RuleDTO>>() {});

        return rules;
    }

    @Override
    public List<ParameterDTO> getParameters() throws JsonProcessingException {

        ResponseEntity<String> response = systemProxyLogic.get("/meta/parameters");
        List<ParameterDTO> parameters = objectMapper.readValue(response.getBody(), new TypeReference<List<ParameterDTO>>() {});

        return parameters;
    }

    @Override
    public List<TableDTO> getTables() throws JsonProcessingException {

        ResponseEntity<String> response = systemProxyLogic.get("/meta/tables");
        List<TableDTO> tables = objectMapper.readValue(response.getBody(), new TypeReference<List<TableDTO>>() {});

        return tables;
    }
}
