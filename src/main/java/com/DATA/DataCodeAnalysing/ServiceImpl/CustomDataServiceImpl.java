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

	public BaseDTO getDataFromDataCode(DataCodeDetails dataCode) {
		BaseDTO baseDto = new BaseDTO();

		Map<String, Object> queryContent = jdbcTemplate
				.queryForMap("SELECT * FROM application_queries WHERE query_name = ?", dataCode.getDataCode());

		String sql = (String) queryContent.get("query_content");

		Map<String, String> params = dataCode.getPlaceholderKeyValueMap();
		if (params != null && !params.isEmpty()) {
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

		List<Map<String, Object>> listData = jdbcTemplate.queryForList(sql);

		baseDto.setStatusCode(0);
		baseDto.setResponseContent(listData);

		return baseDto;
	}
}
