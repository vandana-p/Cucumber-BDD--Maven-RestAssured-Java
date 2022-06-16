package utilities;

import java.io.InputStream;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.params.CoreConnectionPNames;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;

import io.restassured.builder.MultiPartSpecBuilder;

import io.restassured.config.HttpClientConfig;

import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;

import io.restassured.http.Method;

import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

import io.restassured.specification.RequestSpecification;

public class RestAssuredUtil {

	private static Logger logger = LoggerFactory.getLogger(RestAssuredUtil.class);

	private int statusCode;

	private String response;

	public String sendRequest(String endPointUrl, String body, Method method, Map<String, String> queryParams,

			Map<String, String> headers, List<MultiPartSpecification> multiPartFile) {

		RestAssuredConfig config = RestAssured.config()

				.httpClient(HttpClientConfig.httpClientConfig().setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000)

						.setParam(CoreConnectionPNames.SO_TIMEOUT, 30000));

		RequestSpecification requestSpecification;

		if (headers == null || headers.isEmpty()) {

			headers = new HashMap<>();

		}

		if (multiPartFile != null && !multiPartFile.isEmpty() && !headers.containsKey("Content-Type")) {

			headers.put("Content-Type", "multipart/form-data");

		} else if (!headers.containsKey("Content-Type")) {

			headers.put("Content-Type", "application/json");

		}

		if (queryParams != null) {

			requestSpecification = RestAssured.given().queryParams(queryParams);

		} else {

			requestSpecification = RestAssured.given();

		}

		requestSpecification = requestSpecification.headers(headers).config(config);

		if (method.equals(Method.GET)) {

			executeGetRequest(endPointUrl, requestSpecification);

		} else if (method.equals(Method.POST)) {

			executePostRequest(endPointUrl, requestSpecification, body, multiPartFile);

		} else if (method.equals(Method.PUT)) {

			executePutRequest(endPointUrl, requestSpecification, body);

		} else if (method.equals(Method.DELETE)) {

			executeDeleteRequest(endPointUrl, requestSpecification);

		}

		return this.response;

	}

	private void executeDeleteRequest(String endPointUrl, RequestSpecification requestSpecification) {

		try {

			final Response response = requestSpecification.body("").filter(new RequestFilter()).delete(endPointUrl);

			this.response = response.asString();

			this.statusCode = response.getStatusCode();
			logger.info("INFO: executed DELETE call ");

		} catch (Exception e) {

			logger.error("ERROR: Exception occurred while executing DELETE call:- {}", e.getMessage());

		}

	}

	private void executePutRequest(String endPointUrl, RequestSpecification requestSpecification, String body) {

		try {

			final Response response = requestSpecification.body(body).filter(new RequestFilter()).put(endPointUrl);

			this.response = response.asString();

			this.statusCode = response.getStatusCode();

			logger.info("INFO: executed PUT call ");

		} catch (Exception e) {

			logger.error("ERROR: Exception occurred while executing PUT call:- {}", e.getMessage());

		}

	}

	private void executePostRequest(String endPointUrl, RequestSpecification requestSpecification, String body,

			List<MultiPartSpecification> multiPartFile) {

		try {

			if (!(multiPartFile == null || multiPartFile.isEmpty())) {

				for (MultiPartSpecification multiPartSpecification : multiPartFile) {

					requestSpecification.multiPart(multiPartSpecification);

				}

			} else {

				requestSpecification = requestSpecification.body(body);

			}

			final Response response = requestSpecification.filter(new RequestFilter()).when().post(endPointUrl);

			this.response = response.asString();

			this.statusCode = response.getStatusCode();

			logger.info("INFO: executed POST call ");

		} catch (Exception e) {

			logger.error("ERROR: Exception occurred while executing POST call:- {}", e.getMessage());

		}

	}

	private void executePostRequest(String endPointUrl, RequestSpecification requestSpecification, String body,

			Map<String, String> multiPartFile) {

		try {

			if (multiPartFile != null) {

				for (Map.Entry<String, String> entry : multiPartFile.entrySet()) {

					if (entry.getKey().equalsIgnoreCase("file")) {

						MultiPartSpecification multiPartSpecification = this.getMultiPartSpecification(entry.getKey(),

								entry.getValue());

						requestSpecification.multiPart(multiPartSpecification);
						logger.info("INFO: Multipart file created ");

					}

					else {

						MultiPartSpecification multiPartSpecification = this.getMultiPartSpecification(entry.getKey(),

								entry.getValue());

						requestSpecification.multiPart(multiPartSpecification);
						logger.info("INFO: Multipart file created ");

					}

				}

			} else {

				requestSpecification = requestSpecification.body(body);
				logger.info("INFO: Multipart file created ");

			}

			final Response response = requestSpecification.filter(new RequestFilter()).when().post(endPointUrl);

			this.response = response.asString();

			this.statusCode = response.getStatusCode();
			logger.info("INFO: executed POST call ");

		} catch (Exception e) {

			logger.error("ERROR: Exception occurred while executing POST call:- {}", e.getMessage());

		}

	}

	private void executeGetRequest(String endPointUrl, RequestSpecification requestSpecification) {

		try {

			final Response response = requestSpecification.body("").filter(new RequestFilter()).get(endPointUrl);

			this.response = response.asString();

			this.statusCode = response.getStatusCode();

			logger.info("INFO: executed GET call ");

		} catch (Exception e) {

			logger.error("ERROR: Exception occurred while executing GET call:- {}", e.getMessage());

		}

	}

	public int getStatusCode() {

		return this.statusCode;

	}

	public String getResponse() {

		return this.response;

	}

	private MultiPartSpecification getMultiPartSpecification(String controlName, String filePath) {

		try {

			String contentType;

			InputStream fileContent = this.getClass().getClassLoader().getResourceAsStream(filePath);

			if (filePath.endsWith("json")) {

				contentType = ContentType.JSON.toString();

			} else {

				contentType = ContentType.MULTIPART.toString();

			}

			MultiPartSpecification multiPartSpecification = new MultiPartSpecBuilder(fileContent)

					.controlName(controlName).mimeType(contentType).build();

			return multiPartSpecification;

		} catch (Exception e) {

			logger.info("Exception occured while creating MultiPartSpecification. Exception:- {}", e.getMessage());

		}

		return null;

	}

	public static MultiPartSpecification createMultiPartSpecification(String fileContent, String controlName,

			String mimeType) {

		try {

			MultiPartSpecification multiPartSpecification = new MultiPartSpecBuilder(IOUtils.toInputStream(fileContent))

					.controlName(controlName).mimeType(mimeType).build();
			logger.info("MultiPartSpecification created successfully");

			return multiPartSpecification;

		} catch (Exception e) {

			logger.info("Exception occured while creating MultiPartSpecification. Exception:- {}", e.getMessage());

		}

		return null;

	}

}