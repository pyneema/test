package sears.pages;

import com.saf.data.BaseData;
import com.saf.global.Global;

public class PageGoogle extends Global {

	private static final String inputSearch = "name=q";
	private static final String buttonSearch = "name=btnG";
	private static final String sectionResult = "//h3/a";
	private static final String gmail = "link=Gmail";

	public void search(String keyword) {
		type(inputSearch, keyword);
		clickAndWait(buttonSearch, 2);
	}

	public void verifySearchResults() {
		logWithScreenshot("First Result :" + getText(sectionResult));
		waitForText(BaseData.result, 3);
		retry("id=hfhjv", new Object() {
		}, 1);
	}

	public void clickGamil() {
		clickAndWait(gmail, 2);
		waitForElement("name=Email", 3);
	}

	public void verifyGmailPage() {
		waitForText("Sign in to continue to Gmail", 3);
	}

}
