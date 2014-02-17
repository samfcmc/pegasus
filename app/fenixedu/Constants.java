package fenixedu;

import pt.ist.fenixedu.sdk.FenixEduClient;

public class Constants {
	
	private static FenixEduClient client = FenixEduClientQAFactory.getSingleton();
	
	public static final String loginURL = client.getAuthenticationUrl();

	

}
