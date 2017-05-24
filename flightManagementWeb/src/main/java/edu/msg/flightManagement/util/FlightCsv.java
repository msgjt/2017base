package edu.msg.flightManagement.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.PlaneBeanInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Csv writer/reader for flights in order to import/export these data
 * 
 * @author codaui
 *
 */
@Stateless
public class FlightCsv extends CsvFactory implements Serializable {

	private static final long serialVersionUID = 641660445686792941L;
	private static Logger logger = LoggerFactory.getLogger(FlightCsv.class);
	
	@EJB
	private FlightBeanInterface fb;
	
	@EJB
	private AirportBeanInterface ab;
	
	@EJB
	private PlaneBeanInterface pb;
	
	@EJB
	private CompanyBeanInterFace cb;
	
	@Override
	public boolean importCsv(String filename) {
		try{
        StringBuilder sb = new StringBuilder();
        sb.append("C:/");
        sb.append(filename);
        CSVReader csvReader = new CSVReader(new FileReader(sb.toString()), ',', '"', 1);
        String[] nextLine;
		while ((nextLine = csvReader.readNext()) != null) {
			FlightDTO fdto = new FlightDTO();
			String pattern="EEE, d MMM yyyy HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
			fdto.setAirport1(ab.findById(Integer.parseInt(nextLine[0])));
			fdto.setAirport2(ab.findById(Integer.parseInt(nextLine[1])));
			fdto.setStartDate(sdf.parse(nextLine[2]));
			fdto.setEndDate(sdf.parse(nextLine[3]));
			fdto.setPlane(pb.getPlaneById(Integer.parseInt(nextLine[4])));
			fdto.setCompany(cb.getByCompanyId(Integer.parseInt(nextLine[5])));
			fdto.setItineraries(new ArrayList<ItineraryDTO>());
			fdto.setUsers(new ArrayList<UserDTO>());
			fb.insertFlight(fdto);
		}
		csvReader.close();
		return true;
		} catch (IOException e) {
			logger.error("IO Exception ", e);
		} catch (NumberFormatException e) {
			logger.error("Wrong number format", e);
		} catch (ParseException e) {
			logger.error("Wrong date and time format ", e);
		}
		return false;
	}

	@Override
	public boolean exportCsv() {
		try {
        String csv = "C:/allflights.csv";
        CSVWriter csvWriter = new CSVWriter(new FileWriter(csv));
        List<FlightDTO> flightsList = fb.getAllFlights();
    	String[] entries = "flightId,airport1,airport2,startDate,endDate, plane, company".split(",");
    	csvWriter.writeNext(entries);
        for (int i = 0; i < flightsList.size(); i++ ) {
        	entries[0] = Integer.toString(flightsList.get(i).getFlightId());
        	entries[1] = flightsList.get(i).getAirport1().getAirportName();
        	entries[2] = flightsList.get(i).getAirport2().getAirportName();
        	entries[3] = flightsList.get(i).getStartDate().toString();
        	entries[4] = flightsList.get(i).getEndDate().toString();
        	entries[5] = flightsList.get(i).getPlane().getModel();
        	entries[6] = flightsList.get(i).getCompany().getCompanyName();
        	csvWriter.writeNext(entries);
        }
        csvWriter.close();
        return true;
		} catch (IOException e) {
			logger.error("IO Exception at writing ", e);
		}
		return false;
	}

}