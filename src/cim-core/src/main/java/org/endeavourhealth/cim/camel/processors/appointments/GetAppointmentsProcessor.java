package org.endeavourhealth.cim.camel.processors.appointments;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.camel.helpers.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.exceptions.BaseException;
import org.endeavourhealth.cim.camel.exceptions.InvalidParamException;
import org.endeavourhealth.cim.camel.helpers.DateUtils;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.hl7.fhir.instance.model.Appointment;

import java.util.Date;
import java.util.UUID;

public class GetAppointmentsProcessor implements Processor
{
	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception
	{
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, CIMHeaderKey.Patient, true);
		String statusString = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Status, false);
		Appointment.AppointmentStatus status = null;

		try
		{
			if (!TextUtils.isNullOrTrimmedEmpty(statusString))
				status = Appointment.AppointmentStatus.fromCode(statusString);
		}
		catch (Exception e)
		{
			throw new InvalidParamException(e);
		}

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getPatientAppointments(odsCode, patientId, status);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
