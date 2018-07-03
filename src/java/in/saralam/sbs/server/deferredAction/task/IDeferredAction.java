package in.saralam.sbs.server.deferredAction.task;


import com.sapienter.jbilling.server.pluggableTask.TaskException;
import java.util.List;


public interface IDeferredAction {

    public void execute() throws TaskException ;
    
}
