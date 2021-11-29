package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.util.SystemUtils;
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

        String jikeCliVersion = SystemUtils.gitInfo().getProperty("git.build.version", "[unknown]");
        String gitHash = SystemUtils.gitInfo().getProperty("git.commit.id.full", "[unknown]");

        return new String[]{
                "jike-cli v" + jikeCliVersion,
                "git commit: " + gitHash,
                javaInfo.toString()
        };
    }

}
