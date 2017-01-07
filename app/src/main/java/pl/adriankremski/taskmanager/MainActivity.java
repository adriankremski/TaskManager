package pl.adriankremski.taskmanager;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.adriankremski.taskmanager.views.Fab;

public class MainActivity extends AppCompatActivity {

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

    private TaskAdapter taskAdapter = new TaskAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        fab.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        setSupportActionBar(toolbar);
        setupMaterialSheetFab();

        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
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

    @OnClick(R.id.add_task)
    public void addTask() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        }
        Task newTask = new Task(textInputField.getText().toString());
        textInputField.setText("");
        taskAdapter.addTask(newTask);
    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }
}
