/*
 * OpenBRM CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise OpenBRM Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise OpenBRM Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise OpenBRM Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */

package com.sapienter.jbilling.server.util;

import com.sapienter.jbilling.server.mediation.db.MediationRecordDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sapienter.jbilling.server.util.MediationUtil;

/**
 * MediationErrorWS
 *
 * @author Brian Cowdery
 * @since 09-10-2013
 */
public class MediationErrorWS implements Serializable {
    
	private  String   uniqid; 
	private  String   indexUUID;
	private  String   startTime;
	private  String   endTime;
	private  Integer  volume;
	private  String   metricType ;
	private  String   errorMessage;
	private  Date  jbillingTimeStamp;
	
 
    public MediationErrorWS() {
    }

    public MediationErrorWS(String uniqid, String indexUUID, String startTime, String endTime, Integer volume, String metricType, String errorMessage, Date jbillingTimeStamp) {
        
		    setUniqid(uniqid);
			setIndexUUID(indexUUID);
			setStartTime(startTime);
			setEndTime(endTime);
			setVolume(volume);
			setMetricType(metricType);
			setErrorMessage(errorMessage);
			setJbillingTimeStamp(jbillingTimeStamp);
    }

    public String getUniqid() {
		return uniqid;
	}
	public void setUniqid(String uniqid) {
		this.uniqid = uniqid;
	}
	public String getIndexUUID() {
		return indexUUID;
	}
	public void setIndexUUID(String indexUUID) {
		this.indexUUID = indexUUID;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public String getMetricType() {
		return metricType;
	}
	public void setMetricType(String metricType) {
		this.metricType = metricType;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    public void setJbillingTimeStamp(Date jbillingTimeStamp){
		this.jbillingTimeStamp = jbillingTimeStamp;
	}
	
	public Date getJbillingTimeStamp(){
		return jbillingTimeStamp;
	}
}
