package com.chibik.perf.report;

public class HTMLNewLine implements HTMLElement {

    public static final HTMLNewLine instance = new HTMLNewLine();

    public HTMLNewLine() {
    }

    @Override
    public String render() {
        return "<br/>";
    }
}
