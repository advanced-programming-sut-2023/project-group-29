package view.menus;

public enum Captcha {
    CAPTCHA1("1.png",1781),
    CAPTCHA2("2.png",1381),
    CAPTCHA3("3.png",1491),
    CAPTCHA4("4.png",1722),
    CAPTCHA5("5.png",1959),
    CAPTCHA6("6.png",2163),
    CAPTCHA7("7.png",2177),
    CAPTCHA8("8.png",2723),
    CAPTCHA9("9.png",2785),
    CAPTCHA10("10.png",3541),
    CAPTCHA11("11.png",3847),
    CAPTCHA12("12.png",3855),
    CAPTCHA13("13.png",3876),
    CAPTCHA14("14.png",3967),
    CAPTCHA15("15.png",4185),
    CAPTCHA16("16.png",4310),
    CAPTCHA17("17.png",4481),
    CAPTCHA18("18.png",4578),
    CAPTCHA19("19.png",4602),
    CAPTCHA20("20.png",4681),
    CAPTCHA21("21.png",4924),
    CAPTCHA22("22.png",5326),
    CAPTCHA23("23.png",5463),
    CAPTCHA24("24.png",5771),
    CAPTCHA25("25.png",5849),
    CAPTCHA26("26.png",6426),
    CAPTCHA27("27.png",6553),
    CAPTCHA28("28.png",6607),
    CAPTCHA29("29.png",6133),
    CAPTCHA30("30.png",6960),
    CAPTCHA31("31.png",7475),
    CAPTCHA32("32.png",7609),
    CAPTCHA33("33.png",1155),
    CAPTCHA34("34.png",7825),
    CAPTCHA35("35.png",7905),
    CAPTCHA36("36.png",8003),
    CAPTCHA37("37.png",8010),
    CAPTCHA38("38.png",8368),
    CAPTCHA39("39.png",8455),
    CAPTCHA40("40.png",8506),
    CAPTCHA41("41.png",8555),
    CAPTCHA42("42.png",8583),
    CAPTCHA43("43.png",8692),
    CAPTCHA44("44.png",8776),
    CAPTCHA45("45.png",8972),
    CAPTCHA46("46.png",8996),
    CAPTCHA47("47.png",9061),
    CAPTCHA48("48.png",9386),
    CAPTCHA49("49.png",9582),
    CAPTCHA50("50.png",9633),
    ;
    private String imageAddress;
    private int number;

    Captcha(String imageAddress, int number) {
        this.imageAddress = "/images/captcha/" + imageAddress;
        this.number = number;
    }

    public static Captcha getCaptchaByNumber(int number) {
        for (Captcha value : Captcha.values()) {
            if (value.imageAddress.equals("/images/captcha/" + number + ".png")) return value;
        }
        return null;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public int getNumber() {
        return number;
    }
}
