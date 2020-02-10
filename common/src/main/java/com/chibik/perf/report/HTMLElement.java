package com.chibik.perf.report;

public interface HTMLElement {

    static HTMLElement h3(HTMLElement content) {
        return new HTMLHeader(3, content);
    }

    static HTMLElement newLine() {
        return new HTMLNewLine();
    }

    static HTMLElement text(String text) {
        return new HTMLText(text);
    }

    static HTMLElement text(Number text) {
        return new HTMLText(String.valueOf(text));
    }

    String render();
}
