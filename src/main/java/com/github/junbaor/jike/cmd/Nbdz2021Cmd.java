package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.enums.ActionEnum;
import com.github.junbaor.jike.model.nbdz2021.Nbdz2021Me;
import org.apache.commons.lang3.RandomUtils;
import picocli.CommandLine;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@CommandLine.Command(name = "2021", description = "南北大种")
public class Nbdz2021Cmd implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int num = 1;
        while (true) {
            try {
                if (num % 10 == 0) {
                    Nbdz2021Me nbdz2021Me = App.jikeClient.getNbdz2021Me();
                    Nbdz2021Me.ContributionBean contribution = nbdz2021Me.getContribution();
                    String msg = "今天贡献：" + contribution.getTodayScore()
                            + " 总贡献：" + contribution.getCareerScore()
                            + " 超越了 " + contribution.getPercentileRank() + "% 网友";
                    System.out.println(msg);
                }

                int i = RandomUtils.nextInt(1, 4);
                if (i == 1) {
                    App.jikeClient.getNbdz2021Act(ActionEnum.PLANT);
                    System.out.println("播种...");
                } else if (i == 2) {
                    App.jikeClient.getNbdz2021Act(ActionEnum.WATER);
                    System.out.println("施肥...");
                } else if (i == 3) {
                    App.jikeClient.getNbdz2021Act(ActionEnum.REAP);
                    System.out.println("收割...");
                }

                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(8, 18));
                num++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
