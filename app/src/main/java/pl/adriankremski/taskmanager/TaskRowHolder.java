package pl.adriankremski.taskmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskRowHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.task_name)
    TextView taskNameLabel;

    @Bind(R.id.checkbox)
    CheckBox checkBox;

    private Task task;

    private TaskCompletedListener taskCompletedListener;

    public interface TaskCompletedListener {
        void taskCompleted(Task task, boolean isCompleted);
    }

    public TaskRowHolder(View itemView, final TaskCompletedListener taskCompletedListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.taskCompletedListener = taskCompletedListener;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                taskCompletedListener.taskCompleted(task, isChecked);
            }
        });
    }

    public void setTask(Task task) {
        this.task = task;
        taskNameLabel.setText(task.getText());
        checkBox.setChecked(task.isCompleted());
    }
}
