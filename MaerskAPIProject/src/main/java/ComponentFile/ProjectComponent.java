package ComponentFile;
import java.util.HashMap;
import java.util.Map;
import base.SetUp;
import helpers.Reporter;
import helpers.RestUtil;
import io.restassured.response.Response;
public class ProjectComponent extends SetUp {
	
	public Response CreateProject_Getweather(HashMap<String, String> myData, String payload, String jauthToken) {
		{
			
			boolean b= true;
			try {
				Response response = null;
				Map<String, String> request = new HashMap<String, String>();
				request.put("Accept", "text/html");
				request.put("Cookie", "_ga=GA1.2.1313233244.1638726136");
				String url = "http://api.openweathermap.org/data/2.5/weather?id=524901&appid=fcce4cba24fec3c672ca40ab9b1a9e79&lang=english";
				response = RestUtil.getByUrlwithoutAuth(url,request,b);
				Reporter.logReport(ComponentFile.ProjectComponent.class, log_Type_Pass, "Response received against Project Check Status :" + response.prettyPrint(), null);
				return response;
			} catch (Exception e) {
				Reporter.logReport(ComponentFile.ProjectComponent.class, log_Type_Fail, "error while creating the Project Check Status response.", e);
				throw new RuntimeException("error while creating   CreateProject_Post Status response.", e);
			}
		}
	}
	

}
