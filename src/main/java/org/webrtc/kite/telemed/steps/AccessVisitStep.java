package org.webrtc.kite.telemed.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.webrtc.kite.telemed.pages.MainPage;

import java.util.Map;

public class AccessVisitStep extends TestStep {

    private final String url;
    private final MainPage mainPage;
    Map<String, String> visits;
    String room;
    boolean account;

    public AccessVisitStep(Runner runner, String url, String room, boolean medicAccount) {
        super(runner);
        this.url = url;
        this.mainPage = new MainPage(runner);
        this.room = room;
        this.account = medicAccount;
    }

    @Override
    public String stepDescription() {
        return "Open " + url;
    }

    @Override
    protected void step() throws KiteTestException {
        logger.info("visitid: " + room);
        if (!account) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, 3);
                wait.until(ExpectedConditions.alertIsPresent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mainPage.open(url + room);
    }
}