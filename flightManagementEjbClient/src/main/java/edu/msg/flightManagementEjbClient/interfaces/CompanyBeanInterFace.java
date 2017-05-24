package edu.msg.flightManagementEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;

@Remote
public interface CompanyBeanInterFace {
	
	CompanyDTO insertCompany(CompanyDTO companyDTO) throws RemoteException;
	
	CompanyDTO updateCompany(CompanyDTO companyDTO) throws RemoteException;
	
	void deleteCompany(CompanyDTO companyDTO) throws RemoteException;
	
	List<CompanyDTO> getCompanies() throws RemoteException;
	
	CompanyDTO getByCompanyName(String companyName) throws RemoteException;
	
	CompanyDTO getByCompanyId(int id) throws RemoteException;
	
	List<CompanyDTO> getAvailableCompanies() throws RemoteException;
	
}
