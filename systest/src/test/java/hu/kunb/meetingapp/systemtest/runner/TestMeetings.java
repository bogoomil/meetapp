package hu.kunb.meetingapp.systemtest.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/meetingReservations")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "hu.kunb.meetingapp.systemtest.stepdefinitions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "html:target/riport/html/Meetings.html")
public class TestMeetings {
}
