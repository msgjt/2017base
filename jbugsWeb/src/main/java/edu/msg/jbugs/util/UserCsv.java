package edu.msg.jbugs.util;

import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import edu.msg.jbugs.dtos.UserDTO;
import edu.msg.jbugs.services.UserFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Import users from csv file to database
 * Export users from database to csv file
 * 
 * 
 * @author codaui
 *
 */
@Stateless
public class UserCsv extends CsvFactory implements Serializable {

	private static final long serialVersionUID = 641660445686792941L;
	private static Logger logger = LoggerFactory.getLogger(UserCsv.class);
	
	@EJB
	private UserFacade ub;
	
	@Override
	public boolean importCsv(String filename) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("C:/");
            sb.append(filename);
            CSVReader csvReader = new CSVReader(new FileReader(sb.toString()), ',', '"', 1);
            ColumnPositionMappingStrategy<UserDTO> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(UserDTO.class);
            String[] columns = new String[]{"lastName", "firstName", "userName", "password", "type", "status"};
            mappingStrategy.setColumnMapping(columns);
            CsvToBean<UserDTO> ctb = new CsvToBean<>();
            List<UserDTO> userList = ctb.parse(mappingStrategy, csvReader);
            for (UserDTO udto : userList) {
            	ub.insertUser(udto);
            }
            csvReader.close();
            return true;
        } catch (IOException e) {
        	logger.error("Error at importing users ", e);
        }
    	return false;
	}

	@Override
	public boolean exportCsv() {
		try {
            String csv = "C:/allusers.csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv));
            BeanToCsv<UserDTO> bc = new BeanToCsv<>();
            ColumnPositionMappingStrategy<UserDTO> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(UserDTO.class);
            String[] columns = new String[]{"userId", "lastName", "firstName", "userName", "password", "type", "status"};
            mappingStrategy.setColumnMapping(columns);
            List<UserDTO> someList = ub.getUsers();
            bc.write(mappingStrategy,csvWriter,someList);
            csvWriter.close();
            return true;
		} catch(IOException e) {
			logger.error("Error at exporting users ", e);
		}
		return false;
	}

}