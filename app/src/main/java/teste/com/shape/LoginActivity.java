package teste.com.shape;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
    }


    public void login(View view) {
        String email = mEmailView.getText().toString();
        String pass = mPasswordView.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Insira o login", Toast.LENGTH_SHORT).show();
        }else if(email.equals("charles") && pass.equals("123")){
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this,"Dados inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}



