package edu.msg.flightManagementEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.msg.flightManagementEjbClient.dtos.AirportDTO;

@Remote
public interface AirportBeanInterface {
	
    AirportDTO insertAirport(AirportDTO airportDTO) throws RemoteException;
	
    AirportDTO updateAirport(AirportDTO airportDTO) throws RemoteException;
	
    AirportDTO getAirportByName(String airportName) throws RemoteException;
	
    AirportDTO  findById(int airportId) throws RemoteException;
	
	void deleteAirport(AirportDTO AirportDTO) throws RemoteException;
	
	void deleteAirportById(int airportId) throws RemoteException;
	
	List<AirportDTO> getAirports() throws RemoteException;
	
	List<AirportDTO> getAvailableAirports() throws RemoteException;
	
}
