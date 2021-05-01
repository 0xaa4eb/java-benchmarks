package com.chibik.perf.report;

public class HTMLHeader implements HTMLElement {

    private HTMLElement content;
    private int headNumber;

    HTMLHeader(int headNumber, HTMLElement content) {
        this.content = content;
        this.headNumber = headNumber;
    }

    @Override
    public String render() {
        return "<" + htmlTag() + ">" + content.render() + "<" + htmlTag() + "/>";
    }

    private String htmlTag() {
        return "h" + headNumber;
    }
}
