package org.endeavourhealth.cim.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TestDataAdapter implements IDataAdapter {
    // System
    public String getTransformerTypeName() {
        return "org.endeavourhealth.cim.transform.EmisTransformer";
    }

    // Demographics
    public String getPatientRecordByPatientId(String odsCode, UUID patientId) {
        return "";
    }
    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) {
        return "";
    }
    public String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) {
        return "";
    }
    public String tracePatientByNhsNumber(String nhsNumber) {
        return "";
    }
    public ArrayList<UUID> getChangedPatients(String odsCode, Date date) {
        return new ArrayList<>();
    }

    // Medical record
    public String createCondition(String odsCode, String requestData) {
        return "";
    }
    public String getConditionsByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }
    public String getAllergyIntolerancesByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }
    public String getImmunizationsByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }
    public String getMedicationPrescriptionsByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }

    // Appointments
    public String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) {
        return "";
    }
    public void requestOrder(String odsCode, String orderRequest) {

    }
    public String getSchedules(String odsCode, Date dateFrom, Date dateTo) {
            return getSchedulesResult();
    }

	public String getSchedules(String odsCode, String actor) {
			return getSchedulesResult();
	}

	public String getSlots(String odsCode, String scheduleId) {
        return "";
    }

	private String getSchedulesResult() {
		return "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
				"<AppointmentSessionList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.e-mis.com/emisopen/MedicalRecord\">\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>17</DBID>\n" +
				"    <Date>29/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>1</DBID>\n" +
				"        <RefID>1</RefID>\n" +
				"        <Title>Dr</Title>\n" +
				"        <FirstNames>Robert</FirstNames>\n" +
				"        <LastName>Burns</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>18</DBID>\n" +
				"    <Date>29/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>2</DBID>\n" +
				"        <RefID>2</RefID>\n" +
				"        <Title>Mrs</Title>\n" +
				"        <FirstNames>Sharon</FirstNames>\n" +
				"        <LastName>Cook</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>19</DBID>\n" +
				"    <Date>29/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>3</DBID>\n" +
				"        <RefID>3</RefID>\n" +
				"        <Title>Mrs</Title>\n" +
				"        <FirstNames>Kathleen</FirstNames>\n" +
				"        <LastName>Bray</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>20</DBID>\n" +
				"    <Date>29/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>4</DBID>\n" +
				"        <RefID>4</RefID>\n" +
				"        <Title>Mr</Title>\n" +
				"        <FirstNames>Anonymous</FirstNames>\n" +
				"        <LastName>User</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>33</DBID>\n" +
				"    <Date>30/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>1</DBID>\n" +
				"        <RefID>1</RefID>\n" +
				"        <Title>Dr</Title>\n" +
				"        <FirstNames>Robert</FirstNames>\n" +
				"        <LastName>Burns</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>34</DBID>\n" +
				"    <Date>30/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>2</DBID>\n" +
				"        <RefID>2</RefID>\n" +
				"        <Title>Mrs</Title>\n" +
				"        <FirstNames>Sharon</FirstNames>\n" +
				"        <LastName>Cook</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>35</DBID>\n" +
				"    <Date>30/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>3</DBID>\n" +
				"        <RefID>3</RefID>\n" +
				"        <Title>Mrs</Title>\n" +
				"        <FirstNames>Kathleen</FirstNames>\n" +
				"        <LastName>Bray</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>36</DBID>\n" +
				"    <Date>30/06/2015</Date>\n" +
				"    <StartTime>09:00</StartTime>\n" +
				"    <EndTime>17:00</EndTime>\n" +
				"    <SlotLength>60</SlotLength>\n" +
				"    <SlotTypeList>\n" +
				"      <SlotType>\n" +
				"        <TypeID />\n" +
				"        <Description />\n" +
				"        <Total>9</Total>\n" +
				"        <Booked>0</Booked>\n" +
				"        <Blocked>0</Blocked>\n" +
				"        <Embargoed>0</Embargoed>\n" +
				"        <Available>9</Available>\n" +
				"      </SlotType>\n" +
				"    </SlotTypeList>\n" +
				"    <Site>\n" +
				"      <DBID>1</DBID>\n" +
				"      <Name>Alpha Surgery</Name>\n" +
				"    </Site>\n" +
				"    <HolderList>\n" +
				"      <Holder>\n" +
				"        <DBID>4</DBID>\n" +
				"        <RefID>4</RefID>\n" +
				"        <Title>Mr</Title>\n" +
				"        <FirstNames>Anonymous</FirstNames>\n" +
				"        <LastName>User</LastName>\n" +
				"      </Holder>\n" +
				"    </HolderList>\n" +
				"  </AppointmentSession>\n" +
				"</AppointmentSessionList>";
	}
}
