package io.jenkins.plugins.main;

import fr.inria.spirals.repairnator.pipeline.JenkinsLauncher;
import fr.inria.spirals.repairnator.pipeline.RepairToolsManager;
import java.util.Arrays;


import java.util.logging.ConsoleHandler;
import java.util.logging.LogManager;

import java.util.logging.Logger;
import java.util.logging.Level; 
import java.util.logging.FileHandler;
import java.io.File;

import java.util.Enumeration;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import java.util.logging.Handler;

/* Run repairnator pipeline */
public class RunPipelineAction {
    private String gitUrl;
    private String gitToken;
    private String gitBranch;

    /* Might need to pinpoint the exact logger in the future, but it should be fine for now*/
    private void pipeAllLoggerToStderr() {
        ConsoleHandler handler = new ConsoleHandler();
        LogManager manager = LogManager.getLogManager();
        Enumeration<String> names = manager.getLoggerNames();
        while(names.hasMoreElements() ){
            String name = names.nextElement();
            Logger log = LogManager.getLogManager().getLogger(name);
            /* If already added remove it*/
            log.removeHandler(handler);
            log.addHandler(handler);
            log.setUseParentHandlers(false);
        }
    }

    public void run(String[] tools) {
        this.pipeAllLoggerToStderr();
        try {
            JenkinsLauncher launcher = new JenkinsLauncher();
            RepairToolsManager.getInstance().manualLoadRepairTools();
            launcher.jenkinsMain(this.gitUrl,this.gitToken,this.gitBranch,tools);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RunPipelineAction(String gitUrl,String gitToken,String gitBranch) {
        this.gitUrl = gitUrl;
        this.gitToken = gitToken;
        this.gitBranch = gitBranch;
    }
} 