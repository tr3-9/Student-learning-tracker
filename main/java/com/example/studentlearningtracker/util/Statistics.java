package com.example.studentlearningtracker.util;

import java.util.List;

public final class Statistics {
    public static double pearson(List<Integer> xs, List<Integer> ys) {
        int n = xs.size();
        if (n == 0 || ys.size() != n) return Double.NaN;
        double meanX = 0, meanY = 0;
        for (int v : xs) meanX += v;
        for (int v : ys) meanY += v;
        meanX /= n;  meanY /= n; //calc pearson statistic

        double num = 0, denX = 0, denY = 0;
        for (int i = 0; i < n; i++) {
            double dx = xs.get(i) - meanX;
            double dy = ys.get(i) - meanY;
            num  += dx * dy;
            denX += dx * dx;
            denY += dy * dy;
        }
        return num / Math.sqrt(denX * denY);
    }
    private Statistics() {}
}
