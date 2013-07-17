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

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@Transactional
@Component
public class InvoiceService {
    Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    public String sendInvoice(Invoice invoice,String emailAddress) throws FileNotFoundException, IOException, DocumentException, Exception {
      
        String successmsg = "";
        InputStream is = null;
        try {
            Properties prop = new Properties();
            prop.load(InvoiceService.class.getResourceAsStream("/invoice.properties"));
            is = InvoiceService.class.getResourceAsStream("/invoice.pdf");
            PdfReader pdfTemplate = new PdfReader(is);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(pdfTemplate, out);
            stamper.setFormFlattening(true);

            Map<String, String> invoiceMap = assignValuesToMap(prop, invoice);

            for (String key : invoiceMap.keySet()) {
                stamper.getAcroFields().setField(key, invoiceMap.get(key));
            }

            stamper.close();
            pdfTemplate.close();
            logger.info("ByteArrayOutputStream size " + out.size());
//            MailUtil mailUtil = new MailUtil();
//            successmsg = mailUtil.sendInvoiceMail(emailAddress, out,invoice.getId().toString());
           
        } catch (Exception e) {

        }finally{
            if(is!=null) is.close();
        }
        logger.info("sendInvoice "+successmsg);
        
        return successmsg;

    }

    private Map<String, String> assignValuesToMap(Properties prop, Invoice invoice) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        LangUtils langUtils = new LangUtils();
       String amountInWords = langUtils.convertNumToWord(invoice.getItemInfo().getAmount().intValue());
        Map<String, String> invoiceMap = new HashMap<String, String>();
        invoiceMap.put(prop.getProperty("legalName"), invoice.getSellerInfo().getLegalName() + "\n" + invoice.getReversePickup());
        invoiceMap.put(prop.getProperty("invoiceNumber"), invoice.getInvoiceNumber());
        invoiceMap.put(prop.getProperty("invoiceDate"), dateFormatter.format(invoice.getInvoiceDate()));
        invoiceMap.put(prop.getProperty("modeOfPayment"), invoice.getPaymentDescription());
        invoiceMap.put(prop.getProperty("orderNo"), invoice.getId().toString());
        invoiceMap.put(prop.getProperty("suborderNo"), invoice.getOrderId().toString());
        invoiceMap.put(prop.getProperty("orderDate"), dateFormatter.format(invoice.getOrderDate()));
        if(invoice.getImei()!=null && !invoice.getImei().equals(""))
        {
            invoiceMap.put(prop.getProperty("remarks"), invoice.getRemarks()+" , IMEI number : "+invoice.getImei());
        }
        else
        {
            invoiceMap.put(prop.getProperty("remarks"), invoice.getRemarks());
        }
        String name = "";
        
        if(invoice.getBuyer().getMiddleName() != null){
            name = invoice.getBuyer().getFirstName() + " "+invoice.getBuyer().getMiddleName() + " "
                     + invoice.getBuyer().getLastName();
        } else {
            name = invoice.getBuyer().getFirstName() + " "
                    + invoice.getBuyer().getLastName();
        }
        invoiceMap.put(prop.getProperty("address"), " Buyer: \n " + name + "\n " + invoice.getBuyer().getAddress() + " \n " + invoice.getBuyer().getCity() + "\n" + invoice.getBuyer().getCountry() + "\n" + invoice.getBuyer().getZip());
        invoiceMap.put(prop.getProperty("documentNo"), invoice.getDespatchInfo().getDocumentNumber());
        if(invoice.getDespatchInfo().getDate()!=null)
        {
        invoiceMap.put(prop.getProperty("shippmentDate"),dateFormatter.format(invoice.getDespatchInfo().getDate()));
        }
        invoiceMap.put(prop.getProperty("courierName"), invoice.getDespatchInfo().getCourier());
        invoiceMap.put(prop.getProperty("destination"), invoice.getDespatchInfo().getDestination());
        invoiceMap.put(prop.getProperty("description"), invoice.getItemInfo().getDescription());
        invoiceMap.put(prop.getProperty("quantity"), new Integer(invoice.getItemInfo().getQuantity()).toString());
       // invoiceMap.put(prop.getProperty("shippingquantity"), new Integer(invoice.getItemInfo().getQuantity()).toString());
        invoiceMap.put(prop.getProperty("rate"), (invoice.getItemInfo().getRate()).toString());
       // invoiceMap.put(prop.getProperty("shippingrate"), (invoice.getItemInfo().getShippingCharge()).toString());
        invoiceMap.put(prop.getProperty("price"), String.valueOf(invoice.getItemInfo().getRate().doubleValue() *  invoice.getItemInfo().getQuantity()));
        invoiceMap.put(prop.getProperty("shippingprice"), String.valueOf(invoice.getItemInfo().getShippingCharge().doubleValue() *  invoice.getItemInfo().getQuantity()));
        invoiceMap.put(prop.getProperty("discountAmount"), String.valueOf(invoice.getItemInfo().getDiscountAmount()));
        invoiceMap.put(prop.getProperty("totalQuantity"), new Integer(invoice.getItemInfo().getQuantity()).toString());
        invoiceMap.put(prop.getProperty("totalPrice"), (invoice.getItemInfo().getAmount()).toString());
        invoiceMap.put(prop.getProperty("amountinWords"), amountInWords);
        invoiceMap.put(prop.getProperty("remarkslegalName"), invoice.getSellerInfo().getLegalName());
        invoiceMap.put(prop.getProperty("salesTaxNumber"), invoice.getSellerInfo().getLocalSalesTaxNumber());
        
        return invoiceMap;

    }
   
    public static void main(String[] args) throws FileNotFoundException, IOException, DocumentException, Exception {
		System.out.println("sending invoice");
    	Invoice invoice = new Invoice();
		invoice.setId(12345L);
		invoice.setInvoiceDate(new Date());
		invoice.setInvoiceNumber("98765");
		invoice.setOrderId(34567L);
		InvoiceService service = new InvoiceService();
		service.sendInvoice(invoice, "shaikezas@gmail.com");
		System.out.println("Invoice sent");
	}
    
}
