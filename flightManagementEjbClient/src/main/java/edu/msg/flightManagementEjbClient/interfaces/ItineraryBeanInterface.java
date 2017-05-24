package edu.msg.flightManagementEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;

@Remote
public interface ItineraryBeanInterface {

	ItineraryDTO insertItinerary(ItineraryDTO itineraryDTO) throws RemoteException;

	ItineraryDTO findById(int itineraryId) throws RemoteException;

	ItineraryDTO updateItinerary(ItineraryDTO itineraryDTO) throws RemoteException;

	void deleteItinerary(ItineraryDTO itineraryDTO) throws RemoteException;

	void deleteItineraryById(int itineraryId) throws RemoteException;

	List<ItineraryDTO> getItineraries() throws RemoteException;

	ItineraryDTO getByItineraryName(String itineraryName) throws RemoteException;

	List<ItineraryDTO> getByCompany(CompanyDTO company) throws RemoteException;

	void deleteItineraryByFlightId(int flightId) throws RemoteException;
}
