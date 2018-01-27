package fg.mktvgo.com.paymentantadrestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button myApiTrigger;
    private TextView myNewText;
    RequestQueue myRequestQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApiTrigger    = (Button) findViewById(R.id.apiTrigger);
        myNewText= (TextView) findViewById(R.id.txtNewMsg);
        myRequestQ = Volley.newRequestQueue(this);


        myApiTrigger.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Api call
                        getApiValues();
                    }
                }
        );

    }

    private void getApiValues(){
        final String endPoint = "http://104.198.46.93:8080/paymentAntad/rest/blue/usrPaymentLog?user=mborruel";
        JsonArrayRequest myJsonReq = new JsonArrayRequest(Request.Method.GET, endPoint,
        new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            String shops = "";
                            for(int i = 0; i < response.length(); i++){
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String newMessage = jsonObj.get("shopCenter").toString();
                                    shops += i + 1 +" " +newMessage + ", ";
                                } catch (JSONException e) {
                                    shops += "Error en : " + e.getMessage();
                                }finally {
                                    myNewText.setText("Endpoint shop values : " + shops);

                                }
                            }

                        } else {
                            myNewText.setText("No encontramos la direccion buscada");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myNewText.setText("Error en : " + error.getMessage());
                    }
                }
        );
        myRequestQ.add(myJsonReq);


    }


}
