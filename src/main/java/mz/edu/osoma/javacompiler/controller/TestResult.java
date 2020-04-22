package mz.edu.osoma.javacompiler.controller;

import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    private List<Failure> failures = new ArrayList<>();
    private boolean wasSuccessful = false;

    public TestResult(List<Failure> failures, boolean wasSuccessful) {
        this.failures = failures;
        this.wasSuccessful = wasSuccessful;
    }

    public List<Failure> getFailures() {
        return failures;
    }

    public void setFailures(List<Failure> failures) {
        this.failures = failures;
    }

    public boolean isWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }
}
