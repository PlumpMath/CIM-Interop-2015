package org.endeavourhealth.cim.camel.processors.clinical;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.exceptions.InvalidParamException;
import org.endeavourhealth.cim.camel.exceptions.NotFoundException;
import org.endeavourhealth.cim.dataManager.emis.DataManager;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.hl7.fhir.instance.model.MedicationOrder;

public class GetMedicationPrescriptionsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);
		String status = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Status, false);

		MedicationOrder.MedicationOrderStatus medicationOrderStatus = null;

		if (!TextUtils.isNullOrTrimmedEmpty(status)) {
			try	{
				medicationOrderStatus = MedicationOrder.MedicationOrderStatus.fromCode(status);
			} catch (Exception e) {
				throw new InvalidParamException("status", e);
			}
		}

		DataManager dataManager = new DataManager();
		String responseBody = dataManager.getMedicationPrescriptions(odsCode, patientId, medicationOrderStatus);

		if (TextUtils.isNullOrTrimmedEmpty(responseBody))
			throw new NotFoundException("patient");

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
