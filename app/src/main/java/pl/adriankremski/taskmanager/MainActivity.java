package pl.adriankremski.taskmanager;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.SyncUser;
import pl.adriankremski.taskmanager.views.Fab;
import pl.adriankremski.taskmanager.views.SwipeToDismissTouchListener;

public class MainActivity extends AppCompatActivity implements TaskRowHolder.TaskCompletedListener {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Bind(R.id.fab)
    Fab fab;

    @Bind(R.id.fab_sheet)
    View sheetView;

    @Bind(R.id.overlay)
    View overlayView;

    @Bind(R.id.text)
    TextView textInputField;

    private MaterialSheetFab materialSheetFab;

    private TaskAdapter taskAdapter;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        fab.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        setSupportActionBar(toolbar);
        setupMaterialSheetFab();

        realm = Realm.getDefaultInstance();
        RealmResults realmTasks = realm.where(Task.class).findAll();
        recyclerView.setAdapter(taskAdapter = new TaskAdapter(extractTasksFromRealm(realmTasks), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        SwipeToDismissTouchListener swipeToDismissTouchListener = new SwipeToDismissTouchListener(recyclerView, new SwipeToDismissTouchListener.DismissCallbacks() {
            @Override
            public SwipeToDismissTouchListener.SwipeDirection canDismiss(int position) {
                return SwipeToDismissTouchListener.SwipeDirection.RIGHT;
            }

            @Override
            public void onDismiss(RecyclerView view, List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {
                for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
                    removeTaskFromRealm(taskAdapter.getTaskAtPosition(data.position));
                    taskAdapter.removeTask(data.position);
                }
            }
        });
        recyclerView.addOnItemTouchListener(swipeToDismissTouchListener);
    }

    private void setupMaterialSheetFab() {
        int sheetColor = ContextCompat.getColor(this, R.color.background_light);
        int fabColor = ContextCompat.getColor(this, R.color.colorPrimary);

        materialSheetFab = new MaterialSheetFab(fab, sheetView, overlayView,
                sheetColor, fabColor);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sheetView.getLayoutParams();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        params.width = (int) (size.x * 0.9); // we don't want our sheet to cover whole width of the screen
        sheetView.setLayoutParams(params);
    }

    private List<Task> extractTasksFromRealm(RealmResults<Task> realmResults) {
        List<Task> tasks = new LinkedList<Task>();
        for (Task realmTask : realmResults) {
            tasks.add(realmTask);
        }
        return tasks;
    }

    private void removeTaskFromRealm(final Task taskToRemove) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                taskToRemove.deleteFromRealm();
            }
        });
    }

    @OnClick(R.id.add_task)
    public void addTask() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        }
        addTaskToRealm();
    }

    private void addTaskToRealm() {
        final String newTaskText = textInputField.getText().toString();
        textInputField.setText("");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final Task realmTask = realm.createObject(Task.class);
                realmTask.setText(newTaskText);
                taskAdapter.addTask(realmTask);
            }
        });
    }

    @Override
    public void taskCompleted(final Task task, final boolean isCompleted) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setCompleted(isCompleted);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SyncUser.currentUser().logout();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Always remember to close the realm instance
    }
}
