package aggregateSpecSusp.formula;

import java.util.Collections;
import java.util.List;

import aggregateSpecSusp.*;

public class Formula {
    final public double formura(int corresponding, int notcorresponding) {

        double ret = 0;
        int type = App.weightType;
        switch (type) {
            case 0:
                ret = haruka(corresponding, notcorresponding);
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
                ret = senko((double) (corresponding / (corresponding + notcorresponding + 1.0)));
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

    final public double motherRoot(int son,int mother){
        double threshold = App.threshold;
        if(mother == 0){
            return 1.0;
        }
        if((double)son/(double)mother < threshold){
            return 1.0;
        }
        return Math.max(1.0,son/Math.sqrt(mother));
    }

    final public double sigmoidFunction(double proximity) {
        double gain = 20.0;
        return (Math.tanh(gain * (proximity - 0.9) / 2.0) + 1.0) / 2.0;
    }

    final public double sigmoidWeight(double proximity, int numberOfTest) {
        if(proximity < App.threshold){
            return 1.0;
        }
        return 1.0+sigmoidFunction(proximity);
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

    final public double haruka(double proximity, int numberOfTest) {
        double threshold = App.threshold;
        if (proximity < threshold) {
            return 1.0;
        } else {
            return (double) 1.0+sigmoidFunction(proximity);
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

    final public double senko(int pass,List<Double> proximity) {
        double thresholdL = 0.25;
        double thresholdR = 0.75;
        if (x < thresholdL) {
            return 1.0 - x;
        } else if (thresholdR < x) {
            return x;
        } else {
            return thresholdR - x + 1.0;
        }
    }

    private double getFirstQuartile(List<Double> proximity){
        Collections.sort(proximity);
        if(proximity.size()%2==0){

        }else{

        }
    }

    private double getThirdQuartile(List<Double> proximity){
        
    }

    private double yoruka(double x) {
        double A = 10.0;
        double Min = 1.0;
        double Pi = Math.PI;
        double ret = A * (-Math.sin((double) ((5.0 / 6.0) * Pi * x + (1.0 / 6.0) * Pi)) + (1.0 + Min));
        return ret;
    }
}
