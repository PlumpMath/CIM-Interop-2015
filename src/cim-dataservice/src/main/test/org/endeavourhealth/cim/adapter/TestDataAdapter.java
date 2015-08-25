package org.endeavourhealth.cim.dataManager.emis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestDataAdapter extends EmisDataAdapter {
     // Demographics
	@Override
    public String getPatientRecordByPatientId(String odsCode, UUID patientId) {
        return "";
    }
	@Override
    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) {
        return "";
    }
	@Override
    public String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) {
        return "";
    }
	@Override
    public List<String> tracePatientByNhsNumber(String nhsNumber) {
        return new ArrayList<>();
    }
	@Override
    public ArrayList<UUID> getChangedPatients(String odsCode, Date date) {
        return new ArrayList<>();
    }

    // Medical record
	@Override
    public String createCondition(String odsCode, String requestData) {
        return "";
    }
    // Appointments
	@Override
    public String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) {
        return "";
    }
	@Override
	public String getSchedules(String odsCode, Date dateFrom, Date dateTo) {
		return "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
				"<AppointmentSessionList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.e-mis.com/emisopen/MedicalRecord\">\n" +
				"  <AppointmentSession>\n" +
				"    <DBID>1</DBID>\n" +
				"    <Date>21/07/2015</Date>\n" +
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
				"    <DBID>2</DBID>\n" +
				"    <Date>21/07/2015</Date>\n" +
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
				"    <DBID>3</DBID>\n" +
				"    <Date>21/07/2015</Date>\n" +
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
				"    <DBID>4</DBID>\n" +
				"    <Date>21/07/2015</Date>\n" +
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
	@Override
	public String getSlots(String odsCode, String scheduleId) {
        return "";
    }

	// Admin
	@Override
	public String getOrganisationInformation(String odsCode) {
		return "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
				"<OrganisationInformation xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.e-mis.com/emisopen/MedicalRecord\">\n" +
				"  <OrganisationList>\n" +
				"    <Organisation>\n" +
				"      <DBID>1</DBID>\n" +
				"      <GUID>4a3ea1cf-7f01-4f61-9654-b07dd51a34f8</GUID>\n" +
				"      <LocationName>Alpha Surgery</LocationName>\n" +
				"      <NationalCode>A99999</NationalCode>\n" +
				"    </Organisation>\n" +
				"  </OrganisationList>\n" +
				"  <UserList>\n" +
				"    <User>\n" +
				"      <DBID>1</DBID>\n" +
				"      <GUID>e93841be-bd3d-469e-a2b3-03f6eea7cd9a</GUID>\n" +
				"      <Title>Dr</Title>\n" +
				"      <FirstNames>Robert</FirstNames>\n" +
				"      <LastName>Burns</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>2</DBID>\n" +
				"      <GUID>316d34eb-f495-4c91-b814-364c69b1a541</GUID>\n" +
				"      <Title>Mrs</Title>\n" +
				"      <FirstNames>Sharon</FirstNames>\n" +
				"      <LastName>Cook</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>3</DBID>\n" +
				"      <GUID>6cf19e3c-fbc4-4430-be96-5b98a94e12ab</GUID>\n" +
				"      <Title>Mrs</Title>\n" +
				"      <FirstNames>Kathleen</FirstNames>\n" +
				"      <LastName>Bray</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>4</DBID>\n" +
				"      <GUID>a21d7adb-b000-49fb-b2b3-5ec9c9d2ed49</GUID>\n" +
				"      <Title>Mr</Title>\n" +
				"      <FirstNames>Anonymous</FirstNames>\n" +
				"      <LastName>User</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>5</DBID>\n" +
				"      <GUID>756ce7b3-6e45-4693-b147-65ae05936534</GUID>\n" +
				"      <Title>Mrs</Title>\n" +
				"      <FirstNames>Carol</FirstNames>\n" +
				"      <LastName>Fitzgerald</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>6</DBID>\n" +
				"      <GUID>7235b5fe-a01f-4cfe-b7fa-b8cbe0c2725c</GUID>\n" +
				"      <Title />\n" +
				"      <FirstNames>Mary</FirstNames>\n" +
				"      <LastName>Cunningham</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>7</DBID>\n" +
				"      <GUID>0e9fec8f-c287-4f48-aece-c0c6433eb5e8</GUID>\n" +
				"      <Title>Mr</Title>\n" +
				"      <FirstNames>Philip</FirstNames>\n" +
				"      <LastName>Oliver</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>8</DBID>\n" +
				"      <GUID>893001d7-07fa-42d8-a303-c61fd99c874b</GUID>\n" +
				"      <Title>Dr</Title>\n" +
				"      <FirstNames>Adam</FirstNames>\n" +
				"      <LastName>Walton</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>9</DBID>\n" +
				"      <GUID>bdd39c67-dee0-474c-a3fe-2dcd09810aaf</GUID>\n" +
				"      <Title>Mrs</Title>\n" +
				"      <FirstNames>Eileen</FirstNames>\n" +
				"      <LastName>Walton</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>10</DBID>\n" +
				"      <GUID>256ddcf6-710b-4d0d-b1fe-02713063d187</GUID>\n" +
				"      <Title>Miss</Title>\n" +
				"      <FirstNames>Julie</FirstNames>\n" +
				"      <LastName>Thomson</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>11</DBID>\n" +
				"      <GUID>d664182d-c980-402e-b1c9-117f9c329475</GUID>\n" +
				"      <Title>Mrs</Title>\n" +
				"      <FirstNames>Joanne</FirstNames>\n" +
				"      <LastName>Sharpe</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>12</DBID>\n" +
				"      <GUID>7a07b9c1-9595-4f17-9672-4c5de9072879</GUID>\n" +
				"      <Title>Mrs</Title>\n" +
				"      <FirstNames>Rachel</FirstNames>\n" +
				"      <LastName>Skinner</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>13</DBID>\n" +
				"      <GUID>ded17d52-5646-47bd-8cb5-80667c1522aa</GUID>\n" +
				"      <Title />\n" +
				"      <FirstNames>Physiotherapist</FirstNames>\n" +
				"      <LastName>-</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>14</DBID>\n" +
				"      <GUID>cf1a5b91-d02d-49e4-9f52-9bc961628aaa</GUID>\n" +
				"      <Title>Mrs</Title>\n" +
				"      <FirstNames>Victoria</FirstNames>\n" +
				"      <LastName>Herbert</LastName>\n" +
				"    </User>\n" +
				"    <User>\n" +
				"      <DBID>15</DBID>\n" +
				"      <GUID>7fab7cd6-10be-454c-a34f-ddec091f5625</GUID>\n" +
				"      <Title>Miss</Title>\n" +
				"      <FirstNames>Maureen</FirstNames>\n" +
				"      <LastName>Whitehouse</LastName>\n" +
				"    </User>\n" +
				"  </UserList>\n" +
				"  <LocationTypeList>\n" +
				"    <LocationType>\n" +
				"      <DBID>1</DBID>\n" +
				"      <GUID>187011f3-aa9d-4419-89b7-d461953f9eb0</GUID>\n" +
				"      <Description>EMIS PCS Test Surgery 2</Description>\n" +
				"    </LocationType>\n" +
				"  </LocationTypeList>\n" +
				"</OrganisationInformation>";
	}
}
