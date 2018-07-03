package avanzadagroup.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;



import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.item.AssetBL;
import com.sapienter.jbilling.server.item.AssetWS;
import com.sapienter.jbilling.server.item.db.AssetDAS;
import com.sapienter.jbilling.server.item.db.AssetDTO;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;

public class MSISDNActivation implements Runnable{
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(MSISDNActivation.class));	
	
	private Integer userId ;
	
	public MSISDNActivation(Integer userId){
		this.userId = userId;
	}

	@Override
	public void run() {
		WebServicesSessionSpringBean wsssb = new WebServicesSessionSpringBean();
		List<AssetWS> assetWSList = wsssb.getAssetsByUserId(userId);
		
		for (Iterator<AssetWS> it = assetWSList.iterator(); it.hasNext(); ){
			AssetWS aws = it.next();
			LOG.debug( aws.getIdentifier() + aws.getStatus());
			
		}
	
	
		
	}
	
	

}
