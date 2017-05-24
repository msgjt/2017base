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

import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.ItineraryBeanInterface;

/**
 * Read data from database and write it to csv file Read data from csv file and
 * write it to database
 * 
 * 
 * @author codaui
 *
 */
@Stateless
public class ItineraryCsv extends CsvFactory implements Serializable {

	private static final long serialVersionUID = 641660445686792941L;
	private static Logger logger = LoggerFactory.getLogger(ItineraryCsv.class);

	@EJB
	private ItineraryBeanInterface ib;

	@EJB
	private AirportBeanInterface ab;

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
				ItineraryDTO idto = new ItineraryDTO();
				idto.setItineraryName(nextLine[0]);
				idto.setAirport1(ab.findById(Integer.parseInt(nextLine[1])));
				idto.setAirport2(ab.findById(Integer.parseInt(nextLine[2])));
				idto.setCompany(cb.getByCompanyId(Integer.parseInt(nextLine[3])));
				idto.setFlights(new ArrayList<FlightDTO>());
				ib.insertItinerary(idto);
			}
			csvReader.close();
			return true;
		} catch (IOException e) {
			logger.error("IO exception at reading itinerary from csv ", e);
		} catch (NumberFormatException e) {
			logger.error("Number Format Exception at reading itinerary from csv", e);
		} catch (IllegalFormatException e) {
			logger.error("Format for a field is not good at reading itinerary from csv");
		}
		return false;
	}

	@Override
	public boolean exportCsv() {
		try {
			String csv = "C:/allitineraries.csv";
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csv));
			List<ItineraryDTO> itinerariesList = ib.getItineraries();
			String[] entries = "itineraryId,itineraryName,airport1,airport2,company".split(",");
			csvWriter.writeNext(entries);
			for (int i = 0; i < itinerariesList.size(); i++) {
				entries[0] = Integer.toString(itinerariesList.get(i).getItineraryId());
				entries[1] = itinerariesList.get(i).getItineraryName();
				if (itinerariesList.get(i).getAirport1() != null) {
					entries[2] = itinerariesList.get(i).getAirport1().getAirportName();
				}
				if (itinerariesList.get(i).getAirport2() != null) {
					entries[3] = itinerariesList.get(i).getAirport2().getAirportName();
				}
				if (itinerariesList.get(i).getCompany() != null) {
					entries[4] = itinerariesList.get(i).getCompany().getCompanyName();
					csvWriter.writeNext(entries);
				}

			}
			csvWriter.close();
			return true;
		} catch (IOException e) {
			logger.error("Error at writing itineraries to csv file ", e);
		}
		return false;
	}
}
