package sears.test;

import org.testng.annotations.Test;

import com.saf.data.BaseData;

public class testGoogle extends sears.global.PageFactory {

	@Test
	public final void testGoogleSearch() throws Exception {
		open(url);
		PageGoogle().search(BaseData.keyword);
		PageGoogle().verifySearchResults();
		pause(5);

		checkinglist(getAssertList());
	}

	@Test
	public void testGmail() {
		open(url);
		PageGoogle().clickGamil();
		PageGoogle().verifyGmailPage();
	}
}
