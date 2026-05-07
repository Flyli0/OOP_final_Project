package config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ValidateDb {
	public static List validate(List c) {
		if(c == null) {
			return new ArrayList<>();
		}
		else {
			return c;
		}
	}
}
