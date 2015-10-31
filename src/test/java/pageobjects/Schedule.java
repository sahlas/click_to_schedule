package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class Schedule extends Base{

    private WebDriver driver;


    // initialize this later when the appointmentName is passed from the test
    By choiceLocator;


    // first page appointment type list
    By appointmentTypeLocator = By.id("tt_form_ValidationList_0");

    // Availability - used to select the first available date on the page that contains time slots
    By availabilityToolbar = By.id("atoolbar");
    By timeSlotchoiceSelectLocator = By.id("tt_form_ChoiceSelect_0");
    By slotContainer = By.cssSelector(".slotContainer");
    By weekView = By.id("WeekDateTime");

    // when a double booking occurs this is useful to quickly locate and assert that the text is visibly shown to client
    By failureMessageLocator = By.className("errors");
    // locator used to help determine whether to continue to move forward or continue waiting
    By readytoScheduleLocator = By.id("workflow-step");


    public Schedule(WebDriver driver) {
        super(driver);
        this.driver = driver;
        visit("/schedule/?locationId=westford");
        // assert that the initial page is ready for testing, otherwise abort
        assertTrue("The schedule application is not available, aborting!",
                isDisplayed(appointmentTypeLocator));
    }

    /**
     *  Setup the page object with defaults for your test
     * @param appointmentType
     * @param appointmentName
     * @param firstName
     * @param lastName
     * @param email
     * @param isWeek - when true, will switch to a Week view for the time slots page
     */
    public void with(String appointmentType, String appointmentName,
                     String firstName, String lastName, String email, Boolean isWeek) {
        // Select an appointment type
        selectAppointmentType(appointmentType, appointmentName);
        // choose a time
        chooseTimeSlot(isWeek);
        // fill in the attendee information
        fillAttendeeInfo(appointmentName, firstName, lastName, email);
        //schedule it and
        scheduleAppointment();
        // review confirmation page and check for confirmation # or a rejection notice
        reviewConfirmationPage();
    }

    /**
     * wrapper for appointmentType
     * @param appointmentType - enum value used to lookup the element on the page
     * @param appointmentName - enum name used for description here
     */
    public void selectAppointmentType(String appointmentType, String appointmentName) {
        driver.findElement(appointmentTypeLocator).click();

        choiceLocator =  By.id(appointmentType);
        System.out.println("type: " + appointmentType);
        switch (appointmentName) {
            case "ALIEN":
                driver.findElement(choiceLocator).click();
                break;
            case "COMPOFFICE":
                driver.findElement(choiceLocator).click();
                break;
            case "INDIANA":
                driver.findElement(choiceLocator).click();
                break;
            default:
                System.out.println("type: " + appointmentName);
                break;
        }

        driver.findElement(By.id("nextBtn")).click();
    }

    public void chooseTimeSlot (Boolean isWeek) {
        // Can validate location

        // wait for the calendar which holds the timeslots to load
        waitForIsDisplayed(timeSlotchoiceSelectLocator, 5);

        if (isWeek) {
            // choose either Week
            driver.findElement(By.cssSelector(".subtle.first")).click();
            waitForIsDisplayed(weekView, 5);
            //        waitForIsDisplayed(slotContainer, 5);
            // pick the first time slot
            new Select(driver.findElement(By.cssSelector("#tt_form_ChoiceSelect_21"))).selectByIndex(1);
        } else {
            new Select(driver.findElement(timeSlotchoiceSelectLocator)).selectByIndex(1);
        }
        // move to the next phase in booking the appointment
        driver.findElement(By.id("nextBtn")).click();
    }

    /**
     * Complete the attendee contact information and submit
     * @param appointmentName - choice of 3 appointment types
     * @param firstName - attendee first name
     * @param lastName - attendee last name
     * @param email - attendee email
     */
    public void fillAttendeeInfo (String appointmentName, String firstName, String lastName, String email) {

        // these are the common fields for each appointment type
        driver.findElement(By.name("attendee_person_firstName")).clear();
        driver.findElement(By.name("attendee_person_firstName")).sendKeys(firstName);
        driver.findElement(By.name("attendee_person_lastName")).clear();
        driver.findElement(By.name("attendee_person_lastName")).sendKeys(lastName);
        driver.findElement(By.name("attendee_email")).clear();
        driver.findElement(By.name("attendee_email")).sendKeys(email);

        // handle the extended attendee information request
        switch (appointmentName) {
            case "ALIEN":
                new Select(driver.findElement(By.id("questionId__current"))).selectByVisibleText("Yes");
                break;
            case "INDIANA":
                //questionId__signed-label
                driver.findElement(By.id("questionId__signed")).click();
                break;
            default:
                System.out.println("This type '" + appointmentName + "' does not require additional information.");
                break;
        }

        driver.findElement(By.id("nextBtn")).click();

    }

    public void scheduleAppointment () {
        driver.findElement(By.id("nextBtn")).click();
    }

    public void reviewConfirmationPage () {}

    public Boolean readyToScheduleForm() {
        return waitForIsDisplayed(readytoScheduleLocator, 10);
    }

    public Boolean failureMessagePresent() {
        return driver.findElement(failureMessageLocator).isDisplayed();
    }

}

