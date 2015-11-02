package tests;


/**
 * Created by billsahlas on 10/27/15.
 */

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.experimental.categories.Category;
import pageobjects.Schedule;
import tests.groups.Deep;
import tests.groups.Shallow;

import static org.junit.Assert.assertTrue;

public class TestSchedule extends Base{

    enum ScheduleType {
        ALIEN("tt_form_ChoiceButton_0"), INDIANA("tt_form_ChoiceButton_3"), COMPOFFICE("tt_form_ChoiceButton_2");

        private String locator;

        public String getLocator(){
            return this.locator;
        }
        private ScheduleType(String locator){
            this.locator = locator;
        }
    }

    private Schedule schedule;
    private String preferedTime = "8:00 PM";

    @Before
    public void setUp() { schedule = new Schedule(driver); }

    // ALIEN TYPE
    @Test
    @Category(Deep.class)
    public void selectAlienWeek() {
        ScheduleType locator = ScheduleType.valueOf("ALIEN");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), true, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    @Test
    @Category(Shallow.class)
    public void selectAlienMonth() {
        ScheduleType locator = ScheduleType.valueOf("ALIEN");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), false, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    // INDIANA TYPE
    @Test
    @Category(Deep.class)
    public void selectIndianaWeek() {
        ScheduleType locator = ScheduleType.valueOf("INDIANA");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), true, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    @Test
    @Category(Shallow.class)
    public void selectIndianaMonth() {
        ScheduleType locator = ScheduleType.valueOf("INDIANA");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), false, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    // COMPOFFICE type withDefaultAttendees random time slot selections
    @Test
    @Category(Shallow.class)
    public void selectCompOfficeWeek() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), true, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
        assertTrue("Expected an availability error and instructions to choose another time slot",
                schedule.failureMessagePresent());
    }

    @Test
    @Category(Shallow.class)
    public void selectCompOfficeWeekRandom() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), true, true);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    @Test
    @Category(Shallow.class)
    public void selectCompOfficeMonth() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), false, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
        assertTrue("Expected an availability error and instructions to choose another time slot",
                schedule.failureMessagePresent());
    }

    @Test
    @Category(Shallow.class)
    public void selectCompOfficeMonthRandom() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), false, true);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    // write a test that tries to submit contact info without properly filling out the form
    // mvn clean test -Dtest=TestSchedule#incompleteAttendeeInfo
    @Test
    @Category(Shallow.class)
    public void incompleteAttendeeInfo() {
        ScheduleType locator = ScheduleType.valueOf("ALIEN");
        schedule.withAttendees(locator.getLocator(), locator.name(), "Bill", "Sahlas", "");
        assertTrue("Value required message indicator should have been displayed!",
                schedule.valueRequiredMessagePresent());
    }

    // write a test that tries to submit the appointment type without choosing a type the form
    // mvn clean test -Dtest=TestSchedule#missingAppointmentType
    @Test
    @Category(Shallow.class)
    public void missingAppointmentType() {
        schedule.withoutAppointmentType();
        assertTrue("Value required message indicator should have been displayed!",
                schedule.valueRequiredModalDialog());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
