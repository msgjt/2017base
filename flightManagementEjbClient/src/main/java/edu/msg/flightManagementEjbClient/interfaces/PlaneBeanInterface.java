package edu.msg.flightManagementEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;

@Remote
public interface PlaneBeanInterface {

	PlaneDTO insertPlane(PlaneDTO plane) throws RemoteException;
	
	PlaneDTO updatePlane(PlaneDTO plane) throws RemoteException;
	
	List<PlaneDTO> getAllPlanes()throws RemoteException;
	
	PlaneDTO getPlaneById(int planeId) throws RemoteException;
	
	PlaneDTO getPlaneByModel(String model) throws RemoteException;
	
	List<PlaneDTO> getAllPlanesByCompany(CompanyDTO company)throws RemoteException;
	
	List<PlaneDTO> getAvailablePlanes() throws RemoteException;
	
	void deletePlane(PlaneDTO planeDTO);
}
