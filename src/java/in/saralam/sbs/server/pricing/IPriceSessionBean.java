package in.saralam.sbs.server.pricing;

import java.math.BigDecimal;
import java.util.Date;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;



public interface IPriceSessionBean {
    
   /* public PricePackageDTO getPricePackage(Integer orderId) throws SessionInternalError;

    public PricePackageDTO getPricePackageEx(Integer orderId, Integer languageId) 
            throws SessionInternalError;
  
    public void delete(Integer id, Integer executorId) 
            throws SessionInternalError;
 
    public PackageProductDTO[] getProducts(Integer entityId, Integer languageId) 
            throws SessionInternalError;

    public PricePackageDTO addProduct(Integer ProductID, BigDecimal quantity, PricePackageDTO pricePackage,
            Integer languageId, Integer userId, Integer entityId) 
            throws SessionInternalError, ItemDecimalsException;
    
    public PricePackageDTO addProduct(Integer ProductID, Integer quantity, PricePackageDTO pricePackage,
            Integer languageId, Integer userId, Integer entityId) 
            throws SessionInternalError, ItemDecimalsException; */

    public Integer createUpdate(Integer entityId, PricePackageDTO pricePackage ) throws SessionInternalError;
 // public Integer createUpdate(Integer entityId, PackagePriceDTO pricePackage ) throws SessionInternalError
 
}
