/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */
package com.sapienter.jbilling.server.util.audit.db;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.db.JbillingTable;

@Entity
@org.hibernate.annotations.Entity(mutable = false)
@TableGenerator(
        name="event_log_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="event_log",
        allocationSize = 1000
        )
@Table(name="event_log")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EventLogDTO  implements java.io.Serializable {



    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="event_log_GEN")
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="table_id")
    private JbillingTable jbillingTable;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserDTO baseUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="affected_user_id")
    private UserDTO affectedUser;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="message_id", nullable=false)
    private EventLogMessageDTO eventLogMessage;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="module_id", nullable=false)
    private EventLogModuleDTO eventLogModule;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="entity_id")
    private CompanyDTO company;

    @Column(name="foreign_id", nullable=false)
    private int foreignId;

    @Column(name="create_datetime", nullable=false, length=29)
    private Date createDatetime;

    @Column(name="level_field", nullable=false)
    private int levelField;

    @Column(name="old_num")
    private Integer oldNum;

    @Column(name="old_str", length=1000)
    private String oldStr;

    @Column(name="old_date", length=29)
    private Date oldDate;     

    @Version
    @Column(name="OPTLOCK")
    private Integer versionNum;


    public EventLogDTO() {
    }

    
    public EventLogDTO(Integer id, JbillingTable jbillingTable, 
            UserDTO baseUser, UserDTO affectedUser, 
            EventLogMessageDTO eventLogMessage, 
            EventLogModuleDTO eventLogModule, CompanyDTO entity, int foreignId,
            int levelField, Integer oldNum, String oldStr, Date oldDate) {
       this.id = id;
       this.jbillingTable = jbillingTable;
       this.baseUser = baseUser;
       this.affectedUser = affectedUser;
       this.eventLogMessage = eventLogMessage;
       this.eventLogModule = eventLogModule;
       this.company = entity;
       this.foreignId = foreignId;
       this.createDatetime = Calendar.getInstance().getTime();
       this.levelField = levelField;
       this.oldNum = oldNum;
       this.oldStr = oldStr;
       this.oldDate = oldDate;
    }
   
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
		this.id = id;
	}
    
    public JbillingTable getJbillingTable() {
        return this.jbillingTable;
    }
    
    public void setJbillingTable(JbillingTable jbillingTable) {
		this.jbillingTable = jbillingTable;
	}
    
    
    public UserDTO getBaseUser() {
        return this.baseUser;
    }

    public void setBaseUser(UserDTO baseUser) {
		this.baseUser = baseUser;
	}
    
    public UserDTO getAffectedUser() {
        return this.affectedUser;
    }
    
    public void setAffectedUser(UserDTO affectedUser) {
		this.affectedUser = affectedUser;
	}
    
    public EventLogMessageDTO getEventLogMessage() {
        return this.eventLogMessage;
    }
    
    public void setEventLogMessage(EventLogMessageDTO eventLogMessage) {
		this.eventLogMessage = eventLogMessage;
	}
    
    public EventLogModuleDTO getEventLogModule() {
        return this.eventLogModule;
    }
    
    public void setEventLogModule(EventLogModuleDTO eventLogModule) {
		this.eventLogModule = eventLogModule;
	}
    
    public CompanyDTO getCompany() {
        return this.company;
    }
    
    public void setCompany(CompanyDTO company) {
		this.company = company;
	}
        
    public int getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(int foreignId) {
		this.foreignId = foreignId;
	}

    public Date getCreateDatetime() {
        return this.createDatetime;
    }
    
    public void setCreatedDateTime(Date createDatetime)  {
	    this.createDatetime = createDatetime;
	}
    
    public int getLevelField() {
        return this.levelField;
    }
    
    public void setLevelField(int levelField)  {
	    this.levelField = levelField;
	}
    
    public Integer getOldNum() {
        return this.oldNum;
    }
    
    public void setOldNum(Integer oldNum) {
		this.oldNum = oldNum;
	}
    
    public String getOldStr() {
        return this.oldStr;
    }

    public void setOldStr(String oldStr) {
		this.oldStr = oldStr;
	}
    
    public Date getOldDate() {
        return this.oldDate;
    }

    public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
	}
    
    protected int getVersionNum() { 
    	return this.versionNum; 
    	}
    
    public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

    public void touch() {
        getJbillingTable().getName();
        if (getBaseUser() != null) {
            getBaseUser().getUserName();
        }
        getEventLogModule().getId();
        getEventLogMessage().getId();
    }
}


