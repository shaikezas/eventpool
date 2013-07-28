package com.eventpool.order.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.InvoiceDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Invoice;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.TicketSnapShot;
import com.eventpool.common.module.DateCustomConverter;
import com.eventpool.common.module.EmailAttachment;
import com.eventpool.common.module.HtmlEmailService;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.common.repositories.InvoiceRepository;
import com.eventpool.common.repositories.SuborderRepository;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@Transactional
@Component
public class InvoiceService {
    Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Resource
    DateCustomConverter customConverter;
    
    @Autowired
    SuborderRepository suborderRepository;
    
    @Autowired
    InvoiceRepository invoiceRepository;
    
    @Autowired
    EventRepository eventRepository;
    
    @Resource
    HtmlEmailService htmlEmailService;
    
    public Boolean sendInvoice(Invoice invoice) throws FileNotFoundException, IOException, DocumentException, Exception {
    	logger.info("Sending mail to :"+invoice.getBuyerMail());
    	ByteArrayOutputStream invoiceAttachemnt = generateInvoice(invoice);
    	String buyerMail = invoice.getBuyerMail();
    	List<String> toList = new ArrayList<String>();
    	toList.add(buyerMail);
    	EmailAttachment attachment = new EmailAttachment();
    	attachment.setAttachment(invoiceAttachemnt);
    	attachment.setAttachmentName(invoice.getEventName()+"-"+invoice.getId()+".pdf");
    	return htmlEmailService.sendMail(toList,"Invoice","Please find the attached invoice",null,attachment);
    	
    }
    public ByteArrayOutputStream generateInvoice(Invoice invoice) throws FileNotFoundException, IOException, DocumentException, Exception {
     logger.info("Generating invoice for registrationId :"+invoice.getId());
    	
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            Properties prop = new Properties();
            prop.load(InvoiceService.class.getResourceAsStream("/invoice.properties"));
            is = InvoiceService.class.getResourceAsStream("/invoice.pdf");
            PdfReader pdfTemplate = new PdfReader(is);
            
            PdfStamper stamper = new PdfStamper(pdfTemplate, out);
            stamper.setFormFlattening(true);

            Map<String, String> invoiceMap = assignValuesToMap(prop, invoice);

            for (String key : invoiceMap.keySet()) {
                stamper.getAcroFields().setField(key, invoiceMap.get(key));
            }

            stamper.close();
            pdfTemplate.close();
        } catch (Exception e) {

        }finally{
            if(is!=null) is.close();
        }
        
        return out;

    }

    private Map<String, String> assignValuesToMap(Properties prop, Invoice invoice) {
        LangUtils langUtils = new LangUtils();
       String amountInWords = langUtils.convertNumToWord(invoice.getTotalAmount().intValue());
        Map<String, String> invoiceMap = new HashMap<String, String>();
        invoiceMap.put(prop.getProperty("event"), invoice.getEventName());
        invoiceMap.put(prop.getProperty("eventDate"), customConverter.convertFrom(invoice.getEventDate()));
        invoiceMap.put(prop.getProperty("eventId"), String.valueOf(invoice.getEventId()));
        invoiceMap.put(prop.getProperty("organizerName"), invoice.getOrganizerName());
        invoiceMap.put(prop.getProperty("organizerContact"), invoice.getOrganizerContact());
        invoiceMap.put(prop.getProperty("buyerName"), invoice.getBuyer());
        invoiceMap.put(prop.getProperty("ticketType"), invoice.getTicketType().toString());
        invoiceMap.put(prop.getProperty("termsAndConditions"), invoice.getTermsAndConditions());
        
        if(invoice.getDiscountAmount()!=null && invoice.getDiscountAmount()> 0){
            invoiceMap.put(prop.getProperty("discountLabel"), "Dicount Amount");
            invoiceMap.put(prop.getProperty("discountAmount"), String.valueOf(invoice.getDiscountAmount()));
            }
        
        
        invoiceMap.put(prop.getProperty("registrationNo"), String.valueOf(invoice.getId()));
        invoiceMap.put(prop.getProperty("registrationDate"), customConverter.convertFrom(invoice.getCreatedDate()));
        invoiceMap.put(prop.getProperty("orderNo"), String.valueOf(invoice.getOrderId()));
        invoiceMap.put(prop.getProperty("suborderNo"), String.valueOf(invoice.getSuborderId()));
        invoiceMap.put(prop.getProperty("remarks"), invoice.getRemarks());
        invoiceMap.put(prop.getProperty("venue"),invoice.getVenue());
        invoiceMap.put(prop.getProperty("quantity"), String.valueOf(invoice.getQuantity()));
        invoiceMap.put(prop.getProperty("price"), String.valueOf(invoice.getPrice()));
        invoiceMap.put(prop.getProperty("totalPrice"), String.valueOf(invoice.getQuantity() *  invoice.getPrice()));
       	invoiceMap.put(prop.getProperty("totalAmount"), String.valueOf(invoice.getTotalAmount()));
        invoiceMap.put(prop.getProperty("amountinWords"), amountInWords);
        
        return invoiceMap;

    }
    
    
    public void generateInvoice(Suborder suborder) throws FileNotFoundException, IOException, DocumentException, Exception{
    	Invoice invoice = convertToInvoice(suborder);
    	invoiceRepository.save(invoice);
    	sendInvoice(invoice);
    }
    
    public InvoiceDTO viewInvoice(Long suborderId){
    	Invoice invoice = invoiceRepository.findBySuborderId(suborderId);
    	InvoiceDTO dto = convertToDTO(invoice);
    	return dto;
    }
    
    public Boolean sendInvoiceToMail(Long suborderId) throws FileNotFoundException, IOException, DocumentException, Exception{
    	Invoice invoice = invoiceRepository.findBySuborderId(suborderId);
    	return sendInvoice(invoice);
    }
    
    private InvoiceDTO convertToDTO(Invoice invoice){
    	InvoiceDTO dto = new InvoiceDTO();
    	dto.setBuyer(invoice.getBuyer());
    	dto.setCreatedDate(customConverter.convertFrom(invoice.getCreatedDate()));
    	dto.setDiscountAmount(invoice.getDiscountAmount());
    	dto.setEventDate(customConverter.convertFrom(invoice.getEventDate()));
    	dto.setEventId(invoice.getEventId());
    	dto.setEventName(invoice.getEventName());
    	dto.setId(invoice.getId());
    	dto.setOrderId(invoice.getOrderId());
    	dto.setOrganizerContact(invoice.getOrganizerContact());
    	dto.setOrganizerName(invoice.getOrganizerName());
    	dto.setPrice(invoice.getPrice());
    	dto.setQuantity(invoice.getQuantity());
    	dto.setRemarks(invoice.getRemarks());
    	dto.setSuborderId(invoice.getSuborderId());
    	dto.setTermsAndConditions(invoice.getTermsAndConditions());
    	dto.setTicketNmae(invoice.getTicketName());
    	dto.setTicketType(invoice.getTicketType().toString());
    	dto.setTotalAmount(invoice.getTotalAmount());
    	dto.setTotalPrice(invoice.getTotalPrice());
    	dto.setVenue(invoice.getVenue());
    	return dto;
    }
    
    private Invoice convertToInvoice(Suborder suborder){
    	Invoice invoice = new Invoice();
    	 TicketSnapShot ticket = suborder.getTicketSnapShot();
    	Event event = eventRepository.findOne(ticket.getEventId()); 
    	
    	invoice.setBuyer(suborder.getOrder().getFirstName()+suborder.getOrder().getLastName());
    	invoice.setBuyerMail(suborder.getOrder().getEmail());
    	invoice.setDiscountAmount(suborder.getDiscountAmount());
		invoice.setEventName(event.getTitle());
    	invoice.setEventDate(event.getStartDate());
    	invoice.setEventId(event.getId());
    	invoice.setOrderId(suborder.getOrder().getId());
    	invoice.setOrganizerContact(event.getContactDetails());
    	invoice.setOrganizerName(event.getOrganizerName());
    	invoice.setPrice(suborder.getTicketPrice());
    	invoice.setQuantity(ticket.getQuantity());
    	invoice.setTotalAmount(suborder.getNetAmount());
    	invoice.setTotalPrice(suborder.getGrossAmount());
    	invoice.setVenue(event.getVenueAddress().getAddress1());
    	invoice.setSuborderId(suborder.getId());
    	invoice.setTicketName(suborder.getTicketName());
    	invoice.setTicketType(ticket.getTicketType());
    	invoice.setCreatedBy(suborder.getCreatedBy());
    	return invoice;
    }
}

