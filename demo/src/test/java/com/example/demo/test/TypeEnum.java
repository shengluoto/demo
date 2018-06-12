package com.example.demo.test;

public enum TypeEnum {
    power("power"),battery("battery"),ver("ver"),hbeat("ver"),projectid("projectid"),
    pilelist("pilelist"),pileinf("pileinf"),pilebitmap("pilebitmap"),emppilelen("emppilelen"),pilingmac("pilingmac"),
    tasklist("tasklist"),rapporteur("rapporteur"),conuseplan("conuseplan"),conuseact("conuseact"),caltrsd("cal&trsd"),
    peralmsta("peralmsta"),almsta("almsta"),matchlist("matchlist"),uselist("uselist"),usenodesta("usenodesta"),
    sensorid("sensorid"), raw("raw"),calcon("cal.con"),calslurry("cal.slurry"),alarm("alarm"),
    fin("fin");

    private String id;

    private TypeEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
