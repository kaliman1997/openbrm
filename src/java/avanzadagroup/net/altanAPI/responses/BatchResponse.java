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
public class BatchResponse extends GeneralResponse {
    private String effectiveDate;
    private String lines;
    private String transactionId;
    private String errorCode;
    private String description;


 

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }  

    public String getLines() {
		return lines;
	}

	public void setLines(String lines) {
		this.lines = lines;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

    
}
