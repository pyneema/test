package sears.pages;

import com.saf.global.Global;

public class PageGmail extends Global {

	public static final String SignIn = "link=Sign in";
	public static final String username = "rohanpandahre22.5@gmail.com";
	public static final String password = "Rohan_Pandhare";
	public static final String SignInButton = "name=signIn";

	public void clickSignInLink() {
		clickAndWait(SignIn, 2);
		typeCredentialAndClick();
		waitForText("The email or password you entered is incorrect.");
	}

	public void typeCredentialAndClick() {
		type("Email", username);
		type("Passwd", password);
		clickAndWait(SignInButton, 2);
	}

}
