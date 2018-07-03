
/*
 * SARALAM CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise Saralam Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise Saralam Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise Saralam Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */

package in.saralam.sbs.server.pricing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;

@Transactional( propagation = Propagation.REQUIRED )
public class PriceSessionBean implements IPriceSessionBean {
    
    //private static final Logger LOG = Logger.getLogger(PriceSessionBean.class);

   /* public PricePackageDTO getPricePackage(Integer packageId) throws SessionInternalError {
        try {
            PricePackageDAS das = new PricePackageDAS();
            PricePackageDTO pricePackage = das.find(packageId);
            pricePackage.touch();
            return pricePackage;
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public PricePackageDTO getPricePackageEx(Integer packageId, Integer languageId) 
            throws SessionInternalError {
        try {
            PricePackageDAS das = new PricePackageDAS();
            PricePackageDTO order = das.find(packageId);
            pricePackage.addExtraFields(languageId);
            pricePackage.touch();
            das.detach(pricePackage);
            Collections.sort(pricePackage.getLines(), new PackageProductComparator());
            //LOG.debug("returning order " + order);
            return pricePackage;
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public void delete(Integer id, Integer executorId) 
            throws SessionInternalError {
        try {
            // now get the order
            PricePackageBL bl = new PricePackageBL(id);
            bl.delete(executorId);
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }

    }
 
    public PricePackageDTO addProduct(Integer ProductID, BigDecimal quantity, PricePackageDTO pricePackage,
            Integer languageId, Integer userId, Integer entityId) throws SessionInternalError, ItemDecimalsException {
			
		LOG.debug("Adding item " + itemID + " q:" + quantity);

        PricePackageBL bl = new PricePackageBL(pricePackage);
        bl.addProduct(productID, quantity, languageId, userId, entityId);
        return pricePackage;
		
			
	}
        
    public PricePackageDTO addProduct(Integer ProductID, Integer quantity, PricePackageDTO pricePackage,
            Integer languageId, Integer userId, Integer entityId) throws SessionInternalError, ItemDecimalsException {

        return addProduct(productID, new BigDecimal(quantity), pricePackage, languageId, userId, entityId);
    } */

     public Integer createUpdate(Integer entityId,PricePackageDTO pricePackage ) throws SessionInternalError {

	 //   public Integer createUpdate(Integer entityId, PackagePriceDTO pricePackage ) throws SessionInternalError {		
        Integer retValue = null;
        try {
            PricePackageBL bl = new PricePackageBL();
            if (pricePackage.getId() == null) {
                retValue = bl.create(entityId,pricePackage);
            } else {
                bl.set(pricePackage.getId());
                //bl.update(executorId, pricePackage);
            }
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
        
        return retValue;
    }
 
    
    
}
