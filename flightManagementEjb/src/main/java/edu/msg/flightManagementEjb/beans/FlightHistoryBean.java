package edu.msg.flightManagementEjb.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.flightManagementEjb.assemblers.FlightHistoryAssembler;
import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.daos.FlightHistoryDAO;
import edu.msg.flightManagementEjb.model.FlightHistory;
import edu.msg.flightManagementEjbClient.dtos.FlightHistoryDTO;
import edu.msg.flightManagementEjbClient.interfaces.FlightHistoryBeanInterface;
import edu.msg.flightManagementEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class FlightHistoryBean implements FlightHistoryBeanInterface {

	private static Logger logger = LoggerFactory.getLogger(FlightHistoryBean.class);

	@EJB
	private FlightHistoryDAO flightHistoryDao;

	private FlightHistoryAssembler flightHistoryAssembler = new FlightHistoryAssembler();

	@Override
	public List<FlightHistoryDTO> getHistoryFlightsByCompanyId(int companyId) throws RemoteException {
		List<FlightHistoryDTO> flightList = new ArrayList<>();
		try {
			List<FlightHistory> list = flightHistoryDao.getHistoryFlightsByCompanyId(companyId);
			flightList = new ArrayList<>();
			for (FlightHistory fh : list) {
				FlightHistoryDTO fDto = flightHistoryAssembler.modelToDto(fh);
				flightList.add(fDto);
			}
			return flightList;
		} catch (DaoException e) {
			logger.error("getHistoryFlightsByCompanyId failed", e);
			throw new RemoteException(e.getMessage(), e);

		}

	}

	@Override
	public List<FlightHistoryDTO> getFlightHistories() throws RemoteException {
		try {
			List<FlightHistory> list = flightHistoryDao.getFlightHistories();
			List<FlightHistoryDTO> flightList = new ArrayList<>();
			for (FlightHistory fh : list) {
				FlightHistoryDTO fDto = flightHistoryAssembler.modelToDto(fh);
				flightList.add(fDto);
			}
			return flightList;
		} catch (DaoException e) {
			logger.error("getFlightHistories failed", e);
			throw new RemoteException(e.getMessage(), e);

		}

	}
}
