package edu.msg.flightManagement.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.interfaces.CompanyBeanInterFace;

/**
 * 
 * @author kelemeni Service used by the CompanyConverter to retrieve a company
 *         by it's ID
 */
@ManagedBean(name = "companyService", eager = true)
@ApplicationScoped
public class CompanyService {

	@EJB
	private CompanyBeanInterFace cb;

	/**
	 * 
	 * @param id
	 * @return a company from the database by it's ID
	 */
	public CompanyDTO getCompany(int id) {
		return cb.getByCompanyId(id);
	}
}
