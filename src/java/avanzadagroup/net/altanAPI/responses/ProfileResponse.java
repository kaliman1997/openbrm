/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI.responses;

/**
 *
 * @author Arturo Ruiz
 */
public class ProfileResponse extends GeneralResponse {
    private String idSubscriber;
    private String imsi;
    private String icc;
    private String subStatus;
    private String errorCode;
    private String description;
    private String detail;
    private String primaryOffering;
    
    
	public String getPrimaryOffering() {
		return primaryOffering;
	}
	public void setPrimaryOffering(String primaryOffering) {
		this.primaryOffering = primaryOffering;
	}
	public String getIdSubscriber() {
		return idSubscriber;
	}
	public void setIdSubscriber(String idSubscriber) {
		this.idSubscriber = idSubscriber;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getIcc() {
		return icc;
	}
	public void setIcc(String icc) {
		this.icc = icc;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
   
}
