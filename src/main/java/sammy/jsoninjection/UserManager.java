package sammy.jsoninjection;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * Hello world!
 *
 */
public class UserManager 
{
    public static void main( String[] args )
    {
    	UserManager userManager = new UserManager();
    	try {
			userManager.addUser("John\",\"role\":\"admin", "pass123", "default");
			
			Map<String, String> userInfo = userManager.getUserInfo();
			System.out.println(userInfo.get("role"));
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
    }
    
    
    public void addUser(String username, String password, String role) throws IOException {
    	JsonFactory jfactory = new JsonFactory();

    	JsonGenerator jGenerator = jfactory.createGenerator(new File("C:/tmp/user_info.json"), JsonEncoding.UTF8);

    	jGenerator.writeStartObject();

    	jGenerator.writeFieldName("username");
    	jGenerator.writeString("\"" + username + "\"");

    	jGenerator.writeFieldName("password");
    	jGenerator.writeRawValue("\"" + password + "\"");

    	jGenerator.writeFieldName("role");
    	jGenerator.writeRawValue("\"default\"");

    	jGenerator.writeEndObject();

    	jGenerator.close();
    }
    
    public Map<String, String> getUserInfo() throws JsonParseException, IOException {
    	
    	JsonFactory jfactory = new JsonFactory();    	
    	JsonParser jParser = jfactory.createParser(new File("C:/tmp/user_info.json"));
    	
    	Map<String, String> userInfo = new HashMap<String, String>();

    	while (jParser.nextToken() != JsonToken.END_OBJECT) {

    	  String fieldname = jParser.getCurrentName();

    	  if ("username".equals(fieldname)) {
    	    jParser.nextToken();
    	    userInfo.put(fieldname, jParser.getText());
    	  }

    	  if ("password".equals(fieldname)) {
    	    jParser.nextToken();
    	    userInfo.put(fieldname, jParser.getText());
    	  }

    	  if ("role".equals(fieldname)) {
    	    jParser.nextToken();
    	    userInfo.put(fieldname, jParser.getText());
    	  }

    	  if (userInfo.size() == 3)
    	    break;
    	}

    	jParser.close();
    	
    	return userInfo;
    }
}
