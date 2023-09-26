package org.webrtc.kite.telemed;

import org.webrtc.kite.stats.GetStatsStep;
import org.webrtc.kite.telemed.steps.AccessVisitStep;
import org.webrtc.kite.telemed.steps.CloseVisitStep;
import org.webrtc.kite.telemed.steps.OpenUrlStep;
import org.webrtc.kite.telemed.steps.WaitStep;
import org.webrtc.kite.tests.KiteBaseTest;
import org.webrtc.kite.tests.TestRunner;

import java.util.HashMap;
import java.util.Map;

public class KiteTelemedTest extends KiteBaseTest {
  Map<String, String> visits = new HashMap<>();
  int visitCounter = 0;
  boolean medicAccount = false;
  @Override
  protected void payloadHandling() {
    super.payloadHandling();
    if (this.payload != null) {
      getRoomManager().setRoomUrl("");
    }
  }

  @Override
  public void populateTestSteps(TestRunner runner) {

    String room = getRoomManager().getRoomUrl(true);

    medicAccount = !medicAccount;
    runner.addStep(new OpenUrlStep(runner, url, medicAccount, visits, room));
    runner.addStep(new AccessVisitStep(runner, url + "#/pocPJ/", visits, room));
    runner.addStep(new GetStatsStep(runner, getStatsConfig));
    runner.addStep(new CloseVisitStep(runner));

  }

}
