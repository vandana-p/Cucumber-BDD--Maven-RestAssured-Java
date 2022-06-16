package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.swing.text.AbstractDocument.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.MultiPartSpecification;
import utilities.AssertionConstants;
import utilities.Base;
import utilities.ConversionUtility;
import utilities.FileUtils;
import utilities.JsonUtils;
import utilities.RestAssuredUtil;

public class PetStoreStepDef extends Base {
	private static Logger logger = LoggerFactory.getLogger(PetStoreStepDef.class);

	RestAssuredUtil rs = new RestAssuredUtil();
	private String petPayload;
	private String orderPayload;
	private String userPayload;
	private String userListPayload;
	private String petImage;
	private String petId;
	private int statusCode;
	private String response;
	private String orderId;
	private String userId;
	private String userName;
	private String userFn;
	private String userLn;
	private String userEmail;
	private String userPassword;
	private String userPhone;
	private String userStatus;
	private String userId1;
	private String userName1;
	private String userFn1;
	private String userLn1;
	private String userEmail1;
	private String userPassword1;
	private String userPhone1;
	private String userStatus1;

	// Storing url in a variables

	// pet
	private String peturl = urlprop.getProperty("api.absoluteurl") + urlprop.getProperty("api.peturl");
	private String getPetByStatusUrl = urlprop.getProperty("api.absoluteurl")
			+ urlprop.getProperty("api.getpetbystatusurl");
	private String uploadPetImageUrl = urlprop.getProperty("api.uploadpetimageurl");

	// Store
	private String getinventorystatusurl = urlprop.getProperty("api.absoluteurl")
			+ urlprop.getProperty("api.getinventorystatusurl");
	private String orderpeturl = urlprop.getProperty("api.absoluteurl") + urlprop.getProperty("api.orderpeturl");

	// User
	private String userloginurl = urlprop.getProperty("api.absoluteurl") + urlprop.getProperty("api.userloginurl");
	private String userlogouturl = urlprop.getProperty("api.absoluteurl") + urlprop.getProperty("api.userlogouturl");
	private String userurl = urlprop.getProperty("api.absoluteurl") + urlprop.getProperty("api.userurl");
	private String createuserwithlist = urlprop.getProperty("api.absoluteurl")
			+ urlprop.getProperty("api.createuserwithlist");

	// Storing file path in a variable
	private String createPetPayload = System.getProperty("user.dir")
			+ testdataprop.getProperty("createpet.relativepath");
	private String orderPetPayload = System.getProperty("user.dir")
			+ testdataprop.getProperty("orderpetfromstore.relativepath");
	private String createUserPayload = System.getProperty("user.dir")
			+ testdataprop.getProperty("createuser.relativepath");
	private String createUserWithListPayload = System.getProperty("user.dir")
			+ testdataprop.getProperty("createuserwithlist.relativepath");

	@Given("Pet payload with petname {string}, pet id {string} and status {string}")
	public void pet_payload_with_petname_pet_id_and_status(String string, String string2, String string3)
			throws IOException {
		petId = string2;

		List<String> oldValue = List.of("idvalue", "namevalue", "statusvalue"); // Updating the json with desired
																				// testdata at runtime
		List<String> newValue = List.of(string2, string, string3);
		petPayload = ConversionUtility.updateJsonFile(createPetPayload, oldValue, newValue);
		logger.info("INFO: Payload created :" + petPayload);

	}

	@Given("Pet {string}, pet id {string} and status {string} is added in the store")
	public void pet_pet_id_and_status_is_added_in_the_store(String string, String string2, String string3)
			throws IOException {
		petId = string2;

		// Updating the json with desired testdata at runtime
		List<String> oldValue = List.of("idvalue", "namevalue", "statusvalue");
		List<String> newValue = List.of(string2, string, string3);
		petPayload = ConversionUtility.updateJsonFile(createPetPayload, oldValue, newValue);
		logger.info("INFO: Payload created :" + petPayload);
		response = rs.sendRequest(peturl, petPayload, Method.POST, null, null, null);
		statusCode = rs.getStatusCode();
		logger.info("INFO: Response of API call is:" + response);
		logger.info("INFO: Status code of API call is:" + statusCode);
		Assert.assertEquals(AssertionConstants.statusCodeOk, statusCode);
	}

	@When("User makes {string} request to {string} pet id {string} in the store")
	public void user_makes_request_to_pet_id_in_the_store(String string, String string2, String string3) {
		String requestType = string;
		switch (requestType) {
		case "POST":
			if (string2.equalsIgnoreCase("create") || string2.equalsIgnoreCase("update")) {
				response = rs.sendRequest(peturl, petPayload, Method.POST, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" + response);
				logger.info("INFO: Status code of API call is:" + statusCode);
			}
			break;
		case "PUT":
			if (string2.equalsIgnoreCase("update")) {
				response = rs.sendRequest(peturl, petPayload, Method.PUT, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" + response);
				logger.info("INFO: Status code of API call is:" + statusCode);
			}
			break;
		case "GET":
				response = rs.sendRequest(peturl + "/" + string3, "", Method.GET, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" + response);
				logger.info("INFO: Status code of API call is:" + statusCode);
			break;
		case "DELETE":
				response = rs.sendRequest(peturl + "/" + string3, "", Method.DELETE, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" + response);
				logger.info("INFO: Status code of API call is:" + statusCode);
			break;

		}
	}

	@Given("Pet id {string} does not exists in the store")
	public void pet_id_does_not_exists_in_the_store(String string) {
		response = rs.sendRequest(peturl + "/" + string, "", Method.GET, null, null, null);
		statusCode = rs.getStatusCode();
		logger.info("INFO: Response of API call is:" + response);
		logger.info("INFO: Status code of API call is:" + statusCode);
		Assert.assertEquals(AssertionConstants.statusCodeNotFound, statusCode);
	}

	@Then("{string} status is set to {string}")
	public void status_is_set_to(String string, String string2) {
		String getNameofPet = JsonUtils.getJsonValueByKey(response, "name");
		logger.info("INFO: Pet name :" +getNameofPet);
		Assert.assertEquals(getNameofPet, string);
		String getStatusofPet = JsonUtils.getJsonValueByKey(response, "status");
		logger.info("INFO: Pet status :" +getStatusofPet);
		Assert.assertEquals(getStatusofPet,string2);
	}

	@When("User makes GET request to view pet details by their status {string}")
	public void user_makes_get_request_to_view_pet_details_by_their_status(String string) {
		Map<String, String> queryparam = new HashMap<String, String>();
		queryparam.put("status", string);
		response = rs.sendRequest(getPetByStatusUrl, "", Method.GET, queryparam, null, null);
		logger.info("INFO: Response of API call is:" + response);

	}

	@Then("Response retrieved contains all pet having status {string}")
	public void response_retrieved_contains_all_pet_having_status(String string) {
		List<String> getPetStatus = JsonUtils.getListofJsonValueByKey(response, "status");
		// Check all retrieved value are same in list and then pick any one value and
		// assert with expected status
		if (!getPetStatus.isEmpty()) {
			boolean allEqual = new HashSet<String>(getPetStatus).size() <= 1;
			Assert.assertTrue(allEqual);
			Assert.assertEquals(getPetStatus.get(0),string);
		} else {
			logger.info("Store has no pets");
		}
	}

	@Then("Status code returned is {string}")
	public void status_code_returned_is(String string) {
		statusCode = rs.getStatusCode();
		logger.info("INFO: Response of API call is:" + response);
		logger.info("INFO: Status code of API call is:" + statusCode);
		Assert.assertEquals(statusCode, Integer.parseInt(string));
		
	}

	@Then("Response body displays message {string}")
	public void response_body_displays_message(String string) {
		Assert.assertTrue(response.contains(string));

	}

	@When("User makes DELETE request to remove pet with pet id {string} from store")
	public void user_makes_delete_request_to_remove_pet_with_pet_id_from_store(String string) {
		response = rs.sendRequest(peturl + "/" + string, "", Method.DELETE, null, null, null);
		statusCode = rs.getStatusCode();
		logger.info("INFO: Response of API call is:" + response);
		logger.info("INFO: Status code of API call is:" + statusCode);
	}

	// do this and write rest scenarios
	@Given("An image {string} of a pet to upload")
	public void an_image_of_a_pet_to_upload(String string) {
		petImage = FileUtils.readFile(string);
		logger.info("INFO: Image file attached successfully");

	}

	@When("User makes {string} request to upload image of pet id {string} in the store")
	public void user_makes_request_to_upload_image_of_pet_id_in_the_store(String string, String string2) {
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("accept", "application/json");
		requestHeaders.put("Content-Type", "application/octet-stream");
		List<MultiPartSpecification> multiPartFile = new ArrayList<MultiPartSpecification>();
		multiPartFile.add(RestAssuredUtil.createMultiPartSpecification(petImage, "file", "application/octet-stream"));
		response = rs.sendRequest(peturl + "/" + string2 + uploadPetImageUrl, "", Method.POST, null, requestHeaders,
				multiPartFile);
		logger.info("INFO: Response of API call is:" + response);
	}

	// Store tests
	@When("User makes GET request to view inventories details by their statuses and quantities")
	public void user_makes_get_request_to_view_inventories_details_by_their_statuses_and_quantities() {
		response = rs.sendRequest(getinventorystatusurl, "", Method.GET, null, null, null);
		statusCode = rs.getStatusCode();
		logger.info("INFO: Response of API call is:" + response);
		logger.info("INFO: Status code of API call is:" + statusCode);

	}

	@Then("Response retrieved contains all status codes {string}, {string} and {string} with quantities")
	public void response_retrieved_contains_all_status_codes_and_with_quantities(String string, String string2,
			String string3) {
		Assert.assertTrue(response.contains(string) && response.contains(string2) && response.contains(string3));
	}

	@Given("Order payload with orderid {string}, pet id {string}, quantity {string}, status {string}, and complete {string}")
	public void order_payload_with_orderid_pet_id_quantity_status_and_complete(String string, String string2,
			String string3, String string4, String string5) {
		orderId = string;
		petId = string2;

		List<String> oldValue = List.of("orderidvalue", "petidvalue", "quantityvalue", "statusvalue", "completevalue");

		// testdata at runtime
		List<String> newValue = List.of(string, string2, string3, string4, string5);
		try {
			orderPayload = ConversionUtility.updateJsonFile(orderPetPayload, oldValue, newValue);
			logger.info("Json file updated");
		} catch (IOException e) {
			logger.error("ERROR: Exception occurred while updating json file:- {}", e.getMessage());
		}
		System.out.println(orderPayload);
	}

	@When("User makes {string} request to {string} order {string} from the store")
	public void user_makes_request_to_order_from_the_store(String string, String string2, String string3) {
		String requestType = string;
		switch (requestType) {
		case "POST":
			if (string2.equalsIgnoreCase("order") || string2.equalsIgnoreCase("update")
					|| string2.equalsIgnoreCase("placed")) {
				response = rs.sendRequest(orderpeturl, orderPayload, Method.POST, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" +response);
				logger.info("INFO: Status code of API call is:" +statusCode);
			}
			break;
		case "PUT":
			if (string2.equalsIgnoreCase("update")) {
				response = rs.sendRequest(orderpeturl, orderPayload, Method.PUT, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" +response);
				logger.info("INFO: Status code of API call is:" +statusCode);
			}
			break;
		case "GET":
			response = rs.sendRequest(orderpeturl + "/" + string3, "", Method.GET, null, null, null);
			statusCode = rs.getStatusCode();
			logger.info("INFO: Response of API call is:" +response);
			logger.info("INFO: Status code of API call is:" +statusCode);

			break;
		case "DELETE":
			response = rs.sendRequest(orderpeturl + "/" + string3, "", Method.DELETE, null, null, null);
			statusCode = rs.getStatusCode();
			logger.info("INFO: Response of API call is:" +response);
			logger.info("INFO: Status code of API call is:" +statusCode);
			break;

		}
	}

	@Given("Order with orderid {string}, pet id {string}, quantity {string}, status {string}, and complete {string} is {string}")
	public void order_with_orderid_pet_id_quantity_status_and_complete_is(String string, String string2, String string3,
			String string4, String string5, String string6) throws IOException {
		if (string6.equalsIgnoreCase("placed")) {
			order_payload_with_orderid_pet_id_quantity_status_and_complete(string, string2, string3, string4, string5);
			user_makes_request_to_order_from_the_store("POST", string6, null);

		}
		if (string6.equalsIgnoreCase("deleted")) {

			orderId = string;
			petId = string2;
			List<String> oldValue = List.of("orderidvalue", "petidvalue", "quantityvalue", "statusvalue",
					"completevalue"); // Updating the json with desired
			List<String> newValue = List.of(string, string2, string3, string4, string5);
			try {
				orderPayload = ConversionUtility.updateJsonFile(orderPetPayload, oldValue, newValue);
				logger.info("Json file updated");
			} catch (IOException e) {
				logger.error("ERROR: Exception occurred while updating json file:- {}", e.getMessage());
			}
			System.out.println(orderPayload);
			response = rs.sendRequest(orderpeturl, orderPayload, Method.POST, null, null, null);
			statusCode = rs.getStatusCode();
			user_makes_request_to_order_from_the_store("DELETE", null, orderId);

		}
	}

	@Then("User verify response body with orderid {string}, pet id {string}, quantity {string}, status {string}, and complete {string}")
	public void user_verify_response_body_with_orderid_pet_id_quantity_status_and_complete(String string,
			String string2, String string3, String string4, String string5) {
		String getOrderId = JsonUtils.getJsonValueByKey(response, "id");
		Assert.assertEquals(string, getOrderId);
		String getPetId = JsonUtils.getJsonValueByKey(response, "petId");
		Assert.assertEquals(string2, getPetId);
		String getQuantity = JsonUtils.getJsonValueByKey(response, "quantity");
		Assert.assertEquals(string3, getQuantity);
		String getStatus = JsonUtils.getJsonValueByKey(response, "status");
		Assert.assertEquals(string4, getStatus);
		String getComplete = JsonUtils.getJsonValueByKey(response, "complete");
		Assert.assertEquals(string5, getComplete);
		logger.info("INFO: Response body verified");
	}

	// User
	@Given("User payload with user {string}")
	public void user_payload_with_user(String string) {
		String userDetails[] = string.split(",");
		userId = userDetails[0];
		userName = userDetails[1];
		userFn = userDetails[2];
		userLn = userDetails[3];
		userEmail = userDetails[4];
		userPassword = userDetails[5];
		userPhone = userDetails[6];
		userStatus = userDetails[7];
		List<String> oldValue = List.of("useridvalue", "usernamevalue", "fnvalue", "lnvalue", "emailvalue", "pwdvalue",
				"phonevalue", "statusvalue");
		List<String> newValue = List.of(userId, userName, userFn, userLn, userEmail, userPassword, userPhone,
				userStatus);
		try {
			userPayload = ConversionUtility.updateJsonFile(createUserPayload, oldValue, newValue);
			logger.info("Json file updated");

		} catch (IOException e) {
			logger.error("ERROR: Exception occurred while updating json file:- {}", e.getMessage());
		}
		System.out.println(userPayload);

	}

	@When("User makes {string} request to {string} user {string}")
	public void user_makes_request_to_user(String string, String string2, String string3) {
		String requestType = string;
		switch (requestType) {
		case "POST":
			if (string2.equalsIgnoreCase("create user") || string2.equalsIgnoreCase("created")) {
				response = rs.sendRequest(userurl, userPayload, Method.POST, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" +response);
				logger.info("INFO: Status code of API call is:" +statusCode);
			}
			if (string2.equalsIgnoreCase("create user list")) {
				response = rs.sendRequest(createuserwithlist, userPayload, Method.POST, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" +response);
				logger.info("INFO: Status code of API call is:" +statusCode);
			}
			break;
		case "PUT":
				response = rs.sendRequest(userurl + "/" + string3, userPayload, Method.PUT, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" +response);
				logger.info("INFO: Status code of API call is:" +statusCode);
			break;
		case "GET":
				response = rs.sendRequest(userurl + "/" + string3, "", Method.GET, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" +response);
				logger.info("INFO: Status code of API call is:" +statusCode);
			break;
		case "DELETE":
				response = rs.sendRequest(userurl + "/" + string3, "", Method.DELETE, null, null, null);
				statusCode = rs.getStatusCode();
				logger.info("INFO: Response of API call is:" +response);
				logger.info("INFO: Status code of API call is:" +statusCode);
			break;

		}
	}

	@When("User makes {string} request to {string} for {string} and {string}")
	public void user_makes_request_to_for_and(String string, String string2, String string3, String string4) {
		if (string2.equalsIgnoreCase("login")) {
			Map<String, String> queryParams = new HashMap<>();
			queryParams.put("username", string3);
			queryParams.put("password", string4);
			response = rs.sendRequest(userloginurl, "", Method.GET, queryParams, null, null);
			statusCode = rs.getStatusCode();
			logger.info("INFO: Response of API call is:" +response);
			logger.info("INFO: Status code of API call is:" +statusCode);
		}
		if (string2.equalsIgnoreCase("logout")) {
			response = rs.sendRequest(userlogouturl, "", Method.GET, null, null, null);
			statusCode = rs.getStatusCode();
			logger.info("INFO: Response of API call is:" +response);
			logger.info("INFO: Status code of API call is:" +statusCode);
		}

	}

	@Given("User payload with user {string} and {string} details")
	public void user_payload_with_user_and_details(String string, String string2) {
		String userDetails1[] = string.split(",");
		userId = userDetails1[0];
		userName = userDetails1[1];
		userFn = userDetails1[2];
		userLn = userDetails1[3];
		userEmail = userDetails1[4];
		userPassword = userDetails1[5];
		userPhone = userDetails1[6];
		userStatus = userDetails1[7];

		String userDetails2[] = string2.split(",");
		userId1 = userDetails2[0];
		userName1 = userDetails1[1];
		userFn1 = userDetails2[2];
		userLn1 = userDetails2[3];
		userEmail1 = userDetails2[4];
		userPassword1 = userDetails2[5];
		userPhone1 = userDetails2[6];
		userStatus1 = userDetails2[7];
		List<String> oldValue = List.of("useridvalue1", "usernamevalue1", "fnvalue1", "lnvalue1", "emailvalue1",
				"pwdvalue1", "phonevalue1", "statusvalue1", "useridvalue2", "fnvalue2", "lnvalue2", "emailvalue2",
				"pwdvalue2", "phonevalue2", "statusvalue2");
		List<String> newValue = List.of(userId, userName, userFn, userLn, userEmail, userPassword, userPhone,
				userStatus, userId1, userName1, userFn1, userLn1, userEmail1, userPassword1, userPhone1, userStatus1);
		try {
			userPayload = ConversionUtility.updateJsonFile(createUserWithListPayload, oldValue, newValue);
			logger.info("Json file updated");
		} catch (IOException e) {
			logger.error("ERROR: Exception occurred while updating json file:- {}", e.getMessage());

		}

	}

	@Given("User {string} is {string}")
	public void user_is(String string, String string2) throws IOException {
		if (string2.equalsIgnoreCase("created")) {
			user_payload_with_user(string);
			user_makes_request_to_user("POST", string2, null);
			logger.info("INFO: User is created");
		}
		if (string2.equalsIgnoreCase("not created")) {
			String userDetails[] = string.split(",");
			userFn = userDetails[2];
			user_makes_request_to_user("GET", null, userFn);
			response_body_displays_message(AssertionConstants.userNotFound_error);
			logger.info("INFO: User not found");
		}
	}

	@Then("Response body is validated with details {string}")
	public void response_body_is_validated_with_details(String string) {
		String userDetails[] = string.split(",");
		userId = userDetails[0];
		userName = userDetails[1];
		userFn = userDetails[2];
		userLn = userDetails[3];
		userEmail = userDetails[4];
		userPassword = userDetails[5];
		userPhone = userDetails[6];
		userStatus = userDetails[7];
		logger.info("INFO: User details retrieved");
		Assert.assertTrue(response.contains(userId) && response.contains(userFn) && response.contains(userLn)
				&& response.contains(userEmail) && response.contains(userPassword) && response.contains(userPhone)
				&& response.contains(userStatus));
		logger.info("INFO: Response body validated");
	}
	
	
	//endtoend
	@When("User {string} {string}")
	public void user(String string, String string2) throws IOException {
	    if(string2.equalsIgnoreCase("login"))
	    {
	    	user_is(string, "created");
	    	user_makes_request_to_for_and(null,string2,userName,userPassword);
	    	logger.info("INFO: User logged in");
	    }
	    if(string2.equalsIgnoreCase("logout"))
	    {
	    	user_makes_request_to_for_and(null,string2,userName,userPassword);
	    }
	}


}
