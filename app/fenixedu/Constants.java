package fenixedu;

import pt.ist.fenixedu.sdk.FenixEduClient;
import pt.ist.fenixedu.sdk.FenixEduClientFactory;

public class Constants {
	
	private static FenixEduClient client = FenixEduClientQAFactory.getSingleton();
	
	public static final String loginURL = client.getAuthenticationUrl();

	

}
