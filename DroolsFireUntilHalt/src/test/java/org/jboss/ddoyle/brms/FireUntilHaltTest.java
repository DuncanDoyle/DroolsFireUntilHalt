package org.jboss.ddoyle.brms;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireUntilHaltTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(FireUntilHaltTest.class);
	
	@Test
	public void testFireUntilHalt() {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession();
		Thread fireUntilHaltThread = new Thread(new FireUntilHaltRunner(kieSession));
		fireUntilHaltThread.start();
		LOGGER.info("FireUntiHaltThread started. On my Mac, this causes the CPU to go to 100%.");
		//Sleeping for 30 seconds while fireUntilHaltThread is running. This causes the CPU on Mac OS-X 10.8.5 to go to 100%.
		for (int counter = 0; counter < 3; counter++) {
			try {
				LOGGER.info("Going to sleep for 10 seconds.");
				Thread.sleep(10000);
			} catch (InterruptedException ie) {
				LOGGER.error("Got an interrupt while sleeping. Resetting interrupt on current thread.");
				Thread.currentThread().interrupt();
			}
		}
		kieSession.halt();
		LOGGER.info("Halted the session.");
		LOGGER.info("Going to sleep for another 10 seconds to show that CPU load is normal again when FireUntilHalt has stopped.");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			LOGGER.error("Got an interrupt while sleeping. Resetting interrupt on current thread.");
			Thread.currentThread().interrupt();
		}
	}
	
	public static class FireUntilHaltRunner implements Runnable {

		private KieSession kieSession;
		
		public FireUntilHaltRunner(KieSession kieSession) {
			this.kieSession = kieSession;
		}
		
		@Override
		public void run() {
			kieSession.fireUntilHalt();
		}
		
		
	}
}
