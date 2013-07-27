package com.eventpool.order.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.module.DateCustomConverter;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@Transactional
@Component
public class InvoiceService {
    Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Resource
    DateCustomConverter customConverter;
    
    public void sendInvoice(Invoice invoice,String emailAddress) throws FileNotFoundException, IOException, DocumentException, Exception {
    }
    public ByteArrayOutputStream generateInvoice(Invoice invoice) throws FileNotFoundException, IOException, DocumentException, Exception {
     logger.info("Generating invoice for registrationId :"+invoice.getRegistrationId());
    	
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
        invoiceMap.put(prop.getProperty("event"), invoice.getEvent());
        invoiceMap.put(prop.getProperty("eventDate"), invoice.getEventDate());
        invoiceMap.put(prop.getProperty("eventId"), String.valueOf(invoice.getEventId()));
        invoiceMap.put(prop.getProperty("modeOfPayment"), invoice.getModeOfPayment());
        invoiceMap.put(prop.getProperty("organizerName"), invoice.getOrganizerName());
        invoiceMap.put(prop.getProperty("organizerContact"), invoice.getOrganizerContact());
        invoiceMap.put(prop.getProperty("attendee"), invoice.getAttendee());
        invoiceMap.put(prop.getProperty("ticketType"), invoice.getTicketType());
        invoiceMap.put(prop.getProperty("termsAndConditions"), invoice.getTermsAndConditions());
        invoiceMap.put(prop.getProperty("declarations"), invoice.getDeclarations());
        
        if(invoice.getTaxRate()!=null){
        invoiceMap.put(prop.getProperty("taxAmountLabel"), "Tax");
        invoiceMap.put(prop.getProperty("taxRate"), String.valueOf(invoice.getTaxRate()));
        invoiceMap.put(prop.getProperty("taxAmount"), String.valueOf(invoice.getTaxAmount()));
        }
        
        if(invoice.getDiscountAmount()!=null){
            invoiceMap.put(prop.getProperty("discountLabel"), "Tax");
            invoiceMap.put(prop.getProperty("discountAmount"), String.valueOf(invoice.getDiscountAmount()));
            }
        
        
        invoiceMap.put(prop.getProperty("registrationNo"), String.valueOf(invoice.getRegistrationId()));
        invoiceMap.put(prop.getProperty("registrationDate"), customConverter.convertFrom(invoice.getRegistrationDate()));
        invoiceMap.put(prop.getProperty("modeOfPayment"), invoice.getModeOfPayment());
        invoiceMap.put(prop.getProperty("orderNo"), String.valueOf(invoice.getOrderId()));
        invoiceMap.put(prop.getProperty("remarks"), invoice.getRemarks());
        invoiceMap.put(prop.getProperty("venue"),invoice.getVenue());
        invoiceMap.put(prop.getProperty("quantity"), String.valueOf(invoice.getQuantity()));
        invoiceMap.put(prop.getProperty("price"), String.valueOf(invoice.getPrice()));
        invoiceMap.put(prop.getProperty("totalPrice"), String.valueOf(invoice.getQuantity() *  invoice.getPrice()));
        invoiceMap.put(prop.getProperty("discountAmount"), String.valueOf(invoice.getDiscountAmount()));
        invoiceMap.put(prop.getProperty("totalAmount"), String.valueOf(invoice.getTotalAmount()));
        invoiceMap.put(prop.getProperty("amountinWords"), amountInWords);
        
        return invoiceMap;

    }
}

