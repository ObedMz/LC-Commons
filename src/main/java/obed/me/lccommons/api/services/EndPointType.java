package obed.me.lccommons.api.services;

public enum EndPointType {
    USER ("v1/user/"),
    RANK("v1/rank/"),
    RANKS("v1/ranks"),
    PUNISHMENT("v1/punishment/");

    private final String value;

    private EndPointType(String value) {
        this.value = value;
    }

    public String getEndPoint() {
        return value;
    }

}
