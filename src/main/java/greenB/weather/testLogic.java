package greenB.weather;

public class testLogic {
    double _celsius; //섭씨온도
    double _windSpeed; //풍속
    double _windChillTemperature; //체감온도
    double _humidity; //습도
    double _discomfortIndex; // 불쾌지수

    public static void testLogic(){

    }
    public static double getWindChill(double celsius, double windSpeed){
        double windChillTemperature = 0;

        windChillTemperature = 13.12 + (0.6215 * celsius) - (11.37 *(Math.pow(windSpeed, 0.16))) + (0.3965 * (Math.pow(windSpeed, 0.16) * celsius));

        return windChillTemperature;
    }
    public static double getdiscomfortIndex(double celsius, double humidity){
        double discomfortIndex = 0;

        discomfortIndex = ((1.8* celsius)-(0.55*(1-humidity)*((1.8 * celsius)- 26)))+32;

        return discomfortIndex;
    }
    public  static String judgmentWindCHill(double windChillTemperature){
        String judgmentWindCHill = "";
        if( 10.0 >= windChillTemperature && windChillTemperature  >= -10.0) {
            judgmentWindCHill = "낮음";
        } else if (-10.0 >= windChillTemperature && windChillTemperature  >= -25.0) {
            judgmentWindCHill = "보통";
        } else if (-25.0 >= windChillTemperature && windChillTemperature  >= -45.0) {
            judgmentWindCHill = "추움";
        } else if (-45.0 >= windChillTemperature && windChillTemperature  > -60.0) {
            judgmentWindCHill = "주의";
        } else if (-60.0 >= windChillTemperature) {
            judgmentWindCHill = "위험";
        }
        return judgmentWindCHill;
    }
    public  static String judgmentdiscomfortIndex(double discomfortIndex , double celsius){
        String judgmentdiscomfortIndex = "";
        if(discomfortIndex <= 68.0 && celsius <= 20.0) {
            judgmentdiscomfortIndex = "전원 쾌적";
        } else if (discomfortIndex <= 70.0 && celsius <= 21.0) {
            judgmentdiscomfortIndex = "불쾌를 나타냄";
        } else if (discomfortIndex <= 75.0 && celsius <= 24.0) {
            judgmentdiscomfortIndex = "10% 정도 불쾌";
        } else if (discomfortIndex <= 80.0 && celsius <= 26.5) {
            judgmentdiscomfortIndex = "50% 정도 불쾌";
        } else if (discomfortIndex <= 83.0 && celsius <= 28.5) {
            judgmentdiscomfortIndex = "전원 불쾌";
        } else if (discomfortIndex <= 86.0 && celsius <= 30.0) {
            judgmentdiscomfortIndex = "매우 불쾌";
        }

        return judgmentdiscomfortIndex;
    }

}
