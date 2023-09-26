package org.webrtc.kite.telemed.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.webrtc.kite.telemed.pages.MainPage;

import java.util.Map;

public class WaitStep extends TestStep {

    private final String url;
    private final MainPage mainPage;
    Map<String, String> visits;
    String room;

    public WaitStep(Runner runner, String url, Map<String, String> visits, String room) {
        super(runner);
        this.url = url;
        this.mainPage = new MainPage(runner);
        this.visits = visits;
        this.room = room;
    }


    @Override
    public String stepDescription() {
        return "Open " + url;
    }

    @Override
    protected void step() throws KiteTestException {
        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.alertIsPresent());
    }


}