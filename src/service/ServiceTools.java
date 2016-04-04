package service;


import org.json.JSONException;
import org.json.JSONObject;



/*
 * Fonctions écrites en TD/TME2
 * Elles renvoient un JSONObject et sont appelées par UserServices
 */
public class ServiceTools {
	public static JSONObject serviceAccepted(){
		JSONObject res= new JSONObject();
		try {
			res.put("message","OK");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		}
	
	public static JSONObject serviceRefused(String message, int erreur){
		JSONObject res= new JSONObject();
		try {
			res.put("erreur", erreur);
			res.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	

}
