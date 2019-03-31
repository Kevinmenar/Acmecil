package acmecil.project.projima.com.acmecil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ChangePriceActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_price);

        //TODO: getBundle

        //TODO: GetTextView titulo, Precio aniguo

        //
    }


    public void OnButtonChangePrice(View view){
        //if new price is empty display Toast
        //Toast.makeText(this,"Debe introducir texto",Toast.LENGTH_SHORT);
        switch (Controller.getInstance().getSessionRole()){
            case COMMON_USER:{
                reportPrice(0,0,0);
                break;
            }
            case ADMINISTRATOR:{
                changePrice(0,0,0);
                break;
            }
        }
    }


    private void reportPrice(int newPrice, int pharmacyCode, int medicineCode){

    }

    private void changePrice(int newPrice, int pharmacyCode, int medicineCode){

    }
}
