package ar.com.jsuper.services.factElec.impl;

import ar.com.jsuper.dao.exception.BussinessException;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;
import org.apache.axis.encoding.XMLType;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.dao.DataIntegrityViolationException;

public class afip_wsaa_client {

	static String invoke_wsaa(byte[] LoginTicketRequest_xml_cms, String endpoint) throws BussinessException {

		String LoginTicketResponse = null;
//        try {

		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();
		} catch (ServiceException ex) {
			throw new BussinessException(ex);
		}

		try {
			//
			// Prepare the call for the Web service
			//
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
		} catch (MalformedURLException ex) {
			throw new BussinessException(ex);
		}
		call.setOperationName("loginCms");
		call.addParameter("request", XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(XMLType.XSD_STRING);

		try {
			//
			// Make the actual call and assign the answer to a String
			//
			LoginTicketResponse = (String) call.invoke(new Object[]{
				Base64.encode(LoginTicketRequest_xml_cms)});
		} catch (RemoteException ex) {
			throw new BussinessException(ex);
		}

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		return (LoginTicketResponse);
	}

	//
	// Create the CMS Message
	//
	public static byte[] create_cms(String p12file, String p12pass, String signer, String dstDN, String service, Long TicketTime) throws BussinessException {

		PrivateKey pKey = null;
		X509Certificate pCertificate = null;
		byte[] asn1_cms = null;
		CertStore cstore = null;
		String LoginTicketRequest_xml;
		String SignerDN = null;

		//
		// Manage Keys & Certificates
		//
		// Create a keystore using keys from the pkcs#12 p12file
		KeyStore ks;
		try {
			ks = KeyStore.getInstance("pkcs12");
		} catch (KeyStoreException ex) {
			throw new BussinessException(ex);
		}
		FileInputStream p12stream;
		try {
			p12stream = new FileInputStream(p12file);
		} catch (FileNotFoundException ex) {
			throw new DataIntegrityViolationException("No se puede encontrar el archivo :" + p12file);
		}
		try {
			ks.load(p12stream, p12pass.toCharArray());
		} catch (IOException ex) {
			throw new BussinessException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new BussinessException(ex);
		} catch (CertificateException ex) {
			throw new BussinessException(ex);
		}
		try {
			p12stream.close();
		} catch (IOException ex) {
			throw new BussinessException(ex);
		}

		try {
			// Get Certificate & Private key from KeyStore
			pKey = (PrivateKey) ks.getKey(signer, p12pass.toCharArray());
		} catch (KeyStoreException ex) {
			throw new BussinessException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new BussinessException(ex);
		} catch (UnrecoverableKeyException ex) {
			throw new BussinessException(ex);
		}
		try {
			pCertificate = (X509Certificate) ks.getCertificate(signer);
		} catch (KeyStoreException ex) {
			throw new BussinessException(ex);
		}
		SignerDN = pCertificate.getSubjectDN().toString();
		System.out.println("SignerDN:" + SignerDN);

		// Create a list of Certificates to include in the final CMS
		ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>();
		certList.add(pCertificate);

		if (Security.getProvider("BC") == null) {
			Security.addProvider(new BouncyCastleProvider());
		}

		try {
			cstore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
		} catch (InvalidAlgorithmParameterException ex) {
			throw new BussinessException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new BussinessException(ex);
		} catch (NoSuchProviderException ex) {
			throw new BussinessException(ex);
		}

		//
		// Create XML Message
		// 
		LoginTicketRequest_xml = create_LoginTicketRequest(SignerDN, dstDN, service, TicketTime);

		//
		// Create CMS Message
		//
		// Create a new empty CMS Message
		CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

		// Add a Signer to the Message
		gen.addSigner(pKey, pCertificate, CMSSignedDataGenerator.DIGEST_SHA1);

		try {
			// Add the Certificate to the Message
			gen.addCertificatesAndCRLs(cstore);
		} catch (CertStoreException ex) {
			throw new BussinessException(ex);
		} catch (CMSException ex) {
			throw new BussinessException(ex);
		}

		// Add the data (XML) to the Message
		CMSProcessable data = new CMSProcessableByteArray(LoginTicketRequest_xml.getBytes());

		// Add a Sign of the Data to the Message
		CMSSignedData signed;
		try {
			signed = gen.generate(data, true, "BC");
		} catch (NoSuchAlgorithmException ex) {
			throw new BussinessException(ex);
		} catch (NoSuchProviderException ex) {
			throw new BussinessException(ex);
		} catch (CMSException ex) {
			throw new BussinessException(ex);
		}

		try {
			asn1_cms = signed.getEncoded();
		} catch (IOException ex) {
			throw new BussinessException(ex);
		}

		return (asn1_cms);
	}

	//
	// Create XML Message for AFIP wsaa
	// 	
	public static String create_LoginTicketRequest(String SignerDN, String dstDN, String service, Long TicketTime) {

		String LoginTicketRequest_xml;

		Date GenTime = new Date();
		GregorianCalendar gentime = new GregorianCalendar();
		GregorianCalendar exptime = new GregorianCalendar();
		String UniqueId = new Long(GenTime.getTime() / 1000).toString();

		exptime.setTime(new Date(GenTime.getTime() + TicketTime));

		XMLGregorianCalendarImpl XMLGenTime = new XMLGregorianCalendarImpl(gentime);
		XMLGregorianCalendarImpl XMLExpTime = new XMLGregorianCalendarImpl(exptime);

		LoginTicketRequest_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<loginTicketRequest version=\"1.0\">"
				+ "<header>"
				+ "<source>" + SignerDN + "</source>"
				+ "<destination>" + dstDN + "</destination>"
				+ "<uniqueId>" + UniqueId + "</uniqueId>"
				+ "<generationTime>" + XMLGenTime + "</generationTime>"
				+ "<expirationTime>" + XMLExpTime + "</expirationTime>"
				+ "</header>"
				+ "<service>" + service + "</service>"
				+ "</loginTicketRequest>";

		//System.out.println("TRA: " + LoginTicketRequest_xml);
		return (LoginTicketRequest_xml);
	}
}
