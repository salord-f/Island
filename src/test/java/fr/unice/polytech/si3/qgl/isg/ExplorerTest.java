package fr.unice.polytech.si3.qgl.isg;

import eu.ace_design.island.bot.IExplorerRaid;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ExplorerTest {

	@Test
	public void basicExplorer() {
		IExplorerRaid explorer = spy(new Explorer());
		explorer.initialize("{\"men\": 12, \"budget\": 10000,\"contracts\": [ { \"amount\": 600, \"resource\": \"WOOD\" }, { \"amount\": 200, \"resource\": \"GLASS\" }], \"heading\": \"W\" }");
		String decision = explorer.takeDecision();
		assertNotNull(decision);
		explorer.acknowledgeResults("{\n" +
				"  \"data\": {\n" +
				"    \"cost\": 4,\n" +
				"    \"extras\": {\n" +
				"      \"found\": \"OUT_OF_RANGE\",\n" +
				"      \"range\": 52\n" +
				"    },\n" +
				"    \"status\": \"OK\"\n" +
				"  }");
		String report = explorer.deliverFinalReport();
		assertNotNull(report);

		verify(explorer, times(1)).initialize(anyString());
		verify(explorer, times(1)).takeDecision();
		verify(explorer, times(1)).acknowledgeResults(anyString());
		verify(explorer, times(1)).deliverFinalReport();
	}
}
