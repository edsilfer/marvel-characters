package br.com.hole19.marvel.comm.model;

/**
 * Created by edgar on 17/02/2016.
 */
public interface TaskExecutor {
    public void executeOnSuccessTask(Object payload);

    public void executeOnErrorTask(Object payload);
}
