package cw.tweetmodule.logic;

import cw.boardingschoolmanagement.manager.PropertiesManager;

public class BoTweet {

	
	public static boolean isActive() {
		String strActive = PropertiesManager.getProperty("tweetmodule.active", "false");
		if(Boolean.parseBoolean(strActive) == true) {
			return true;
		}
		return false;
	}
}
