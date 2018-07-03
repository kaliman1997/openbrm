package in.saralam.sbs.server.deferredAction.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public interface IDeferredAction extends  Serializable {

  public void execute();


}
