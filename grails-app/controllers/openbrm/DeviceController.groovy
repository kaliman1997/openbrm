

package openbrm


import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.device.db.DeviceDTO
import com.sapienter.jbilling.server.device.DeviceWS

import com.sapienter.jbilling.server.user.db.CompanyDTO
import com.sapienter.jbilling.server.device.DeviceBL
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.client.ViewUtils
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.sapienter.jbilling.client.util.SortableCriteria



import com.sapienter.jbilling.server.util.IWebServicesSessionBean;

import org.springframework.jdbc.datasource.DataSourceUtils
import javax.sql.DataSource
import com.sapienter.jbilling.server.device.db.DeviceDAS
import com.sapienter.jbilling.server.device.db.DeviceStatusDAS
import com.sapienter.jbilling.server.util.csv.CsvExporter
import com.sapienter.jbilling.server.util.csv.Exporter
import com.sapienter.jbilling.client.util.DownloadHelper

import java.text.*;
import java.util.*; 
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import java.lang.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
//import grails.plugins.springsecurity.Secured
import grails.plugin.springsecurity.SpringSecurityUtils
//import grails.plugin.springsecurity.annotation.Secured
import grails.util.Holders;
import jbilling.FilterType;


//@Secured(["MENU_100"])
class DeviceController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
    def filterService

    ViewUtils viewUtils
    DataSource dataSource
	def breadcrumbService


    def index = {
        redirect action: 'list', params: params
    }

     def private getFilteredDevices(filters, GrailsParameterMap params) {
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return DeviceDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {
           try{
            and {
					filters.each { filter ->
						if (filter.value != null) {
							 if (filter.constraintType == FilterConstraint.STATUS) {
								 def statuses = new DeviceStatusDAS().findAll()
								 
							eq("deviceStatus", statuses.find { it.primaryKey?.equals(filter.integerValue) })
                        }else{
							addToCriteria(filter.getRestrictions());
							}
						}
					}
					eq('entity', new CompanyDTO(session['company_id']))
				}
        }catch (Exception e) {
		log.debug("exception is "+e)
				}

            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }

    def list = {
	def filters = filterService.getFilters(FilterType.DEVICE, params)
	def devices = getFilteredDevices(filters, params)
	
        def selected = params.id ? webServicesSession.getDeviceWS(params.int("id")) : null
		breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)
		
        if (params.applyFilter || params.partial) {
//	    params.applyFilter = true;
	    render template: 'devices', model: [ devices: devices, selected: selected, filters: filters ]

        } else {
             [ devices: devices, selected: selected, filters: filters ]
        }
	
    }

        /* @Secured(["ORDER_24"]) */

    def show = {
        DeviceWS devicews = webServicesSession.getDevicesWS(params.int('id'))
        render template:'show', model: [devicews: devicews]
    }

	/*def user = {
		def filter = new Filter(type: FilterType.DEVICE, constraintType: FilterConstraint.EQ, field: 'baseUserByUserId.id', template: 'id', visible: true, integerValue: params.int('id'))
		filterService.setFilter(FilterType.DEVICE, filter)
		redirect action: 'list'
	}*/

    def edit = {
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
	
            render template: 'edit'  
        
    }



	 def save = {

		log.debug("In Device Controllers save action");
        def device = new DeviceWS();
        //bindData(device, params)         
         String [] error;
		error=null;
		def errorCount;
        errorCount=0;
		def errorNotFormat;
		errorNotFormat=0
		def col0
        def icc;
		def imsi;
		def pin1;
		def pin2;
		def puk1Temp;
		def puk2Temp;


        def temp = null
		try {
           	// save or update
			def devicefile = request.getFile("devicefile")   
			log.debug("SIM cars input file " + devicefile + "content " + params.devicefile?.getContentType().toString());

		   	/* if (params.devicefile?.getContentType().toString().contains('text/csv')) {*/
			if(!devicefile.empty) {
				log.debug("devicefile isn't empty");
				def grailsApplication = Holders.getGrailsApplication()				
				//def tempDir = grailsApplication.config.dir.variable
				def tempDir = "F:/mamatharapolu/jbilling-community-4.1.1/jbilling-community-4.1.1/temp"
				//tempDir = tempDir + "//resources//temp"
						  
				File directory = new File(tempDir)
				
				if(!directory.exists()){					
					dirEx=directory.mkdir()
				}
                temp = File.createTempFile('devices', '.txt',directory)
                devicefile.transferTo(temp)
				File file = new File(temp?.getAbsolutePath()); 
				BufferedReader reader = new BufferedReader(new java.io.FileReader(file));

                log.debug("device card csv saved to: " + temp?.getAbsolutePath());

				String[] cols = new Object[8]
				def idx
				def devicews
				def y 			
				String line
				def i

				while((line = reader.readLine())!= null && line.length()!= 0) {

					if(line.startsWith("#")) continue;	//comment
					if(("").equals(line)) continue;		//blank line
					cols = line.split(";", -1); 
					if(cols.size() != 7) {
		 				errorNotFormat++;
						//flash.error = 'file is not in format'
						log.debug("size of fields"+cols.size());
						//continue;
		 			}
						
					try{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");			 
					
						if(cols.size()==7){

							icc = cols[0];
							imsi = cols[1];
							pin1 = cols[2];
							puk1Temp=cols[3];
							pin2 = cols[4];
							puk2Temp=cols[5];
					
							if(icc.isEmpty()|| imsi.isEmpty() ||pin1.isEmpty()||pin2.isEmpty()||puk1Temp.isEmpty()||puk2Temp.isEmpty()){
								log.debug(" file is not format ");
								errorNotFormat++;
							}else{
								log.debug(" none of the field  is empty");
								def today = new Date()
								Date lastUpdatedDate = today;
								Date createdDate = today;
								Integer puk1= Integer.parseInt(puk1Temp);
								log.debug("value of puk1:"+puk1)
								Integer puk2=Integer.parseInt(puk2Temp);
								devicews = new DeviceWS(icc, imsi, puk1, puk2,pin1,pin2, createdDate, lastUpdatedDate);
								y = webServicesSession.createDevices(devicews);
					            log.debug("created devices :"+y);
							}
						}
					}catch (Exception e){
						e.printStackTrace();
						if( errorNotFormat!=0){
							errorCount++
						}
                        
						File dir = new File("F:/mamatharapolu/jbilling-community-4.1.1/jbilling-community-4.1.1/temp/devicecsverrors");

						log.debug(" file is created at:"+dir)
						Writer writer = null;
 
						try {
							File errorFile = new File("error.txt");
							writer = new BufferedWriter(new FileWriter(file));
							log.debug(" under writer")
							writer.write(icc);
							log.debug(" writed record to file"+icc)
						} catch (FileNotFoundException fileError) {
							log.debug("FileNotFoundException"+fileError)
						} catch(IOException fileIOError) {
							log.debug(" IOException"+fileIOError)
						} finally {
							try {
								if (writer != null) {
									writer.close();
								}   
							} catch (IOException closeError) {
								log.debug(" IOException"+closeError)
							}
						}
				    }
				}			
			}
		    	
			//def devicewsd = new DeviceWS();
			 if(errorNotFormat>0)
			 {
                             log.debug(" errorNotFormat  count"+errorNotFormat)
			    flash.error = 'file is not in format'
                              errorNotFormat=0
			 }
			  else if(errorCount>0)
			 {
			    
			     log.debug(" errorCount"+errorCount)
                             flash.error  ='Those Records alredy  existing.Duplications record  are takes place..Duplication Deatils avialble inF:/mamatharapolu/jbilling-community-4.1.1/jbilling-community-4.1.1/temp/devicecsverrors'
			     
			   
			 }
			 else
			{
			flash.message ='SIM CARD deatils are Sucessfully Loaded'
                        }  
                                    
                }
		
		catch (SessionInternalError e) {
                    	viewUtils.resolveException(flash, session.locale, e)
                    	chain action: 'list', model: [ selected: device ]
			return

                } finally {
                    temp?.delete()
                }
            

        chain action: 'list', params: [ id: device?.id ]
    }  


    def splitfields(line) {

	log.debug("splitfields is called");
	return line.split(",", -1);

    }


    def deleteDevice = {
        try {
            webServicesSession.deleteDevice(params.int('id'))
            flash.message = 'order.delete.success'
            flash.args = [params.id, params.id]
        } catch (SessionInternalError e){
            flash.error ='order.error.delete'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list'
    }


    def csv = {
        /*def filters = filterService.getFilters(FilterType.ORDER, params)

        params.max = CsvExporter.MAX_RESULTS
        def orders = getFilteredOrders(filters, params)

        if (orders.totalCount > CsvExporter.MAX_RESULTS) {
            flash.error = message(code: 'error.export.exceeds.maximum')
            flash.args = [ CsvExporter.MAX_RESULTS ]
            redirect action: 'list', id: params.id

        } else {
            DownloadHelper.setResponseHeader(response, "orders.csv")
            Exporter<OrderDTO> exporter = CsvExporter.createExporter(OrderDTO.class);
            render text: exporter.export(orders), contentType: "text/csv"
        }*/
    }

}
