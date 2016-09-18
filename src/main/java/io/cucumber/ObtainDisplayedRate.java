package io.cucumber;

import java.math.BigDecimal;

import io.magentys.Agent;
import io.magentys.AgentProvider;
import io.magentys.Mission;

/**
 * @author glick
 *
 *
 */
// BigDecimal is the return type of your mission
@SuppressWarnings("WeakerAccess")
public class ObtainDisplayedRate implements Mission<BigDecimal> {

  final String ccyPair;
  public ObtainDisplayedRate(final String ccyPair){
    this.ccyPair = ccyPair;
  }

  // Static constructor:

  public static ObtainDisplayedRate obtainsDisplayedRateFor(final String ccyPair){
    return new ObtainDisplayedRate(ccyPair);
  }

  // this is the only method a mission has to implement
  @Override
  public BigDecimal accomplishAs(Agent fxTrader) {

    // get the BloombergServiceClient which was assigned to the agent (fxTrader)
    // in the constructor body
    BloombergServiceClient bsc = AgentProvider.agent().usingThe(BloombergServiceClient.class);

    // use the BloombergServiceClient here...
    BigDecimal result = bsc.getResult();

    //also store in memory
    fxTrader.keepsInMind("bloomberg.rate", result);

    return result;
  }
}
