package lang;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
	private static Locale currentLocale = new Locale("en");
	 private static ResourceBundle bundle =
		        ResourceBundle.getBundle(
		            "messages",
		            currentLocale
		        );

		    public static void setLanguage(String lang) {

		        currentLocale = new Locale(lang);

		        bundle = ResourceBundle.getBundle(
		            "messages",
		            currentLocale
		        );
		    }

		    public static String get(String key) {
		        return bundle.getString(key);
		    }
}
