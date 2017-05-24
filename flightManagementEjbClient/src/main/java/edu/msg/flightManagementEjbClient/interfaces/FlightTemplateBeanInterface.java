package edu.msg.flightManagementEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlighttemplateDTO;

@Remote
public interface FlightTemplateBeanInterface {
	
	FlighttemplateDTO insertFlightTemplate(FlighttemplateDTO flightTemplateDTO) throws RemoteException;
	
	FlighttemplateDTO updateFlightTemplate(FlighttemplateDTO flightTemplateDTO) throws RemoteException;
	
	FlighttemplateDTO  findById(int flightTemplateId) throws RemoteException;
	
	void deleteFlightTemplate(FlighttemplateDTO flightTemplateDTO) throws RemoteException;
	
	void deleteFlightTemplateById(int flightTemplateId) throws RemoteException;
	
	List<FlighttemplateDTO> getFlightTemplates() throws RemoteException;
	
	List<FlighttemplateDTO> getFlightTemplatesByCompany(CompanyDTO company) throws RemoteException;
	
	List<FlighttemplateDTO> getAvailableTemplates() throws RemoteException;
}
