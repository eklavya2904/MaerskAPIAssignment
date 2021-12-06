package ComponentFile;

import static base.APIEndPoints.loginUrl;

import java.util.HashMap;

import base.SetUp;
import helpers.CommonMethods;
import helpers.RestUtil;
import io.restassured.response.Response;

public class TokenComponent extends SetUp {


	CommonMethods commMethod;

	public Response gettokenId() {
		{
			try {
				Response response = null;
				String url=loginUrl;
				response = RestUtil.getByUrl(url);
				System.out.println(response.prettyPrint());

				return response;
			} catch (Exception e) {
				throw new RuntimeException("error while creating   token response status.", e);
			}
		}
	}

	public Response getJauthToken(HashMap myData,String cookie) {
		{
			try {
				Response response = null;

				String url=loginUrl;
				System.out.println(loginUrl);
				response = RestUtil.getByUrl(url,cookie);
				System.out.println(response.prettyPrint());
				return response;
			} catch (Exception e) {
				throw new RuntimeException("error while creating   Mandate Check Status response.", e);
			}
		}
	}
}



