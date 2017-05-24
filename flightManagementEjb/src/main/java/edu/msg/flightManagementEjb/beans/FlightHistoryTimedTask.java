package edu.msg.flightManagementEjb.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import edu.msg.flightManagementEjb.daos.DaoException;
import edu.msg.flightManagementEjb.daos.FlightDAO;
import edu.msg.flightManagementEjb.daos.FlightHistoryDAO;
import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.FlightHistory;

@Singleton
@Startup
public class FlightHistoryTimedTask {

	@Resource
	private TimerService timerService;

	private final Logger logger = Logger.getLogger(getClass().getName());

	@Resource
	private SessionContext ctx;

	@EJB
	private FlightDAO flightDao;

	@EJB
	private FlightHistoryDAO flightHistoryDao;

	private LocalDateTime localDate;

	public LocalDateTime getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDateTime localDate) {
		this.localDate = localDate;
	}

	@PostConstruct
	public void init() {
		logger.info("ProgrammaticalTimerEJB initialized");
		localDate = LocalDateTime.MIN;
		ScheduleExpression everyTenSeconds = new ScheduleExpression().minute("*/1").hour("*");
		timerService.createCalendarTimer(everyTenSeconds, new TimerConfig("Date" + localDate, false));
	}

	@Timeout
	public void processFlights(Timer timer) {
		// get deleted flights
		List<Flight> deletedFlights = new ArrayList<>();
		try {
			deletedFlights = getDeletedFlights();
		} catch (DaoException e) {
			logger.info(e.getMessage());
		}
		if (deletedFlights == null) {
			logger.info("No flights deleted since last time");
		} else {
			logger.info("New deleted flights detected since last time");
			for (Flight f : deletedFlights) {
				FlightHistory fh = new FlightHistory();
				fh.setDescription("deleted");
				fh.setFlight(f);
				try {
					flightHistoryDao.insertFlightHistory(fh);
				} catch (DaoException e) {
					logger.info(e.getMessage());
				}
			}
		}

		// get flights that were completed
		List<Flight> completedFlights = new ArrayList<>();
		try {
			completedFlights = getCompletedFlights();
		} catch (DaoException e) {
			logger.info(e.getMessage());
		}

		if (completedFlights == null) {
			logger.info("No flights completed since last time");
		} else {
			logger.info("New flights completed detected since last time");
			for (Flight f : completedFlights) {
				FlightHistory fh = new FlightHistory();
				fh.setDescription("completed");
				fh.setFlight(f);
				try {
					flightHistoryDao.insertFlightHistory(fh);
				} catch (DaoException e) {
					logger.info(e.getMessage());
				}

			}
		}
	}

	public List<Flight> getDeletedFlights() {
		List<Flight> deletedFlights = new ArrayList<>();
		List<Flight> flightFromhistory = new ArrayList<>();

		try {
			deletedFlights = flightDao.getFlightsByStatus(false);
		} catch (DaoException e) {
			logger.info(e.getMessage());
		}

		try {
			flightFromhistory = flightHistoryDao.getFlightsFromHistory();
		} catch (DaoException e) {
			logger.info(e.getMessage());
		}

		deletedFlights.removeAll(flightFromhistory);
		if (deletedFlights.size() == 0) {
			return null;
		} else {
			return deletedFlights;
		}
	}

	public List<Flight> getCompletedFlights() {
		// get current date time
		Date date = new Date();

		List<Flight> completedFlights = new ArrayList<>();
		List<Flight> flightFromhistory = new ArrayList<>();

		try {
			completedFlights = flightDao.getCompletedFlights(date);
		} catch (DaoException e) {
			logger.info(e.getMessage());
		}

		try {
			flightFromhistory = flightHistoryDao.getFlightsFromHistory();
		} catch (DaoException e) {
			logger.info(e.getMessage());
		}

		completedFlights.removeAll(flightFromhistory);
		if (completedFlights.size() == 0) {
			return null;
		} else {
			return completedFlights;
		}
	}

}
