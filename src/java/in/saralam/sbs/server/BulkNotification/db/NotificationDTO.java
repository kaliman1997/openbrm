package in.saralam.sbs.server.BulkNotification.db;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.apache.log4j.Logger;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@TableGenerator(
		name="bulk_notification_type_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "bulk_notification_type",
		allocationSize = 100
		)
@Table(name="bulk_notification_type")

public class NotificationDTO {
	
	private int  id;
	private String type;
	
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="bulk_notification_type_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="type")
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
		
		@Override
    public String toString() {
        return "NotificationDTO:[" +
               " id=" + id +
               " type=" + type+"]";
    }
}
