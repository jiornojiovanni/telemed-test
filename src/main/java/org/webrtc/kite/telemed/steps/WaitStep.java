package org.webrtc.kite.telemed.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitStep extends TestStep {

    private final String url;


    public WaitStep(Runner runner, String url) {
        super(runner);
        this.url = url;
    }


    @Override
    public String stepDescription() {
        return "Open " + url;
    }

    @Override
    protected void step() throws KiteTestException {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 50);
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}