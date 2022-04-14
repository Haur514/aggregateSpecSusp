package aggregateSpecSusp.formula;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import aggregateSpecSusp.*;

public class Formula {
    final public double formura(int corresponding, int notcorresponding) {

        double ret = 0;
        int type = App.weightType;
        switch (type) {
            case 0:
                break;
            case 1:
                ret = yoshiruka(corresponding, notcorresponding);
                break;
            case 2:
                ret = ruka(corresponding, notcorresponding);
                break;
            case 3:
                ret = haru(corresponding, notcorresponding);
                break;
            case 4:
                ret = haka(corresponding, notcorresponding);
                break;
            case 5:
                ret = yoruka((double) (corresponding / (corresponding + notcorresponding + 1.0)));
                break;
            case 6:

                break;
            case 7:
                ret = yoshiokaharuka(corresponding, notcorresponding);
                break;
            case 8:
                ret = functionC(corresponding, notcorresponding);
                break;
        }

        return ret;
    }

    final public double motherRoot(int son, int mother) {
        double threshold = App.threshold;
        if (mother == 0) {
            return 1.0;
        }
        if ((double) son / (double) mother < threshold) {
            return 1.0;
        }
        return Math.max(1.0, son / Math.sqrt(mother));
    }

    final public double sigmoidFunction(double proximity) {
        double gain = 20.0;
        return (Math.tanh(gain * (proximity - 0.9) / 2.0) + 1.0) / 2.0;
    }

    final public double sigmoidWeight(double proximity, int numberOfTest) {
        if (proximity < App.threshold) {
            return 1.0;
        }
        return 1.0 + sigmoidFunction(proximity);
    }

    private double functionC(int corresponding, int notcorresponding) {
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = 0.75;
        if (x < threshold) {
            return 1.0;
        } else {
            return (double) corresponding / Math.sqrt((double) corresponding + (double) notcorresponding);
        }
    }

    private double yoshiruka(int corresponding, int notcorresponding) {
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = 0.75;
        if (x < threshold) {
            return 1.0;
        } else {
            return 1.0 + 10.0 * (x - 0.75);
        }
    }

    private double haru(int corresponding, int notcorresponding) {
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = 0.25;
        if (x < threshold) {
            return x + 0.75;
        } else if (threshold <= x && x <= (1 - threshold)) {
            return 1.0;
        } else {
            return 1.0 + x - 0.75;
        }
    }

    final public double haruka(int corresponding,int notcorrespoinding, int numberOfTest) {
        double proximity = 0.0;
        double threshold = App.threshold;
        double mother;
        if((corresponding + notcorrespoinding) == 0){
            return 1;
        }
        proximity = (double)corresponding/(double)(corresponding+notcorrespoinding);
        if (proximity < threshold) {
            return 1.0;
        } else {
            return (double) 1.0+proximity*numberOfTest;
        }
    }

    private double ruka(int corresponding, int notcorresponding) {
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = App.threshold;
        if (x < threshold) {
            return 1.0;
        } else {
            return (double) corresponding / Math.sqrt((x));
        }
    }

    final public double haka(int corresponding, int notcorresponding) {
        double x;
        double mother;
        if (corresponding + notcorresponding == 0) {
            mother = 1.0;
            x = 1.0;
        } else {
            mother = (double) ((double) corresponding + (double) notcorresponding);
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = App.threshold;
        if (x < threshold) {
            return 1.0;
        } else {
            return (double) corresponding / Math.sqrt(mother);
        }
    }



    private double yoshiokaharuka(int corresponding, int notcorrespoinding) {
        double x;
        if (corresponding + notcorrespoinding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorrespoinding));
        }
        double threshold = 0.75;
        if (x < threshold) {
            return 1.0;
        } else {
            return yoruka(x);
        }
    }

    final public double senko(int pass, List<Double> proximity,Set<Integer> failedTestList) {
        double thresholdL = 0;//getFirstQuartile(proximity,failedTestList);
        double thresholdR = 0;//getThirdQuartile(proximity,failedTestList);
    //    System.out.println(proximity);
    //    System.out.println(thresholdL);
    //    System.out.println(thresholdR);
       if (proximity.get(pass) <= thresholdL) {
            return 1.0 - proximity.get(pass);
        } else if (thresholdL < proximity.get(pass) && proximity.get(pass) < thresholdR) {
            return proximity.get(pass);
        } else {
            return thresholdR - proximity.get(pass) + 1.0;
        }
    }

    // [start,end]における中央値を返す
    private double getMedianOfSpecifiedSection(List<Double> proximity, int start, int end) {
        List<Double> tmp = new ArrayList<>();
        for (Double p : proximity) {
            tmp.add(p);
        }
        Collections.sort(tmp);
        // System.out.println(tmp);
        if ((end - start) % 2 == 0) {
            return (tmp.get((start + end) / 2));
        } else {
            return (tmp.get((start + end) / 2) + tmp.get((start + end) / 2 + 1)) / 2;
        }
    }

    private double getFirstQuartile(List<Double> proximity,Set<Integer> failedTestList) {
        int start = failedTestList.size();
        int end = proximity.size();
        if ((proximity.size()-failedTestList.size()) % 2 == 0) {
            return getMedianOfSpecifiedSection(proximity, start, (proximity.size() - failedTestList.size()) / 2 + start);
        } else {
            return getMedianOfSpecifiedSection(proximity, start, (proximity.size() - failedTestList.size()) / 2 - 1 + start);
        }
    }

    private double getThirdQuartile(List<Double> proximity,Set<Integer> failedTestList) {
        if ((proximity.size()-failedTestList.size()) % 2 == 0) {
            return getMedianOfSpecifiedSection(proximity,
                    proximity.size() - 1 - (proximity.size() -  failedTestList.size()) / 2 , proximity.size() - 1);
        } else {
            return getMedianOfSpecifiedSection(proximity,
                    proximity.size() - 1 - (proximity.size() -  failedTestList.size()) / 2 , proximity.size() - 1);
        }
    }

    private double yoruka(double x) {
        double A = 10.0;
        double Min = 1.0;
        double Pi = Math.PI;
        double ret = A * (-Math.sin((double) ((5.0 / 6.0) * Pi * x + (1.0 / 6.0) * Pi)) + (1.0 + Min));
        return ret;
    }
}
