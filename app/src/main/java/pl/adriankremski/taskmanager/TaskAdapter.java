package pl.adriankremski.taskmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by adriankremski on 06/01/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskRowHolder> {

    private List<Task> tasks = new LinkedList<Task>();
    private TaskRowHolder.TaskCompletedListener taskCompletedListener;

    public TaskAdapter(List<Task> tasks, TaskRowHolder.TaskCompletedListener taskCompletedListener) {
        this.tasks = tasks;
        this.taskCompletedListener = taskCompletedListener;
    }

    public void addTask(Task task) {
        tasks.add(0, task);
        notifyItemInserted(0);
    }

    @Override
    public TaskRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task_row, parent, false);
        return new TaskRowHolder(itemView, taskCompletedListener);
    }

    @Override
    public void onBindViewHolder(TaskRowHolder holder, int position) {
        holder.setTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public Task getTaskAtPosition(int position) {
        return tasks.get(position);
    }
}
