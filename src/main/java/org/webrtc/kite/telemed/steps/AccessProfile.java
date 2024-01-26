package org.webrtc.kite.telemed.steps;

import org.webrtc.kite.telemed.pages.MainPage;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;

public class AccessProfile extends TestStep {
    private final String url;
    private final MainPage mainPage;

    public AccessProfile(Runner runner, String url) {
        super(runner);
        this.url = url;
        this.mainPage = new MainPage(runner);
    }

    @Override
    public String stepDescription() {
        return "Open " + url;
    }

    @Override
    protected void step() throws KiteTestException {
        mainPage.open(url);
    }
}
