package org.endeavourhealth.cim.processor.clinical;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.exceptions.InvalidParamException;
import org.endeavourhealth.common.core.exceptions.NotFoundException;
import org.endeavourhealth.core.utils.TextUtils;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.hl7.fhir.instance.model.MedicationOrder;

public class GetMedicationPrescriptionsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode, true);
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

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getMedicationPrescriptions(odsCode, patientId, medicationOrderStatus);

		if (TextUtils.isNullOrTrimmedEmpty(responseBody))
			throw new NotFoundException("patient");

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
