package jerry.shen.plugin;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class LocationService extends CordovaPlugin {
    public static final String ACTION_START_SERVICE = "startService";
    public static final String ACTION_STOP_SERVICE = "stopService";
    private String PACKAGENAME = "";
    private String CLASSNAME = "";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_START_SERVICE.equals(action)) { 
                PACKAGENAME = this.cordova.getActivity().getPackageName();
                CLASSNAME = this.cordova.getActivity().getClass().getSimpleName();
                JSONObject arg_object = args.getJSONObject(0);
                Intent intent = new Intent(this.cordova.getActivity(), LocationServiceMain.class).putExtra("destination",arg_object.getString("destination")).putExtra("latitude", arg_object.getString("latitude")).putExtra("longitude", arg_object.getString("longitude")).putExtra("package",PACKAGENAME).putExtra("class",CLASSNAME);
                this.cordova.getActivity().startService(intent);
                callbackContext.success();
                return true;
            } else if (ACTION_STOP_SERVICE.equals(action)) { 
                JSONObject arg_object = args.getJSONObject(0);
                Intent intent = new Intent(this.cordova.getActivity(), LocationServiceMain.class);
                this.cordova.getActivity().stopService(intent);
                callbackContext.success();
                return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}