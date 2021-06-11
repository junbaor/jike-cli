package com.github.junbaor.jike.cmd;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        return new String[]{"v0.1.3"};
    }

}
