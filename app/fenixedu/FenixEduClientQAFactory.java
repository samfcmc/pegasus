package fenixedu;

import java.util.Map;

import play.libs.Yaml;
import pt.ist.fenixedu.sdk.FenixEduClient;
import pt.ist.fenixedu.sdk.FenixEduConfig;

public class FenixEduClientQAFactory {
	
	private static final String yamlFile = "fenixedu.yml";

	private static FenixEduClient instance;
	
	public static FenixEduClient getSingleton() {
		if(instance == null) {
			instance = createSingleton();
		}
		return instance;
	}
	
	private static FenixEduClient createSingleton() {
		String consumerKey = System.getenv("FENIXEDU_APP_ID");
		if(consumerKey == null) {
			//There's no environment variables
			// Lets read this values from yaml file
			return createSingletonFromYaml();
		}
		String consumerSecret = System.getenv("FENIXEDU_APP_SECRET");
		String baseUrl = System.getenv("FENIXEDU_BASE_URL");
		String callbackUrl = System.getenv("FENIXEDU_CALLBACK_URL");
		FenixEduConfig config = new FenixEduConfig(consumerKey, consumerSecret, baseUrl, callbackUrl);
		
		return new FenixEduClient(config);
	}
	
	private static FenixEduClient createSingletonFromYaml() {
		@SuppressWarnings("unchecked")
		Map<String,String> all = (Map<String, String>) Yaml.load(yamlFile);

		
		String consumerKey = all.get("appId");
		String consumerSecret = all.get("appSecret");
		String baseUrl = all.get("baseURL");
		String callbackUrl = all.get("callbackURL");
		FenixEduConfig config = new FenixEduConfig(consumerKey, consumerSecret, baseUrl, callbackUrl);
		
		return new FenixEduClient(config);
	}
}
