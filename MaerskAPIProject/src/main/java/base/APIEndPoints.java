package base;

import java.util.Properties;

import Utility.fileUtils.PropertiesUtility;

public class APIEndPoints {

    public static Properties properties= PropertiesUtility.getApplicationProperties(SetUp.configFileName);

    
    //  the value provided is dummy here -Maersk assignment
    public static String loginUrl=properties.getProperty("ENV")+"rm/j_security_check?j_username="+properties.getProperty("userid")+"&j_password="+properties.getProperty("pwd")+"";
   
}
