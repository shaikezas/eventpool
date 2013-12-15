package com.eventpool.common;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventSettingsDTO;
import com.eventpool.common.dto.MediaDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.OrderFormSettings;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.UserEventSettingDTO;
import com.eventpool.common.dto.UserSettingJson;
import com.eventpool.common.entities.Address;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Media;
import com.eventpool.common.entities.Ticket;
import com.eventpool.common.entities.User;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.image.SaveImage;
import com.eventpool.common.module.CategoryTree;
import com.eventpool.common.module.CommonUtils;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.module.HtmlEmailService;
import com.eventpool.common.module.IPLocation;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.common.repositories.UserRepository;
import com.eventpool.common.type.CurrencyType;
import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;
import com.eventpool.common.type.OrderStatus;
import com.eventpool.common.type.TicketType;
import com.eventpool.event.command.PublishEventCommand;
import com.eventpool.event.command.SaveEventCommand;
import com.eventpool.event.module.EventApi;
import com.eventpool.event.service.EventCommandService;
import com.eventpool.event.service.impl.EventSettingsService;
import com.eventpool.event.service.impl.TicketAttendeeDTO;
import com.eventpool.event.service.impl.TicketBuyerDTO;
import com.eventpool.order.service.OrderService;
import com.eventpool.order.service.PaymentService;
import com.eventpool.web.controller.EventSearchRecord;
import com.eventpool.web.controller.EventService;
import com.eventpool.web.controller.SearchController;
import com.eventpool.web.controller.SearchQueryResponse;
import com.eventpool.web.controller.SearchService;
import com.eventpool.web.forms.EventForm;
import com.eventpool.web.forms.TicketForm;

public class EventTest extends BaseTest{
	
	@Resource
	EventRepository eventRepository;
	
	@Resource
	EventpoolMapper eventMapper;
	
	@Resource
	EventApi eventApi;

	@Resource
	EventService eventService;

	@Resource
	EventCommandService eventCommandService;
	
	@Resource
	SearchService searchService;
	
	@Resource
	EventSettingsService eventSettingsService;
	
    @Test
    @Transactional
    @Rollback(false)
    public void addEvent() {
    	Event event = new Event();
    	event.setContactDetails("contact details");
    	event.setCreatedBy(2L);
    	event.setCreatedDate(new Date());
    	event.setDescription("description");
    	event.setEndDate(new Date());
    	event.setEventType(EventType.FREE);
    	event.setInfoType(EventInfoType.BASIC);
    	event.setIsActive(true);
    	event.setModifiedDate(new Date());
    	event.setOrganizerDescription("org desc");
    	event.setOrganizerName("orgname");
    	event.setStartDate(new Date());
    	event.setStatus(EventStatus.OPEN);
    	event.setSubCategoryId(1);
    	event.setTermsAndConditions("terms and conditions");
    	event.setTitle("title");
    	Address venueAddress = new Address();
    	venueAddress.setAddress1("adr1");
    	venueAddress.setAddress2("addr2");
    	venueAddress.setCityId(1);
    	venueAddress.setFax("fax");
    	venueAddress.setMapUrl("mapUrl");
    	venueAddress.setMobileNumber("molenumbe");
    	venueAddress.setPhoneNumber("ph nber");
    	venueAddress.setZipCode("12345");
    	event.setVenueAddress(venueAddress);
    	event.setVenueName("venue name");
    	
    	Media mediaDTO = new Media();
    	mediaDTO.setBannerUrl("20130509_070251.jpg");
    	mediaDTO.setFaceBookUrl("facebook");
    	mediaDTO.setOtherUrl1("otherurl1");
    	event.setEventUrl("testurl");
    	
    	event.setMedia(mediaDTO);
    	
    	Ticket ticket = new Ticket();
    	ticket.setCreatedBy(0L);
    	ticket.setCreatedDate(new Date());
    	ticket.setDescription("descriptions");
    	ticket.setIsActive(true);
    	ticket.setMaxQty(10);
    	ticket.setMinQty(2);
    	ticket.setModifiedDate(new Date());
    	ticket.setName("name");
    	ticket.setPrice(2.3d);
    	ticket.setQuantity(100);
    	ticket.setSaleEnd(new Date());
    	ticket.setSaleStart(new Date());

    	List<Ticket> tickets = new ArrayList<Ticket>();
    	tickets.add(ticket);
    	event.setTickets(tickets);
    	eventRepository.save(event);
    }
    
    @Test
    @Transactional(readOnly=true)
    public void getEventDTO(){
    	Event event = eventRepository.findOne(1L);
    	EventDTO eventDTO = new EventDTO();
    	if(event!=null){
	    	eventMapper.mapEventDTO(event, eventDTO);
	    	log.info(eventDTO.toString());
    	}
    }

    @Resource
    IPLocation ipLocation; 
    
    @Test
    public void testIpLocation(){
    	
    	System.out.println(ipLocation.getCountryId("123.201.134.203"));
    	/*
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH,1);
		//cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		Date date = cal.getTime();
		System.out.println(date);*/
    }

    
    @Test
    @Transactional
    @Rollback(false)
    public void saveEventDTO(){
    	EventDTO event = getEventDTOObject();
    	eventApi.saveEventDTO(event);
    }

	private EventDTO getEventDTOObject() {
		EventDTO event = new EventDTO();
    	event.setContactDetails("contact details");
    	event.setCreatedBy(2L);
    	event.setCreatedDate(new Date());
    	event.setDescription("description");
    	event.setEndDate(new Date());
    	event.setEventType(EventType.FREE);
    	event.setInfoType(EventInfoType.BASIC);
    	event.setIsActive(true);
    	event.setModifiedDate(new Date());
    	event.setOrganizerDescription("org desc");
    	event.setOrganizerName("orgname");
    	event.setStartDate(new Date());
    	event.setStatus(EventStatus.OPEN);
    	event.setSubCategoryId(1);
    	event.setTermsAndConditions("terms and conditions");
    	event.setTitle("title");
    	AddressDTO venueAddress = new AddressDTO();
    	venueAddress.setAddress1("adr1");
    	venueAddress.setAddress2("addr2");
    	venueAddress.setCityId(1);
    	venueAddress.setFax("fax");
    	venueAddress.setMapUrl("mapUrl");
    	venueAddress.setMobileNumber("molenumbe");
    	venueAddress.setPhoneNumber("ph nber");
    	venueAddress.setZipCode("12345");
    	event.setVenueAddress(venueAddress);
    	event.setVenueName("venue name");
    	
    	TicketDTO ticket = new TicketDTO();
    	ticket.setCreatedBy(0L);
    	ticket.setCreatedDate(new Date());
    	ticket.setDescription("descriptions");
    	ticket.setIsActive(true);
    	ticket.setMaxQty(10);
    	ticket.setMinQty(2);
    	ticket.setModifiedDate(new Date());
    	ticket.setName("name");
    	ticket.setPrice(2.3d);
    	ticket.setQuantity(100);
    	ticket.setSaleEnd(new Date());
    	ticket.setSaleStart(new Date());

    	MediaDTO mediaDTO = new MediaDTO();
    	mediaDTO.setBannerUrl("20130509_070251.jpg");
    	mediaDTO.setFaceBookUrl("facebook");
    	mediaDTO.setOtherUrl1("otherurl1");
    	event.setEventUrl("eventUrl");
    	
    	event.setMedia(mediaDTO);
    	List<TicketDTO> tickets = new ArrayList<TicketDTO>();
    	tickets.add(ticket);
    	event.setTickets(tickets);
		return event;
	}
   
    @Resource
    private CommonUtils commonUtils;
   
    @Test
    public void timeZoneConversion(){
    	 Date gmtDate = commonUtils.getGMTDate(new Date());
    	 log.info("gmt date "+gmtDate);
    	 Date timeZoneDate = commonUtils.getTimeZoneDate(gmtDate, "IST");
    	 log.info(" local date"+timeZoneDate);
    }
    
   @Test
    public void testEventServiceCommand() throws Exception{
    	SaveEventCommand saveEventCommand = new SaveEventCommand();
    	saveEventCommand.setEventDTO(getEventDTOObject());
    	eventCommandService.executeCommand(saveEventCommand);
    }
    
    @Resource
    private EntityUtilities entityUtilities;
    
    @Test
    public void testCityMap(){
    //	Map<Integer, String> citiesWithStateAndCountry = entityUtilities.getCitiesWithStateAndCountry();
    //	for(String str:citiesWithStateAndCountry.values()){
    //		log.info(str);
    //	}
    	
    	int size = entityUtilities.getActiveCountryMap().size();
    	System.out.println(" Actvie Country Size "+size);
    }
    
    @Resource
    HtmlEmailService htmlEmailService;
    @Test
    public void sendMail(){
    	List<String> toList = new ArrayList<String>();
    	toList.add("ramuenugurthi@gmail.com");
    	
    	htmlEmailService.sendMail(null,"subject","body",toList,null);
    }
    
    @Resource
    SaveImage saveImage;
    
    @Test
    public void testImageSaveInLocal(){
    	
    	File file = new File("C:\\Event\\20130509_070251.jpg");
    	try {
			String saveInSourceLocation = saveImage.saveInSourceLocation(file);
			log.info(saveInSourceLocation);
		} catch (IOException e) {
			log.error("error in saving file",e);
		}
    }
   
    @Test
    public void testEventFormMap(){
    	EventForm eventForm  = new EventForm();
    	
    	eventForm.setAddress1("adr1");
    	eventForm.setAddress2("addr2");
    	eventForm.setBanner("banner");
    //	eventForm.setCityId(2);
    	eventForm.setContactDetails("contact details");
    	eventForm.setDescription("description");
    	eventForm.setEventUrl("event url");
    	eventForm.setFaceBookUrl("face book url");
    	eventForm.setIsActive(true);
    	eventForm.setFax("fax");
    	eventForm.setIsPrivate(true);
    	eventForm.setIsPublish(true);
    	eventForm.setKeyWords("key words");
    	eventForm.setMapUrl("map url");
    	//eventForm.setEventId(1L);
    	eventForm.setMobileNumber("1234567890");
    	eventForm.setOrganizerDescription("organizer Desciption");
    	eventForm.setOrganizerName("organizer name");
    	eventForm.setOtherUrl1("other url1");
    	eventForm.setOtherUrl2("other url2");
    	eventForm.setPhoneNumber("1234567890");
    	eventForm.setPrivacyType("PRIVATE");
    	eventForm.setPublishDate(new Date());
    	eventForm.setShowEvent(true);
    	eventForm.setStartDate("05-MAY-2013 11:11");
    	eventForm.setSubCategoryId(2L);
    	eventForm.setTermsAndConditions("terms and conditions");
    	eventForm.setTitle("title");
    	eventForm.setVenueName("venue name");
    	eventForm.setVideoUrl("video url");
    	eventForm.setIsWebinar(false);
    	eventForm.setZipCode("304567");
    	eventForm.setEventWebSiteUrl("eventwebsiteurl");
    	
    	TicketForm ticketForm = new TicketForm();
    	ticketForm.setDescription("description");
    	ticketForm.setIsActive(false);
    	ticketForm.setMaxQty(40);
    	ticketForm.setMinQty(20);
    	ticketForm.setName("name");
    	ticketForm.setPrice(12.3d);
    	ticketForm.setQuantity(123);
    	ticketForm.setSaleEnd("05-MAY-2013 11:11");
    	ticketForm.setSaleStart("05-MAY-2011 11:11");
    	ticketForm.setShowsettings(true);
    	ticketForm.setTicketOrder(3);
    	
    	OrderFormSettings orderFormSettings = new OrderFormSettings();
    	orderFormSettings.setAttendType("buyer");
    	Map<String, UserSettingJson> userSettings = new HashMap<String, UserSettingJson>();
    	UserSettingJson userSettingsObject = new UserSettingJson();
    	Map<String, Boolean> fname = new HashMap<String, Boolean>();
    	fname.put("include", true);
    	fname.put("required",true);
		userSettingsObject.setFname(fname );
    	
		userSettings.put("free",userSettingsObject );
		orderFormSettings.setUserSettings(userSettings );
    	
		//eventForm.setOrderFormSettings(orderFormSettings );
    	
    	List<TicketForm> ticketForms = new ArrayList<TicketForm>();
    	eventForm.setTickets(ticketForms);
    	ticketForms.add(ticketForm);
    	
    	EventDTO eventDTO = new EventDTO();
    	eventDTO.setEventSettingsDTO(new EventSettingsDTO());
		eventMapper.mapEventDTO(eventForm, eventDTO );
		
		//hard coded the following
		eventDTO.setCreatedBy(1L);
		eventDTO.setEventType(EventType.FREE);
		for(TicketDTO ticketDTO:eventDTO.getTickets()){
			ticketDTO.setCreatedBy(2L);
		}
		
		
		eventApi.saveEventDTO(eventDTO);
		
    }
    
    @Test
    public void testBasicEvent(){
    	EventForm eventForm  = new EventForm();
        eventForm.setTitle("title");
        eventForm.setStartDate("05-MAY-2013 11:11");
        eventForm.setEndDate("05-MAY-2013 11:11");
        eventForm.setOrganizerName("organizer name");
        eventForm.setEventUrl("eventurl1");
        EventDTO eventDTO = new EventDTO();
		eventMapper.mapEventDTO(eventForm, eventDTO );
		
		//hard coded the following
		eventDTO.setCreatedBy(1L);
		//eventDTO.setEventType(EventType.FREE);
		eventApi.saveEventDTO(eventDTO);
    }

    @Test
    public void getEventForm(){
    	try {
			EventDTO eventDTO = eventApi.getEvenDTO(28L);
			EventForm eventForm = new EventForm();
			eventMapper.mapEventForm(eventDTO, eventForm );
	
			log.info(eventForm.getAddress1());
			log.info(eventForm.getAddress2());
			log.info(eventForm.getBanner());
			log.info(eventForm.getContactDetails());
			log.info(eventForm.getDescription());
			log.info(eventForm.getEndDate());
			log.info(eventForm.getEventUrl());
			log.info(eventForm.getFaceBookUrl());
			log.info(eventForm.getFax());
			log.info(eventForm.getKeyWords());
			log.info(eventForm.getMapUrl());
			log.info(eventForm.getMobileNumber());
			log.info(eventForm.getOrganizerDescription());
			log.info(eventForm.getOrganizerName());
			log.info(eventForm.getOtherUrl1());
			log.info(eventForm.getOtherUrl2());
		//	log.info(eventForm.getOrganizerLogo());
			log.info(eventForm.getPhoneNumber());
			log.info(eventForm.getPrivacyType());
			log.info(eventForm.getPublishDate().toString());
			log.info(eventForm.getStartDate());
		//	log.info(eventForm.getSelectedformid().toString());
			log.info(eventForm.getSubCategoryId().toString());
			log.info(eventForm.getTermsAndConditions());
			log.info(eventForm.getTitle());
			log.info(eventForm.getVenueName());
			log.info(eventForm.getVideoUrl());
			log.info(eventForm.getZipCode().toString());
			
		} catch (EventNotFoundException e) {
			log.error("Event not found",e);
		}
    }
    
    @Resource
    CategoryTree categoryTree;
    
    @Test
    public void  testCategoryTree(){
    	log.info(categoryTree.toString());
    	
    }
    
    
    @Test
    public void  testGetAllEvents() throws Exception{
    	List<EventDTO> allEvents = eventApi.getAllEvents(1L);
    	log.info(allEvents.size()+" size ");
    }
    
    @Test
    public void testPublishCommand() throws Exception{
    	PublishEventCommand publishEventCommand = new PublishEventCommand();
    	publishEventCommand.setEventId(3L);
    	publishEventCommand.setPublish(true);
    	//publishEventCommand.setEventUrl("event url");
    	eventCommandService.executeCommand(publishEventCommand);
    }
    
    @Test
    public void  testGetAllEventsByStatus() throws Exception{
    	List<EventDTO> allEvents = eventApi.getAllEvents(EventStatus.OPEN);
    	log.info(allEvents.size()+" size ");
    }
    
	@Resource
	OrderService orderService;
    
    @Test
    @Rollback(false)
    public void  testOrder() throws Exception{
    	
    	SuborderDTO suborderDTO =new SuborderDTO();
    	suborderDTO.setCreatedBy(1L);
    	suborderDTO.setCreatedDate(new Date());
    	suborderDTO.setDicountCoupon("discount coupon");
    	suborderDTO.setDiscountAmount(123.00);
    	suborderDTO.setGrossAmount(234.00);
    	suborderDTO.setModifiedDate(new Date());
    	suborderDTO.setNetAmount(345.34);
    	suborderDTO.setOrganizerName("organizername");
    	TicketDTO ticket = new TicketDTO();
		suborderDTO.setTicket(ticket);
    	suborderDTO.getTicket().setId(1L);
    	suborderDTO.getTicket().setQuantity(12);
    	suborderDTO.setStatus(OrderStatus.CANCELLED);
    	suborderDTO.setSubCategoryId(1);
    	suborderDTO.setTicketRegisterId(1L);
    	suborderDTO.getTicket().setEventId(1L);
    	ticket.setCreatedBy(1L);
    	ticket.setCreatedDate(new Date());
    	ticket.setDescription("description");
    	ticket.setTicketType(TicketType.FREE);
    	ticket.setName("name");
    	ticket.setPrice(123.00);
    	
    	OrderDTO orderDTO = new  OrderDTO();
    	orderDTO.setCreatedBy(1L);
    	orderDTO.setCreatedDate(new Date());
    	orderDTO.setDicountCoupon("discount coupon");
    	orderDTO.setEmail("ramuenugurthi@gmail.com");
    	orderDTO.setGrossAmount(234.00);
    	orderDTO.setLastName("lastname");
    	orderDTO.setFirstName("firstname");
    	orderDTO.setModifiedDate(new Date());
    	orderDTO.setNetAmount(234.00);
    	orderDTO.setPaymentCurrency(CurrencyType.USD);
    	List<SuborderDTO> suborders  = new ArrayList<SuborderDTO>();
    	suborders.add(suborderDTO);
		orderDTO.setSuborders(suborders);
		
		AddressDTO billingAddress = new AddressDTO();
		orderDTO.setBillingAddress(billingAddress);
		billingAddress.setAddress1("addr1");
		billingAddress.setAddress2("addr2");
		billingAddress.setCityId(23);
		billingAddress.setFax("fax");
		billingAddress.setMapUrl("mapurl");
		billingAddress.setMobileNumber("123455678");
		billingAddress.setPhoneNumber("98765432");
		billingAddress.setZipCode("12345");
		orderService.createOrder(orderDTO);
    }  
    
    @Test
    @Transactional
    public void testMytickets(){
    	List<SuborderDTO> orderedTickets = eventApi.getOrderedTickets(0L);
    	log.info(orderedTickets.size()+" size");
    }
    
    @Test
    public void testSearchService() throws Exception{
    	SearchQueryResponse searchQueryResponse = searchService.getSearchQueryResponse("title", null,10, 0,null);
    	log.info(searchQueryResponse+" size");
    }
    
    @Test
    public void testEventOrderedTickets() throws Exception{
    	List<SuborderDTO> suborderDTOs = eventApi.getEventOrderedTickets(1L);
    	log.info(suborderDTOs.size()+" size");
    }
    
    @Test
    public void testViewAttendes(){
    	TicketAttendeeDTO attendes = eventSettingsService.getAttendes(4L);
    	log.info(attendes.getAttendeeListMap()+" ");
    }
    
    @Test
    public void testViewBuyers(){
    	TicketBuyerDTO buyers = eventSettingsService.getBuyers(4L);
    	log.info(buyers.getUserDTOs()+" ");
    }
    
    @Test
    public void testUserEventSettingJson(){
    	UserEventSettingDTO userEventSettings = eventSettingsService.getUserEventSettings("{showOrgName:true,showOrgDesc:true,contactDetails:true,showHostWebsite:true,showAttendeDetails:true}");
    	log.info(userEventSettings.toString());
    }

    @Resource
    private UserRepository userRepository;
    
    @Test
    @Transactional(readOnly=true)
    public void testUser(){
    	User user = userRepository.findOne(1L);
    	log.info("user"+user.getMemberShip());
    }
    
    @Test
    public void push(){
    	for(long i=1;i<61;i++){
    		eventService.pushToQueue(i);
    	}
    }

    @Resource
    SearchController searchController;
    
    @Test
    public void homePageResults() throws Exception{
    	SearchQueryResponse homepageResults = searchController.getHomepageResults(null);
    	System.out.println(homepageResults.getEventSearchRecords().size());
    }

    @Test
    public void searchTest() throws Exception{
    	SearchQueryResponse searchQueryResponse = searchService.getSearchQueryResponse(null, new HashMap<String, String>(), 0, 0,null);
    	System.out.println("Search Results "+searchQueryResponse.getEventSearchRecords().size());
    }

    @Test
    public void testDeleted() throws Exception{
    	List<Ticket> eventTickets = eventRepository.getEventTickets(21L);
    	System.out.println(eventTickets.size());
    }

    @Resource
    PaymentService paymentService;
    
    @Test
    public void testPaymentService(){
    	String initPayment = paymentService.initPayment(null);
    	System.out.println(initPayment);
    }
}
