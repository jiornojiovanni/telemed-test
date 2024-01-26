package org.webrtc.kite.telemed.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CloseVisitStep extends TestStep {

    public CloseVisitStep(Runner runner) {
        super(runner);

    }

    @Override
    protected void step() throws KiteTestException {
        webDriver.manage().window().maximize();
        WebElement element = webDriver.findElement(By.id("end-call"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);

        new WebDriverWait(webDriver, 10).until(ExpectedConditions.elementToBeClickable(By.id("end-call"))).click();
    }

    @Override
    public String stepDescription() {
        return "Closing visit";
    }
}
