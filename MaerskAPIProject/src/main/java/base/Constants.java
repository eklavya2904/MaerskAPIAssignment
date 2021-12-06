package base;

import java.util.Properties;

import Utility.fileUtils.PropertiesUtility;
import helpers.CommonMethods;

public interface Constants {
	
    public static Properties properties= PropertiesUtility.getApplicationProperties(SetUp.configFileName);
    public static Properties runTimeprop= PropertiesUtility.getApplicationProperties(CommonMethods.runTimeFileName);


    static final String log_Type_Pass = "Pass";
    static final String log_Type_Fail = "Fail";
    static final String log_Type_Log = "Log";

    static final  String status_Success = "SUCCESS";
    static final String status_Pending = "PENDING";
    static final String failStatus="FAILED";

    static final String status_Failure="FAILED";
    static final String status_Key="Status";

    static final String responseCode_Success ="201";
    static final String responseCode_SuccessLogin ="200";
    static final String responseCode_BadRequest ="400";
    static final String responseCode_Conflict ="409";
    static final String responseCode_Redirect ="302";
    static final String responseCode_Forbidden ="403";
    static final String responseCode_NotAllowed ="405";
    static final String postResponse="201";
  
	static final String secretKey = "test";
	
	//Redirects value
	static final boolean trueval=true;
	static final boolean falseval=false;

	




}
