package edu.msg.flightManagement.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.FlighttemplateDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;

/**
 * Class to create the CSV for Company, extends the abstract class CsvFactory
 * Implementations for both import and export
 * 
 * @author codaui
 *
 */
@Stateless
public class CompanyCsv extends CsvFactory implements Serializable {

	private static final long serialVersionUID = 641660445686792941L;
	private static Logger logger = LoggerFactory.getLogger(CompanyCsv.class);
	
	@EJB
	private CompanyBeanInterFace cb;
	
	@Override
	public boolean importCsv(String filename) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("C:/");
            sb.append(filename);
            CSVReader csvReader = new CSVReader(new FileReader(sb.toString()), ',', '"', 1);
            ColumnPositionMappingStrategy<CompanyDTO> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(CompanyDTO.class);
            String[] columns = new String[]{"companyName", "address", "phone", "email", "status"};
            mappingStrategy.setColumnMapping(columns);
            CsvToBean<CompanyDTO> ctb = new CsvToBean<>();
            List<CompanyDTO> companyList = ctb.parse(mappingStrategy, csvReader);
            for (CompanyDTO cdto : companyList) {
            	cdto.setFlights(new ArrayList<FlightDTO>());
            	cdto.setFlighttemplates(new ArrayList<FlighttemplateDTO>());
            	cdto.setPlanes(new ArrayList<PlaneDTO>());
            	cdto.setUsers(new ArrayList<UserDTO>());
            	cb.insertCompany(cdto);
            }
			csvReader.close();
			return true;
			} catch (IOException e) {
				logger.error("Exception at closing the reader ", e);
			} catch (IllegalFormatException e) {
				logger.error("Illegal format at import companies ", e);
			}
		return false;
	}

	@Override
	public boolean exportCsv() {
        try {
            String csv = "C:/allcompanies.csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv));
            BeanToCsv<CompanyDTO> bc = new BeanToCsv<>();
            ColumnPositionMappingStrategy<CompanyDTO> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(CompanyDTO.class);
            String[] columns = new String[]{"companyId", "companyName", "address", "phone", "email", "status"};
            mappingStrategy.setColumnMapping(columns);
            List<CompanyDTO> someList = cb.getCompanies();
            bc.write(mappingStrategy,csvWriter,someList);
			csvWriter.close();
			return true;
			} catch (IOException e) {
				logger.error("IO Error", e);
			}
		return false;
	}

}