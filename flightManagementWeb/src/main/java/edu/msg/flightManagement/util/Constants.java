package edu.msg.flightManagement.util;

public class Constants {

	public static final String USER_TYPE = "userType";
	public static final String USER_NAME = "userName";

	public static final String CREW = "CREW";
	public static final String ADMINISTRATOR = "ADMINISTRATOR";
	public static final String COMPANY_MANAGER = "COMPANY MANAGER";
	public static final String FLIGHT_MANAGER = "FLIGHT MANAGER";

	public static final String ADMINISTRATOR_L = "administrator";
	public static final String COMPANY_MANAGER_L = "company manager";
	public static final String CREW_L = "crew";
	public static final String FLIGHT_MANAGER_L = "flight manager";

	public static final String ERROR = "error.error";
	public static final String OK = "ok";
	public static final String OKCAPS = "OK";
	public static final String INFO = "INFO";
	public static final String WARNING = "warning.warning";
	public static final String CREWADDED = "info.crewAdded";
	public static final String CREWREMOVED = "info.crewRemoved";
	public static final String LOGGEDOUT = "loggedOut";
	public static final String INSERTED = "inserted";
	public static final String PAGENOTFOUND = "Page not round: ";
	public static final String MATCH = "match";
	public static final String DIFFER = "differ";
	public static final String EMPTY = "empty";
	public static final String SAVED = "saved";

	public static final String INVALIDCREDENTIALS = "error.invalidCredentials";

	public static final String LOGGEDIN = "info.loggedIn";

	public static final String EMPTYDEPARTURESTRING = "error.emptyDepartureField";
	public static final String DATESMUSTBEINTHEFUTURE = "error.datesMustBeInTheFuture";
	public static final String SAMEAIRPORT = "error.sameAirport";

	public static final String DELETEDFLIGHTS = "info.deletedFlight";
	public static final String EMPTYARRIVALAIRPORT = "error.emptyArrivalAirport";
	public static final String ENDDATEMUSTBEGREATER = "error.endDateMustBeGreater";
	public static final String UNSELECTEDDATES = "error.unselectedDates";
	public static final String EMPTYSTARTDATE = "error.unselectedDates";
	public static final String EMPTYENDDATE = "error.emptyEndDate";
	public static final String EMPTYCOMPANY = "error.emptyCompany";
	public static final String EMPTYPLANE = "error.emptyPlane";

	public static final String ITINERARYNAMEREQUIRED = "error.itineraryNameRequired";
	public static final String ITINERARYNAMETOOSHORT = "error.itineraryNameTooShort";
	public static final String ITINERARYNAMETOOLONG = "error.itineraryNameTooLong";
	public static final String ITINERARYEXISTS = "error.itineraryAlreadyExists";

	public static final String AIRPORTNULL = "error.airportNull";
	public static final String AIRPORTEXISTS = "error.airportExists";

	public static final String WRONGPASS = "error.passwordsNotMatch";
	public static final String INVALIDPASS = "error.invalidPassword";
	public static final String USERNAMEEXISTS = "error.usernameExists";

	public static final String INSERTEDFLIGHT = "info.insertedFlight";
	public static final String EDITEDFLIGHT = "info.editedFlight";
	public static final String EDITCANCELLED = "info.editCancelled";
	public static final String DATESSELECTED = "info.dateSelected";
	public static final String INSERTFLIGHTFROMTEMPLATE = "info.insertFlightFromTemplate";
	public static final String EDITFLIGHTFROMTEMPLATE = "info.flightFromTemplateCancel";

	public static final String USERINSERTED = "user.inserted";
	public static final String USEREDITED = "info.userEdited";
	public static final String USEREDITCANCEL = "info.userEditCancelled";

	public static final String AIRPORTINSERT = "info.airportInserted";
	public static final String AIRPORTEDIT = "info.airportEdited";
	public static final String AIRPORTEDITCANCELLED = "info.airportEditCancelled";
	public static final String AIRPORTDELETED = "info.airportDelete";

	public static final String PLANEEDIT = "info.planeEdited";
	public static final String PLANEEDITCANCELLED = "info.planeEditCancelled";
	public static final String PLANEINSERTED = "info.planeInserted";
	public static final String PLANEMODELEXISTS = "info.planeModelExists";

	public static final String FLIGHTADDEDTOITINERARY = "info.flightAddedToItinerary";
	public static final String ITINERARYDELETED = "info.itineraryDeleted";
	public static final String ITINERARYINSERTED = "info.itineraryInserted";
	public static final String ITINERARYEDIT = "info.itineraryEdited";
	public static final String ITINERARYEDITCANCEL = "info.itineraryEditCancelled";

	public static final String COMPANYINSERTED = "info.companyInserted";
	public static final String COMPANYEXISTS = "managecompanies.companyExist";
	public static final String COMPANYEDIT = "info.companyEdit";
	public static final String COMPANYEDITCANCELLED = "info.companyEditCancelled";

	public static final String DATAUPDATED = "info.personalDataUpdated";

	public static final String UPLOADSUCCES = "info.uploadSuccess";
	public static final String UPLOADPROGRESS = "info.uploadProgress";

	public static final String REPORTSSTARTDATE = "error.startDateRequired";
	public static final String REPORTSENDDATE = "error.endDateRequired";
	public static final String REPORTSDATECOMPARE = "error.datesCompare";

	public static final String TEMPLATECREATED = "info.templateCreated";
	public static final String TEMPLATEEDITED = "info.templateEdited";
	public static final String TEMPLATEDELETED = "info.templateDeleted";
	public static final String TEMPLATEEDITCANCELLED = "info.templateEditCancelled";
	public static final String THIS_PLANE = "info.theSelectedPlane";
	public static final String ALREADY_HAS = "info.alreadyHas";

	// Exception handling

	public static final String ERROR_GETAVAILABLECOMPANIES = "error.getCompanies";
	public static final String ERROR_FLIGHTSANDUSERS = "error.getFlightAndUsers";
	public static final String ERROR_FLIGHTSANDUSERSASSIGNEMENT = "error.FlightAndUsersAssignement";
	public static final String ERROR_GETFLIGHTHISTORY = "error.getFlightHistories";
	public static final String ERROR_GETFLIGHTS = "error.getFlights";
	public static final String ERROR_GETPLANES = "error.getPlanes";
	public static final String ERROR_GETTEMPLATES = "error.getTemplateList";
	public static final String ERROR_ADDTEMPLATE = "error.addTemplate";
	public static final String ERROR_UPDATETEMPLATE = "error.updateTemplate";
	public static final String ERROR_DELETETEMPLATE = "error.deleteTemplate";
	public static final String ERROR_GETITINERARIES = "error.getItineraries";
	public static final String ERROR_UPDATEITINERARY = "error.updateItinerary";
	public static final String ERROR_DELETEITINERARY = "error.deleteItinerary";
	public static final String ERROR_ADDITINERARY = "error.addItinerary";
	public static final String ERROR_VALIDATELOGIN = "error.validateLogin";
	public static final String ERROR_GETAIRPORTS = "error.getAirports";
	public static final String ERROR_DELETEAIRPORT = "error.deleteAirport";
	public static final String ERROR_ADDAIRPORT = "error.addAirport";
	public static final String ERROR_UPDATEAIRPORT = "error.updateAirport";
	public static final String ERROR_UPDATECOMPANY = "error.updateCompany";
	public static final String ERROR_ADDCOMPANY = "error.addCompany";
	public static final String ERROR_UPDATEUSER = "error.updateUser";
	public static final String ERROR_GETUSER = "error.getUser";
	public static final String ERROR_ADDPLANE = "error.insertPlane";
	public static final String ERROR_UPDATEPLANE = "error.updatePlane";
	public static final String ERROR_GETUSERS = "error.getUsers";
	public static final String ERROR_ADDUSER = "error.addUser";
	public static final String ERROR_ADDFLIGHT = "error.addFlight";
	public static final String ERROR_UPDATEFLIGHT = "error.updateFlight";
	public static final String ERROR_DELETEFLIGHT = "error.deleteFlight";

	public static final String ERROR_PHONESHORT = "managecompanies.phoneshort";
	public static final String ERROR_PHONELONG = "managecompanies.phoneLong";
	public static final String ERROR_PHONEREQUIRED = "managecompanies.phoneRequired";
	public static final String ERROR_PHONEVALID = "managecompanies.phoneValid";
	public static final String ERROR_EMAILVALID = "managecompanies.emailValid";
	public static final String ERROR_COMPANYNAMEEMPTY = "managecompanies.companyNameEmpty";
	public static final String ERROR_ADDRESSEMPTY = "managecompanies.addressEmpty";
	public static final String ERROR_ADDRESSHORT = "managecompanies.addressShort";
	public static final String ERROR_ADDRESSLONG = "managecompanies.addressLong";
	public static final String ERROR_EMPTYCOUNTRY = "error.emptyCountry";
	public static final String ERROR_EMPTYCITY = "error.emptyCity";
	public static final String ERROR_EMPTYADDRESS = "error.emptyAddress";

	public static final String ERROR_ADDRESS_TOO_SHORT = "error.addressTooShort";
	public static final String ERROR_ADDRESS_TOO_LONG = "error.addressTooLong";
	public static final String ERROR_ADDRESS_REQUIRED = "error.addressRequired";

	public static final String ERROR_CITY_TOO_SHORT = "error.cityTooShort";
	public static final String ERROR_CITY_TOO_LONG = "error.cityTooLong";
	public static final String ERROR_CITY_REQUIRED = "error.cityRequired";

	public static final String ERROR_COMPANY_TOO_SHORT = "error.companyNameTooShort";
	public static final String ERROR_COMPANY_TOO_LONG = "error.companyNameTooLong";
	public static final String ERROR_COMPANY_REQUIRED = "error.companyNameRequired";

	public static final String ERROR_COUNTRY_TOO_SHORT = "error.companyNameTooShort";
	public static final String ERROR_COUNTRY_TOO_LONG = "error.companyNameTooLong";
	public static final String ERROR_COUNTRY_REQUIRED = "error.companyNameRequired";

	public static final String ERROR_EMAIL_REQUIRED = "error.emailRequired";
	public static final String ERROR_EMAIL_INVALID = "error.emailNotValid";

	public static final String ERROR_FNAME_REQUIRED = "error.fnameRequired";
	public static final String ERROR_FNAME_TOO_SHORT = "error.fnameTooLong";
	public static final String ERROR_FNAME_TOO_LONG = "error.fnameTooShort";

	public static final String ERROR_LNAME_REQUIRED = "error.lnameRequired";
	public static final String ERROR_LNAME_TOO_SHORT = "error.lnameTooLong";
	public static final String ERROR_LNAME_TOO_LONG = "error.lnameTooShort";

	public static final String ERROR_PWORD_TOO_SHORT = "error.pwordTooShort";
	public static final String ERROR_PWORD_TOO_LONG = "error.pwordTooLong";
	public static final String ERROR_PWORD_REQUIRED = "error.pwordRequired";

	public static final String ERROR_PHONE_TOO_SHORT = "error.phoneTooShort";
	public static final String ERROR_PHONE_REQUIRED = "error.phoneRequired";
	public static final String ERROR_PHONE_INVALID = "error.phoneInvalid";

	public static final String ERROR_CAPACITY_NO = "error.positiveNo";
	public static final String ERROR_CAPACITY_NAN = "error.capacityNaN";

	public static final String PLANE_MODEL_REQUIRED = "error.modelRequired";
	public static final String PLANE_MODEL_TOO_SHORT = "error.modelNameTooShort";
	public static final String PLANE_MODEL_TOO_LONG = "error.modelNameTooLong";

	public static final String ERROR_USERNAME_REQUIRED = "error.usernameRequired";
	public static final String ERROR_USERNAME_TOO_LONG = "error.usernameTooLong";
	public static final String ERROR_USERNAME_TOO_SHORT = "error.usernameTooShort";

	public static final String ERROR_STARTDATE_NULL = "error.startDateNull";
	public static final String ERROR_ENDDATE_NULL = "error.endDateNull";
	public static final String ERROR_DATE_FUTURE = "error.dateFuture";

	public static final String ERROR_EMPTYMODEL = "error.emptyModel";
	public static final String ERROR_EMPTYCAPACITY = "error.emptyCapacity";
	public static final String ERROR_EMPTYCOMPANY = "error.emptyCompany";

	public static final String ERROR_SHORTCAPACITY = "error.shortCapacity";
	public static final String ERROR_LONGCAPACITY = "error.longCapacity";
	public static final String ERROR_INVALIDCAPACITY = "error.invalidCapacity";
	public static final String ERROR_WRONGFILE = "error.wrongFile";
	public static final String ERROR_TABLENOTSELECTED = "error.tableNotSelected";
	public static final String ERROR_USEREXISTS = "error.userExists";
	public static final String ERROR_IMPORT = "error.importError";

}
