package edu.msg.flightManagementEjbClient.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;

@Remote
public interface FlightBeanInterface {

	FlightDTO insertFlight(FlightDTO flightDTO) throws RemoteException;

	FlightDTO updateFlight(FlightDTO flightDTO) throws RemoteException;

	void deleteFlight(FlightDTO flightDTO) throws RemoteException;

	List<FlightDTO> getAllFlights() throws RemoteException;

	FlightDTO getFlightById(int id) throws RemoteException;

	List<FlightDTO> getAvailableFlights() throws RemoteException;

	List<FlightDTO> getAllFlightsByCompany(CompanyDTO company) throws RemoteException;

	List<FlightDTO> getByCompanyAndDate(CompanyDTO company, Date date) throws RemoteException;

	List<FlightDTO> getByCompanyAndDate(CompanyDTO company, Date date, AirportDTO airport) throws RemoteException;

	boolean isPlaneFree(PlaneDTO planeDto, Date startDate, Date endDate, int selfId);
}
