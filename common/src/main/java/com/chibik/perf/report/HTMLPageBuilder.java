package com.chibik.perf.report;

public class HTMLPageBuilder implements HTMLElement {

    private static final String HTML_START = "<html>";
    private static final String HTML_END = "<html>";
    private static final String META = "<meta charset=\"utf-8\">";
    private static final String HEAD_START = "<head>";
    private static final String HEAD_END = "</head>";
    private static final String TITLE_START = "<title>";
    private static final String TITLE_END = "</title>";
    private static final String BODY_START = "<body>";
    private static final String BODY_END = "</body>";

    private final StringBuilder builder = new StringBuilder();

    public HTMLPageBuilder(String title) {
        builder.append(HTML_START).append("\n");
        builder.append(HEAD_START).append("\n")
                .append(META).append("\n")
                .append(TITLE_START).append("\n")
                .append(title)
                .append(TITLE_END).append("\n")
                .append(HEAD_END).append("\n");

        builder.append(BODY_START);
    }

    public void append(HTMLElement element) {
        builder.append(element.render()).append("\n");
    }

    @Override
    public String render() {
        builder.append(BODY_END).append("\n");
        builder.append(HTML_END).append("\n");
        return builder.toString();
    }
}
