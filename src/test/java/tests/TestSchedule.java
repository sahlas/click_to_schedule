package tests;


/**
 * Created by billsahlas on 10/27/15.
 *
 * PageObject - http://www.seleniumhq.org/docs/06_test_design_considerations.jsp#page-object-design-pattern
 *
 */

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.experimental.categories.Category;
import pageobjects.Schedule;
import tests.groups.Deep;
import tests.groups.Negative;
import tests.groups.Shallow;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSchedule extends Base{

    // define the 3 type used for testing containing
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

    @Before
    public void setUp() { schedule = new Schedule(driver); }

    // ALIEN TYPE
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
    @Category(Shallow.class)
    public void selectIndianaMonth() {
        ScheduleType locator = ScheduleType.valueOf("INDIANA");
        schedule.withDefaultAttendees(locator.getLocator(), locator.name(), false, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    // COMPOFFICE type withDefaultAttendees
    @Test
    @Category(Shallow.class)
    public void selectCompOfficeWeek() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.withRandomAttendees(locator.getLocator(), locator.name(), true, false);
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
        schedule.withAttendees(locator.getLocator(), locator.name(), firstName, lastName, email, false, true);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
        // Note: even though we choose randomly we could still error we continue to choose on the same day
        // could create input data for randomly generated bogus email accounts
        assertFalse("Did not expect indication that we need to choose another time slot",
                schedule.failureMessagePresent());
    }

    @Test
    @Category(Shallow.class)
    public void selectCompOfficeAttendeesRandomMonthRandom() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.withRandomAttendees(locator.getLocator(), locator.name(), false, true);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
        // Note: even though we choose randomly we could still error we continue to choose on the same day
        // could create input data for randomly generated bogus email accounts
        assertFalse("Did not expect indication that we need to choose another time slot",
                schedule.failureMessagePresent());
    }


    // A test that tries to submit contact info without properly filling out the form
    // mvn clean test -Dtest=TestSchedule#incompleteAttendeeInfo
    @Test
    @Category(Negative.class)
    public void incompleteAttendeeInfo() {
        ScheduleType locator = ScheduleType.valueOf("ALIEN");
        schedule.withAttendees(locator.getLocator(), locator.name(), "Bill", "Sahlas", "", false, false);
        assertTrue("Value required message indicator should have been displayed!",
                schedule.valueRequiredMessagePresent());
    }

    // A test that tries to submit the appointment type without choosing a type the form
    // mvn clean test -Dtest=TestSchedule#missingAppointmentType
    @Test
    @Category(Negative.class)
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
