package com.chibik.perf.report;

public class HTMLText implements HTMLElement {

    private final String text;
    private final CssProps props;

    public HTMLText(String text) {
        this.text = text;
        this.props = new CssProps();
    }

    public HTMLText(String text, CssProps props) {
        this.text = text;
        this.props = props;
    }

    public static HTMLText of(String value) {
        return new HTMLText(value);
    }

    public static HTMLText of(Number value) {
        return new HTMLText(String.valueOf(value));
    }

    @Override
    public String render() {
        return text.replace("\r", "").replace("\n", "<br/>");
    }
}
