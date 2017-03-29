package pl.adriankremski.taskmanager;

import android.app.Application;

import com.facebook.FacebookSdk;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmTaskManagerApplication extends Application {

    public static final String OBJECT_SERVER_IP = "192.168.0.213";
    public static final String AUTH_URL = "http://" + OBJECT_SERVER_IP + ":9080/auth";
    public static final String REALM_URL = "realm://" + OBJECT_SERVER_IP + ":9080/~/realmtasks";
    public static final String DEFAULT_LIST_ID = "80EB1620-165B-4600-A1B1-D97032FDD9A0";
    public static String DEFAULT_LIST_NAME = "My Tasks";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        FacebookSdk.sdkInitialize(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

}
