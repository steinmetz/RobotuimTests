package teste.com.shape;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void teste1() throws Exception {
        solo.clickOnButton("Login");
        boolean actual = solo.waitForText("Insira o login");
        assertEquals("Login...", true, actual);
    }

    public void teste2() throws Exception {
        solo.enterText(0, "login");
        solo.enterText(1, "senha");
        solo.clickOnButton("Login");
        boolean actual = solo.waitForText("Dados inválidos");
        assertEquals("Login...", true, actual);
    }

    public void teste3() throws Exception {


        solo.enterText((EditText) solo.getView(R.id.email), "charles");
        solo.enterText((EditText) solo.getView(R.id.password), "123");
        solo.clickOnButton("Login");
        boolean actual = solo.waitForActivity(MapsActivity.class);
        assertEquals("Login...", true, actual);
    }


    public void testLogin() throws Exception {

    }
}