package pl.adriankremski.taskmanager;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmTaskManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
//        mockTasks();
    }
//
//    private void mockTasks() {
//        final List<Task> testTasks = new LinkedList<Task>();
//        testTasks.add(new Task("Have a coffee"));
//        testTasks.add(new Task("Check Android Weekly"));
//        testTasks.add(new Task("Check Your mail"));
//        testTasks.add(new Task("Standup"));
//        testTasks.add(new Task("Pretend that You are working on something"));
//        testTasks.add(new Task("Dinner"));
//        testTasks.add(new Task("Go home"));
//
//        Realm realm = Realm.getDefaultInstance();
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                for (Task task : testTasks) {
//                    Task realmTask = realm.createObject(Task.class); // this line creates a new object saved in Realm
//                    realmTask.setCompleted(false);
//                    realmTask.setText(task.getText());
//                }
//            }
//        });
//        realm.close(); // Always remember to close the realm instance
//    }
}
