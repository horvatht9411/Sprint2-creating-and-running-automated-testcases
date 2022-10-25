public enum Issue {

    TOUCAN1("https://jira-auto.codecool.metastage.net/browse/TOUCAN-1", "TOUCAN-1"),

    TOUCAN2("https://jira-auto.codecool.metastage.net/browse/TOUCAN-2","TOUCAN-2"),

    TOUCAN3("https://jira-auto.codecool.metastage.net/browse/TOUCAN-3","TOUCAN-3"),

    COALA1("https://jira-auto.codecool.metastage.net/browse/COALA-1","COALA-1"),

    COALA2("https://jira-auto.codecool.metastage.net/browse/COALA-2","COALA-2"),

    COALA3("https://jira-auto.codecool.metastage.net/browse/COALA-3","COALA-3"),

    JETI1("https://jira-auto.codecool.metastage.net/browse/JETI-1","JETI-1"),

    JETI2("https://jira-auto.codecool.metastage.net/browse/JETI-2","JETI-2"),

    JETI3("https://jira-auto.codecool.metastage.net/browse/JETI-3","JETI-3");

    private String url;
    private String id;

    Issue(String url, String id) {
        this.url = url;
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }
}
