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
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;

/**
 * Read airport from csv file and insert into database
 * Write airports from database to a csv file
 * 
 * @author codaui
 *
 */
@Stateless
public class AirportCsv extends CsvFactory implements Serializable {

	private static final long serialVersionUID = 641660445686792941L;
	private static Logger logger = LoggerFactory.getLogger(AirportCsv.class);
	
	@EJB
	private AirportBeanInterface ab;
	
	@Override
	public boolean importCsv(String filename) {
		try {
			StringBuilder sb = new StringBuilder();
	        sb.append("C:/");
	        sb.append(filename);
	        CSVReader csvReader = new CSVReader(new FileReader(sb.toString()), ',', '"', 1);
	        ColumnPositionMappingStrategy<AirportDTO> mappingStrategy = new ColumnPositionMappingStrategy<>();
	        mappingStrategy.setType(AirportDTO.class);
	        String[] columns = new String[]{"airportName", "country", "city", "address", "status"};
	        mappingStrategy.setColumnMapping(columns);
	        CsvToBean<AirportDTO> ctb = new CsvToBean<>();
	        List<AirportDTO> airportList = ctb.parse(mappingStrategy, csvReader);
	        for (AirportDTO adto : airportList) {
	        	ab.insertAirport(adto);
	        }
	        csvReader.close();
	        return true;
		} catch (IOException e) {
			logger.error("IO Exception at reading airports from CSV ", e);
		}
		return false;
	}

	@Override
	public boolean exportCsv() {
		try {
            String csv = "C:/allairports.csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv));
            BeanToCsv<AirportDTO> bc = new BeanToCsv<>();
            ColumnPositionMappingStrategy<AirportDTO> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(AirportDTO.class);
            String[] columns = new String[]{"airportId", "airportName", "country", "city", "address", "status"};
            mappingStrategy.setColumnMapping(columns);
            List<AirportDTO> someList = ab.getAirports();
            bc.write(mappingStrategy,csvWriter,someList);
            csvWriter.close();
            return true;
		} catch (IOException e) {
			logger.error("IO Error at writing CSV ", e);
		}
		return false;
	}

}