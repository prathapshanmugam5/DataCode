package com.DATA.DataCodeAnalysing.ServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;
import com.DATA.DataCodeAnalysing.Dto.DataCodeDetails;
import com.DATA.DataCodeAnalysing.Repositary.ApplicationQueryRepositary;
import com.DATA.DataCodeAnalysing.Service.CustomDataService;

@Service
public class CustomDataServiceImpl implements CustomDataService {

    @Autowired
    ApplicationQueryRepositary applicationQueryDTORepositary;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{(\\w+)}");

    @Override
    public BaseDTO getDataFromDataCode(DataCodeDetails dataCode) {
        BaseDTO baseDto = new BaseDTO();

        // Retrieve the query content
        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList("SELECT * FROM application_queries WHERE query_name = ?", dataCode.getDataCode());
        
        if (queryResult.isEmpty()) {
            baseDto.setStatusCode(1); // Set an error status code
            baseDto.setResponseContent(Collections.singletonMap("error", "No query found for the given data code"));
            return baseDto;
        }

        // Assuming only one row is returned for a specific data code
        Map<String, Object> queryContent = queryResult.get(0);
        String sql = (String) queryContent.get("query_content");

        Map<String, String> params = dataCode.getPlaceholderKeyValueMap();
        if (params != null) {
            Matcher matcher = PLACEHOLDER_PATTERN.matcher(sql);
            Set<String> placeholders = new HashSet<>();
            while (matcher.find()) {
                placeholders.add(matcher.group(1));
            }

            if (!params.keySet().containsAll(placeholders)) {
                baseDto.setStatusCode(1); // Set an error status code
                baseDto.setResponseContent(Collections.singletonMap("error", "Missing required parameters"));
                return baseDto;
            }

            for (Map.Entry<String, String> entry : params.entrySet()) {
                String placeholder = "\\$\\{" + entry.getKey() + "\\}"; // Escape the ${} for regex
                String value = entry.getValue();
                sql = sql.replaceAll(placeholder, value);
            }
        }

        // Execute the final query and fetch results
        List<Map<String, Object>> listData = jdbcTemplate.queryForList(sql);
        
        if(listData.isEmpty()) {
        	 baseDto.setStatusCode(1);
             baseDto.setResponseContent(listData);
             baseDto.setErrorMessage(null);
        	return baseDto;
        }
        if(!listData.isEmpty()) {
       	 baseDto.setStatusCode(0);
            baseDto.setResponseContent(listData);
            baseDto.setErrorMessage("Success");
            return baseDto;
       }

       

        return baseDto;
    }
}
