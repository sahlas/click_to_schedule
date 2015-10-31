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

    @Test
    @Category(Deep.class)
    public void selectAlienWeek() {
        ScheduleType locator = ScheduleType.valueOf("ALIEN");
        schedule.with(locator.getLocator(), locator.name(), firstName, lastName, email, true);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }


    @Test
    @Category(Shallow.class)
    public void selectAlienMonth() {
        ScheduleType locator = ScheduleType.valueOf("ALIEN");
        schedule.with(locator.getLocator(), locator.name(), firstName, lastName, email, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    @Test
    @Category(Deep.class)
    public void selectIndianaWeek() {
        ScheduleType locator = ScheduleType.valueOf("INDIANA");
        schedule.with(locator.getLocator(), locator.name(), firstName, lastName, email, true);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    @Test
    @Category(Shallow.class)
    public void selectIndianaMonth() {
        ScheduleType locator = ScheduleType.valueOf("INDIANA");
        schedule.with(locator.getLocator(), locator.name(), firstName, lastName, email, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
    }

    @Test
    @Category(Shallow.class)
    public void selectCompOfficeWeek() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.with(locator.getLocator(), locator.name(), firstName, lastName, email, true);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
        assertTrue("Expected an availability error and instructions to choose another time slot",
                schedule.failureMessagePresent());
    }

    @Test
    @Category(Shallow.class)
    public void selectCompOfficeMonth() {
        ScheduleType locator = ScheduleType.valueOf("COMPOFFICE");
        schedule.with(locator.getLocator(), locator.name(), firstName, lastName, email, false);
        assertTrue("There was a problem reaching the '" +
                        "Ready to Schedule this Appointment?' form",
                schedule.readyToScheduleForm());
        assertTrue("Expected an availability error and instructions to choose another time slot",
                schedule.failureMessagePresent());
    }


    @After
    public void tearDown() {
        driver.quit();
    }
}
