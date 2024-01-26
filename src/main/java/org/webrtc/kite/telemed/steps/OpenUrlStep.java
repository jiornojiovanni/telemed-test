package org.webrtc.kite.telemed.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import io.cosmosoftware.kite.interfaces.Runner;
import org.openqa.selenium.JavascriptExecutor;
import org.webrtc.kite.telemed.APIClient;
import org.webrtc.kite.telemed.pages.MainPage;

import java.util.Map;

public class OpenUrlStep extends TestStep {
  
  private final String url;
  private final MainPage mainPage;
  APIClient client;
  Map<String, String> visits;
  String room;
  private final String token;
  public OpenUrlStep(Runner runner, String url, String token, String room) {
    super(runner);
    this.url = url;
    this.mainPage = new MainPage(runner);
    this.token = token;
    this.client = new APIClient();
    this.room = room;
  }
  
  
  @Override
  public String stepDescription() {
    return "Open " + url;
  }
  
  @Override
  protected void step() throws KiteTestException {
    mainPage.open(url);

    //We take the system time in milliseconds and add the expiry time of the token, 3600 seconds
    long expires_at = (System.currentTimeMillis() + 3600000);

    ((JavascriptExecutor) webDriver).executeScript(String.format("localStorage.setItem('jwt_token', \'%s\');", token));
    ((JavascriptExecutor) webDriver).executeScript(String.format("localStorage.setItem('expires_at', \'%s\');", expires_at));
    
  }


}
