package Reader;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.jayway.jsonpath.JsonPath;

public class JSONReader {
	public static void main(String[] args) throws IOException {
		File jsonFile;
		
		jsonFile = new File("src/test/resources/TestData/test.json");
		
		// Get all root level keys
        Set<String> rootKeys = JsonPath.read(jsonFile, "$.keys()");
        System.out.println("Root keys: " + rootKeys);

		Object jsonPathValue = JsonPath.read(jsonFile, "$.HOME");
		System.out.println(jsonPathValue.toString());
		
		Set<String> menuSubKeys = JsonPath.read(jsonFile, "$.MENU.keys()");
		System.out.println("MENU sub keys: " + menuSubKeys);
		
		Object jsonPathValue2 = JsonPath.read(jsonFile, "$.MENU.SubMenu1[0].Option12");
		System.out.println(jsonPathValue2.toString());
		Object jsonPathValue3 = JsonPath.read(jsonFile, "$.MENU.SubMenu1[1]");
		System.out.println(jsonPathValue3.toString());
		Object jsonPathValue4 = JsonPath.read(jsonFile, "$.MENU.SubMenu2[0]");
		System.out.println(jsonPathValue4.toString());
		Object jsonPathValue5 = JsonPath.read(jsonFile, "$.MENU.SubMenu1");
		System.out.println(jsonPathValue5.toString());
	}

}
