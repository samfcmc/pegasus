package fenixedu;

import pt.ist.fenixedu.sdk.FenixEduClient;
import pt.ist.fenixedu.sdk.FenixEduConfig;

public class FenixEduClientQAFactory {

	private static FenixEduClient instance;
	
	public static FenixEduClient getSingleton() {
		if(instance == null) {
			instance = createSingleton();
		}
		return instance;
	}
	
	private static FenixEduClient createSingleton() {
		String consumerKey = System.getenv("QA_APP_ID");
		String consumerSecret = System.getenv("QA_APP_SECRET");
		String baseUrl = System.getenv("QA_BASE_URL");
		String callbackUrl = System.getenv("QA_CALLBACK_URL");
		FenixEduConfig config = new FenixEduConfig(consumerKey, consumerSecret, baseUrl, callbackUrl);
		
		return new FenixEduClient(config);
	}
}
