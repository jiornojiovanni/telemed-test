package org.webrtc.kite.telemed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.webrtc.kite.stats.GetStatsStep;
import org.webrtc.kite.telemed.steps.AccessProfile;
import org.webrtc.kite.telemed.steps.AccessVisitStep;
import org.webrtc.kite.telemed.steps.CloseVisitStep;
import org.webrtc.kite.telemed.steps.OpenUrlStep;
import org.webrtc.kite.telemed.steps.WaitStep;
import org.webrtc.kite.tests.KiteBaseTest;
import org.webrtc.kite.tests.TestRunner;

import io.cosmosoftware.kite.exception.KiteTestException;

public class KiteTelemedTest extends KiteBaseTest {
  Map<String, String> visits = new HashMap<>();
  ArrayList<String> visite = new ArrayList<>();
  int visitCounter = 0;
  boolean medicAccount = false;
  String medicToken, patientToken;

  @Override
  protected void payloadHandling() {
    super.payloadHandling();

    if (this.payload != null) {
      getRoomManager().setRoomUrl("");
    }

    APIClient client = new APIClient();
    try {
      Properties credentials = new Properties();
      credentials.load(getClass().getResourceAsStream("/credentials.properties"));

      medicToken = client.getToken(credentials.getProperty("medicEmail"), credentials.getProperty("medicPass"));
      patientToken = client.getToken(credentials.getProperty("patientEmail"), credentials.getProperty("patientPass"));

      getRoomManager().setPreconfiguredRooms(null);

      for (int i = 0; i < (tupleSize / getMaxUsersPerRoom()); i++) {
        String visit = String.valueOf(client.createVisit(medicToken, credentials.getProperty("patientEmail")));
        logger.info("The visit is: " + visit);
        visite.add(visit);
      }

      roomManager.setPreconfiguredRooms(visite);

    } catch (IOException e) {
      System.err.println(e);
      throw new RuntimeException(e);
    }

  }

  @Override
  public void populateTestSteps(TestRunner runner) {

    String room;
    try {
      room = getRoomManager().getPreconfigureRooUrl();
      logger.info(room);

      medicAccount = !medicAccount;

      if (medicAccount) {
        runner.addStep(new OpenUrlStep(runner, url, medicToken, room));
      } else {
        runner.addStep(new OpenUrlStep(runner, url, patientToken, room));
      }
  
      runner.addStep(new AccessProfile(runner, url + "profile"));
      runner.addStep(new AccessVisitStep(runner, url + "#/pocPJ/", room, medicAccount));
      runner.addStep(new GetStatsStep(runner, getStatsConfig));
      
      if (medicAccount) {
        runner.addStep(new CloseVisitStep(runner));
      }

    } catch (KiteTestException e) {
      e.printStackTrace();
    }
  }
}
