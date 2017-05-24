package edu.msg.flightManagement.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.PlaneBeanInterface;

/**
 * Read data about planes from csv file and add into the database
 * Write all data about planes from database to csv file
 * 
 * @author codaui
 *
 */
@Stateless
public class PlaneCsv extends CsvFactory implements Serializable {

	private static final long serialVersionUID = 641660445686792941L;
	private static Logger logger = LoggerFactory.getLogger(PlaneCsv.class);
	
	@EJB
	private PlaneBeanInterface pb;
	
	@EJB
	private CompanyBeanInterFace cb;
	
	@Override
	public boolean importCsv(String filename) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("C:/");
            sb.append(filename);
            CSVReader csvReader = new CSVReader(new FileReader(sb.toString()), ',', '"', 1);
            String[] nextLine;
    		while ((nextLine = csvReader.readNext()) != null) {
    			PlaneDTO pdto = new PlaneDTO();
    			pdto.setModel(nextLine[0]);
    			pdto.setCapacity(Integer.parseInt(nextLine[1]));
    			pdto.setStatus(Boolean.parseBoolean(nextLine[2]));
    			pdto.setCompany(cb.getByCompanyId(Integer.parseInt(nextLine[3])));
    			pb.insertPlane(pdto);
    		}
            csvReader.close();
            return true;
        } catch(IOException e) {
        	logger.error("IO Exception at reading plane from csv", e);
        } catch (NumberFormatException e) {
        	logger.error("Number format exception", e);
        }
    	return false;
	}

	@Override
	public boolean exportCsv() {
		try {
            String csv = "C:/allplanes.csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv));
            List<PlaneDTO> planesList = pb.getAllPlanes();
        	String[] entries = "model,capacity,status,companyId".split(",");
        	csvWriter.writeNext(entries);
            for (int i = 0; i < planesList.size(); i++ ) {
            	entries[0] = planesList.get(i).getModel();
            	entries[1] = Integer.toString(planesList.get(i).getCapacity());
            	entries[2] = Boolean.toString(planesList.get(i).getStatus());
            	entries[3] = Integer.toString(planesList.get(i).getCompany().getCompanyId());
            	csvWriter.writeNext(entries);
            }
            csvWriter.close();
            return true;
        } catch(IOException e) {
            logger.error("IO Exception at writing plane to csv", e);
        }
		return false;
	}
}
