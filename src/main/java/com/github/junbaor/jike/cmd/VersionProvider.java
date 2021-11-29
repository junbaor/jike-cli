package com.github.junbaor.jike.cmd;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        String javaVmName = System.getProperty("java.vm.name");
        String javaVersion = System.getProperty("java.version");
        String javaHome = System.getProperty("java.home");

        StringBuilder javaInfo = new StringBuilder();
        javaInfo.append("Java version").append(": ").append(javaVersion).append(", ");
        javaInfo.append("VM name").append(": ").append(javaVmName).append(", ");
        javaInfo.append("runtime").append(": ").append(javaHome);

        String jikeCliVersion = "v0.1.3";
        return new String[]{
                "jike-cli " + jikeCliVersion,
                javaInfo.toString()
        };
    }

}
