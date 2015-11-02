package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class Schedule extends Base{

    private WebDriver driver;
    private String timeSlotChosen;


    // initialize this later when the appointmentName is passed from the test
    By choiceLocator;

    // first page appointment type list
    By appointmentTypeLocator = By.id("tt_form_ValidationList_0");
    By timeSlotChoiceSelectLocator = By.id("tt_form_ChoiceSelect_0");
    By weekView = By.id("WeekDateTime");

    // when a double booking occurs this is useful to quickly locate and assert that the text is visibly shown to client
    By failureMessageLocator = By.className("errors");
    // locator used to help determine whether to continue to move forward or continue waiting
    By readytoScheduleLocator = By.id("workflow-step");

    public Schedule(WebDriver driver) {
        super(driver);
        this.driver = driver;
        visit("/schedule/?locationId=westford");
        // Assert that the initial page is ready for testing, otherwise abort
        // this assertion is OK here and is the exception to the rule that says
        // 'keep your test code out of page object code'.
        assertTrue("The click to schedule application is not available, aborting!",
                isDisplayed(appointmentTypeLocator));
    }

    /**
     *  Setup the page object with Default Attendees for your test
     * @param appointmentType
     * @param appointmentName
     * @param isWeek - when true, will switch to a Week view for the time slots page
     * @param isRandom - when true will pick a random timeslot otherwise picks index 1
     */
    public void withDefaultAttendees(String appointmentType, String appointmentName, Boolean isWeek, Boolean isRandom) {
        // Select an appointment type
        selectAppointmentType(appointmentType, appointmentName);
        // choose a time
        chooseTimeSlot(isWeek, isRandom);
        // fill in the attendee information
        fillAttendeeInfo(appointmentName, firstName, lastName, email);
        //schedule it and
        scheduleAppointment();
        // review confirmation page and check for confirmation # or a rejection notice
        reviewConfirmationPage();
    }

    /**
     * Schedule appointments and specify the attendees information
     * @param appointmentType
     * @param appointmentName
     * @param firstName
     * @param lastName
     * @param email
     */
    public void withAttendees(String appointmentType, String appointmentName,
                              String firstName, String lastName, String email) {
        // Select an appointment type
        selectAppointmentType(appointmentType, appointmentName);
        // choose a time
        chooseTimeSlot(false, false);
        // fill in the attendee information
        fillAttendeeInfo(appointmentName, firstName, lastName, email);
        //schedule it and
        scheduleAppointment();
        // review confirmation page and check for confirmation # or a rejection notice
        reviewConfirmationPage();
    }

    public void withoutAppointmentType(){
        // Select an appointment type
        selectAppointmentType(null, null);
    }

    /**
     * wrapper for appointmentType
     * @param appointmentType - enum value used to lookup the element on the page
     * @param appointmentName - enum name used for description here
     */
    public void selectAppointmentType(String appointmentType, String appointmentName) {
        if(appointmentType!=null) {
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
        } else {
            // try to submit without first making choice
            driver.findElement(By.id("nextBtn")).click();
        }
        driver.findElement(By.id("nextBtn")).click();
    }

    public void chooseTimeSlot(Boolean isWeek, Boolean isRandom) {
        Random rand = new Random();
        int idx = 0, numSlots = 0;

        // wait for the calendar which holds the timeslots to load
        waitForIsDisplayed(timeSlotChoiceSelectLocator, 5);
        // determine the number of available time slots and choose a random available time
        numSlots = numAvailableSlots(timeSlotChoiceSelectLocator);

        if (isWeek) {
            // choose either Week
            driver.findElement(By.cssSelector(".subtle.first")).click();
            waitForIsDisplayed(weekView, 5);
            idx = (isRandom) ? rand.nextInt(numSlots) + 1 : 1;
            new Select(driver.findElement(By.cssSelector("#tt_form_ChoiceSelect_21"))).selectByIndex(idx);
        } else {
            idx = (isRandom) ? rand.nextInt(numSlots) + 1 : 1;
            System.out.println("with index " + idx);
            new Select(driver.findElement(timeSlotChoiceSelectLocator)).selectByIndex(idx);
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
                driver.findElement(By.id("questionId__signed")).click();
                break;
            case "COMPOFFICE":
                System.out.println("This type '" + appointmentName + "' does not require additional information.");
                break;
            default:
                System.out.println("Unexpected appointmentName param " + appointmentName);
                // do some error handling
                break;
        }
        driver.findElement(By.id("nextBtn")).click();
    }

    public void scheduleAppointment () {
        driver.findElement(By.id("nextBtn")).click();
    }

    public int numAvailableSlots (By bytimeSlotChoice) {
        List<WebElement> options;
        String availableSlotsLabel;
        int availableSlots;
        WebElement firstSelectedOption = new Select(driver.findElement(bytimeSlotChoice)).getFirstSelectedOption();
        System.out.println("firstSelectedOption: " + firstSelectedOption.getText());
        // reads the integer value
        availableSlotsLabel = firstSelectedOption.getText();
        // strip out the strings and parse the int from the value
        availableSlots = Integer.parseInt(availableSlotsLabel.replaceAll("[\\D]", ""));
        // keep track of the slot
        return availableSlots;
    }

    public void reviewConfirmationPage () {}

    public Boolean readyToScheduleForm() {
        return waitForIsDisplayed(readytoScheduleLocator, 10);
    }

    public Boolean failureMessagePresent() {
        return driver.findElement(failureMessageLocator).isDisplayed();
    }

    public Boolean valueRequiredMessagePresent() {
        return driver.findElement(By.cssSelector("#attendee_email")).isDisplayed();
    }

    public Boolean valueRequiredModalDialog() {
        return driver.findElement(By.cssSelector(".title")).isDisplayed();
    }
}

