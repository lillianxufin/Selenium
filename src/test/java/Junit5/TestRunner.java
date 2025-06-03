package Junit5;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import UnitTest.NumberComputingTest;
import UnitTest.NumberComputingTest2;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Running JUnit 5 tests...");
        
        // Create launcher
        Launcher launcher = LauncherFactory.create();
        
        // Create summary listener to collect results
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        
        // Build discovery request for specific test classes
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(
                DiscoverySelectors.selectClass(NumberComputingTest.class),
                DiscoverySelectors.selectClass(NumberComputingTest2.class)
            )
            .build();
        
        // Execute tests
        launcher.execute(request, listener);
        
        // Print results
        System.out.println("Tests completed.");
        System.out.println("Tests run: " + listener.getSummary().getTestsFoundCount());
        System.out.println("Tests successful: " + listener.getSummary().getTestsSucceededCount());
        System.out.println("Tests failed: " + listener.getSummary().getTestsFailedCount());
        System.out.println("Tests skipped: " + listener.getSummary().getTestsSkippedCount());
        
        // Print detailed failure information if any
        if (listener.getSummary().getTestsFailedCount() > 0) {
            System.out.println("\nFailure details:");
            listener.getSummary().getFailures().forEach(failure -> {
                System.out.println("Failed: " + failure.getTestIdentifier().getDisplayName());
                System.out.println("Reason: " + failure.getException().getMessage());
            });
        }
    }
}