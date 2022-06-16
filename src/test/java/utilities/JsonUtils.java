package utilities;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.path.json.JsonPath;

public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public static String getJsonValueByKey(String response, String key) {
		JsonPath jsonPath = new JsonPath(response);
		String value = jsonPath.getString(key);
		logger.info(key+" key value retrieved is"+value);
		return value;
	}

	public static List<String> getListofJsonValueByKey(String response, String key) {
		JsonPath jsonPath = new JsonPath(response);
		List<String> value = jsonPath.getList(key);
		logger.info(key+" key value retrieved is"+value);
		return value;
	}

}
