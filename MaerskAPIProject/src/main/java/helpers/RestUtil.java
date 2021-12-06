package helpers;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;
import java.util.Properties;
import Utility.fileUtils.PropertiesUtility;
import base.SetUp;
public class RestUtil {
	public static Properties properties = PropertiesUtility.getApplicationProperties(SetUp.configFileName);
	static String bufferelememt;
	public static Response postByJson(String element, String url, Map header) throws InterruptedException {
		Response response = RestAssured.given().auth().preemptive()
				.basic("userid", "pwd")
				.urlEncodingEnabled(true).headers(header).body(element).log().all()
				.relaxedHTTPSValidation().when().redirects().follow(true).redirects().max(1000).post(url);
		Thread.sleep(8000);
		System.out.println("In postbyjson method" + response.getStatusCode());
		return response;
	}
	
	public static Response postOauthAuthentication(String oauth_consumer_key, String secretKey,String oauthrequesttoken,String oauthrequestsecrettoken,String url, Map header,Map<String, Object> qparamrequest) {

		Response response =  RestAssured.given().auth().oauth(oauth_consumer_key, secretKey,oauthrequesttoken, oauthrequestsecrettoken)
				.relaxedHTTPSValidation().headers(header).queryParams(qparamrequest)
				.post(url);
		return response;
	}
	public static Response putByJson(String element, String url, Map header) {

		Response response = RestAssured.given().auth().preemptive()
				.basic(properties.getProperty("userid"), properties.getProperty("pwd")).urlEncodingEnabled(false)
				.headers(header).body(element).log().all().relaxedHTTPSValidation().when().redirects().follow(true)
				.redirects().max(100).put(url);
		return response;
	}
	public static Response getByUrl(String url, Map header) {
		Response response = (Response) RestAssured.given().auth().preemptive()
				.basic(properties.getProperty("userid"), properties.getProperty("pwd")).urlEncodingEnabled(false)
				.headers(header).log().all().relaxedHTTPSValidation().when().redirects().follow(true).redirects()
				.max(100).get(url);
		return response;
	}
	public static Response getByUrlwithoutAuth(String url, Map header,boolean b1) {

		Response response = (Response) RestAssured.given().urlEncodingEnabled(false).headers(header).log().all()
				.relaxedHTTPSValidation().when().redirects().follow(b1).redirects().max(100).get(url);
		return response;
	}
	public static Response getByUrl(String url) {
		Response response = (Response) RestAssured.given().auth().preemptive()
				.basic(properties.getProperty("userid"), properties.getProperty("pwd"))

				.relaxedHTTPSValidation().redirects().follow(true).redirects().max(100).when().get(url).then().log()
				.all().extract().response();
		return response;
	}
	public static Response getByUrl(String url, String cookie) {
		Response response = (Response) RestAssured.given().cookies("LtpaToken2", cookie).auth().preemptive()
				.basic(properties.getProperty("userid"), properties.getProperty("pwd"))

				.relaxedHTTPSValidation().redirects().follow(true).redirects().max(100).when().get(url).then().log()
				.all().extract().response();
		return response;
	}
	public static Response get(Map header,Map<String, String> params,Map cookies, String path) {

		Response response = RestAssured.given().relaxedHTTPSValidation().headers(header).formParams(params).cookies(cookies).post(path);
		return response;
	}
	public static Response get(Map<String, String> params, String path, String CONTENT_TYPE) {

		Response response = RestAssured.given().urlEncodingEnabled(false).header("Content-Type", CONTENT_TYPE).log()
				.all().relaxedHTTPSValidation().formParams(params).get(path);
		return response;
	}
	public static Response get(Map<String, String> params, Map<String, String> headers, String path) {
		Response response = RestAssured.given().urlEncodingEnabled(false).headers(headers).log().all()
				.relaxedHTTPSValidation().formParams(params).get(path);
		return response;
	}
	public static Response getByUrl(Map<String, String> param,String url,Map headerVal) {
			Response response = (Response) RestAssured.given().queryParams(param).headers(headerVal)                        
				.relaxedHTTPSValidation().redirects().follow(false).redirects().max(100).when().get(url).then().log()
				.all().extract().response();
		return response;
	}
	public static Response post(Map<String, String> data, String path, String CONTENT_TYPE) {
		Response response = RestAssured.given().header("Content-Type", CONTENT_TYPE).body(data).log().all()
				.relaxedHTTPSValidation().when().post(path);
		return response;
	}
	public static Response deleteContent(String url, Map header) {

		Response response = (Response) RestAssured.given().auth().preemptive()
				.basic(properties.getProperty("userid"), properties.getProperty("pwd"))
				.urlEncodingEnabled(false).headers(header)
				.log().all()
				.relaxedHTTPSValidation().when().redirects().follow(true).redirects().max(100).delete(url);
		return response;
	}
}
