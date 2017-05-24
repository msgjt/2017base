package edu.msg.flightManagementEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.msg.flightManagementEjbClient.dtos.FlightHistoryDTO;

@Remote
public interface FlightHistoryBeanInterface {

	List<FlightHistoryDTO> getHistoryFlightsByCompanyId(int companyId) throws RemoteException;
	
	 List<FlightHistoryDTO> getFlightHistories() throws RemoteException;
}
